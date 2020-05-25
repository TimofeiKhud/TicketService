package com.example.ticketservicenew.data.eventlist;

import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.LiveData;

import com.example.ticketservicenew.business.model.Event;
import com.example.ticketservicenew.data.dto.EventDateHallDto;
import com.example.ticketservicenew.data.dto.admin.EventOutputDto;
import com.example.ticketservicenew.data.dto.mapper.EventBookingDtoMapper;
import com.example.ticketservicenew.data.eventlist.datasource.EventListDataSource;
import com.example.ticketservicenew.data.provider.web.Api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import retrofit2.Response;

/**
 * {@link EventListRepository} for retrieving event data.
 */
public class EventListRepositoryImpl implements EventListRepository{
    private static final String TAG = "EventsListRepoImpl";

    private OnFiltersChangedListener listener;

    //API
    private Api api;
    private EventBookingDtoMapper eventBookingDtoMapper;

    private int retryCount;
    private int numberOfAttemptToRetry;
    private int pageSize;

    private List<EventOutputDto> allEvents;
    //events filtered by category and date(if filters set)
    private List<EventOutputDto> filteredEvents;
    //Category filters array - if empty -> all categs
    private List<Integer> categFilters;
    //in case dates in date range were changed
    private Pair<Long, Long> dateFilter;
    private String searchString;
    private boolean searchStringChanged;


    /**
     * Constructs a {@link EventListRepository}.
     *
     * @param api {@link Api}
     * @param eventBookingDtoMapper {@link EventBookingDtoMapper}.
     */
    public EventListRepositoryImpl(Api api, EventBookingDtoMapper eventBookingDtoMapper) {
        Log.d(TAG, "create");
        this.api = api;
        this.eventBookingDtoMapper = eventBookingDtoMapper;

        categFilters = new ArrayList<>();
        dateFilter = new Pair<>(-1L, -1L);
        allEvents = new ArrayList<>();
        filteredEvents = new ArrayList<>();
        searchString = "";
        pageSize = 5;
        numberOfAttemptToRetry = 3;
    }

    @Override
    public void setOnFiltersChangedListener(OnFiltersChangedListener listener) {
        this.listener = listener;
    }

    //on reloading list of events(on change screen orientation, return from another fragment, etc.)
    @Override
    public void onInitialLoading() {
        if(!searchStringChanged){
            filteredEvents.clear();
        }
    }

    @Override
    public Single<List<Event>> getEvents(int pageNumber, int pageSize) {
        Log.d(TAG, "getCurrentEvents: start");
        retryCount = 0;
        if(searchStringChanged){
            Log.d(TAG, "get events (filtered events number): " + filteredEvents.size());
            if(pageNumber > 0){
                searchStringChanged = false;
                return Single.just(new ArrayList<>());
            }
            return Single.just(filteredEvents)
                    .flatMap(this::filterNotFilledEvents)
                    .flatMap(this::searchEvents)
                    .map(eventOutputDtoList -> eventBookingDtoMapper.mapEventDtoToEvent(eventOutputDtoList));
        }else if (dateFilter.first > 0 && dateFilter.second > 0){
            EventDateHallDto dto = new EventDateHallDto(dateFilter.first, dateFilter.second);
            return api.getByDates(pageNumber, pageSize, dto)
                    .onErrorResumeNext(throwable -> {
                        if (retryCount < numberOfAttemptToRetry) {
                            retryCount++;
                        return api.getByDates(pageNumber, pageSize, dto);
                    }else return Single.fromObservable(Observable.empty()); })
                    .flatMap(this::onGetEventsSuccess)
                    .flatMap(this::filterNotFilledEvents)
                    .flatMap(this::searchEvents)
                    .flatMap(this::filterEvents)
                    .map(eventOutputDtoList -> eventBookingDtoMapper.mapEventDtoToEvent(eventOutputDtoList));
        }else{
            return api.getCurrentEvents(pageNumber, pageSize)
                    .onErrorResumeNext(throwable -> {
                        if (retryCount < numberOfAttemptToRetry) {
                            retryCount++;
                            return api.getCurrentEvents(pageNumber, pageSize);
                        }else return Single.fromObservable(Observable.empty()); })
                    .flatMap(this::onGetEventsSuccess)
                    .flatMap(this::filterNotFilledEvents)
                    .flatMap(this::searchEvents)
                    .flatMap(this::filterEvents)
                    .map(eventOutputDtoList -> eventBookingDtoMapper.mapEventDtoToEvent(eventOutputDtoList));
        }
    }

    @Override
    public void onSearchTextChanged(String newText) {
        Log.d(TAG, "onSearchTextChanged(search string text): " + newText);
        searchString = newText;
        searchStringChanged = true;
        listener.onFiltersChanged();
    }

    @Override
    public boolean onFilterSelect(Pair<Long, Long> dateRange, List<Integer> categories) {
        long fromDate = dateRange.first;
        long toDate  = dateRange.second;
        if(fromDate == dateFilter.first &&
                toDate == dateFilter.second &&
                categFilters.containsAll(categories) &&
                categFilters.size() == categories.size()){
            return false;
        }else{
            dateFilter = dateRange;
            categFilters = categories;
        }
        listener.onFiltersChanged();
        return true;
    }

    private Single<List<EventOutputDto>> onGetEventsSuccess(Response<List<EventOutputDto>> listResponse) throws IOException {
        if (listResponse.isSuccessful()){
            List<EventOutputDto> eventsToAdd = listResponse.body();
            if (eventsToAdd == null){
                throw new RuntimeException("Empty list");
            }
            for(EventOutputDto dto : eventsToAdd){
                Log.d(TAG, "onGetEventsSuccess: "+ dto.getArtist());
            }
            allEvents.addAll(eventsToAdd);
            filteredEvents.addAll(eventsToAdd);
            return Single.just(eventsToAdd)/*.flatMap(this::getFilteredByCategory)/*.flatMap(this::getSearchedList)*/;
        }else {
            Log.d(TAG, "onGetEventsSuccess: "+ listResponse.errorBody().string());
            throw new RuntimeException("Server error! Call to support");
        }
    }

    private Single<List<EventOutputDto>> filterEvents(List<EventOutputDto> listForFilter){
        if(categFilters.size() == 0){
            return Single.just(listForFilter);
        }else{
            List<EventOutputDto> res = new ArrayList();
            for(EventOutputDto dto : listForFilter){
                Log.d(TAG, "filter events: "+ dto.getArtist());
                if(categFilters.contains(dto.getEventType())){
                    res.add(dto);
                }
            }
            return Single.just(res);
        }
    }

    private Single<List<EventOutputDto>> searchEvents(List<EventOutputDto> listForSearch){
        if(searchString.isEmpty()){
            return Single.just(listForSearch);
        }
        List<EventOutputDto> res = new ArrayList<>();
        for (EventOutputDto dto : listForSearch){
            Log.d(TAG, "search events: "+ dto.getArtist());
            if ((dto.getArtist().toLowerCase()).startsWith(searchString.toLowerCase())){
                res.add(dto);
            }
        }
        return Single.just(res);
    }

    private Single<List<EventOutputDto>> filterNotFilledEvents(List<EventOutputDto> listForFilter){
        List<EventOutputDto> res = new ArrayList<>();
        for (EventOutputDto dto : listForFilter){
            Log.d(TAG, "filter Not Filled Events "+ dto.getArtist());
            if (dto.getArtist() != null){
                res.add(dto);
            }
        }
        return Single.just(res);
    }


}
