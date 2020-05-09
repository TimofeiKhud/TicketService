package com.example.ticketservicenew.data.event;

import android.util.Log;

import com.example.ticketservicenew.business.model.Event;
import com.example.ticketservicenew.business.model.EventInfo;
import com.example.ticketservicenew.data.dto.admin.EventOutputDto;
import com.example.ticketservicenew.data.dto.objectsForDto.RestTicketsDto;
import com.example.ticketservicenew.data.provider.web.Api;

import java.io.IOException;

import io.reactivex.Single;
import retrofit2.Response;

public class EventRepositoryImpl implements EventRepository{
    public static final String TAG = EventRepositoryImpl.class.getName();
private Api api;

    public EventRepositoryImpl(Api api) {
        this.api = api;
    }

    @Override
    public Single<Event> getEvent(String id) {
        return api.getEvent(id).flatMap(this::onGetEventSuccess).map(this::mapEventDtoToModel);
        //return null;
    }

    @Override
    public Single<EventInfo> getEventInfo(String id) {
        return api.getEventInfo(id).flatMap(this::onGetEventInfoSuccess).map(this::mapEventInfoDtoToModel);
    }

    private Single<EventOutputDto> onGetEventSuccess(Response<EventOutputDto> response) throws IOException {
            if (response.isSuccessful()){
                Log.d(TAG, "onGetEventSuccess: " + response.body());
                EventOutputDto event = response.body();
                return Single.just(event);
            }else {
                Log.d(TAG, "onGetEventSuccess: " + response.errorBody().string());
                throw new RuntimeException("Server error! Call to support");
            }
    }

    private Event mapEventDtoToModel(EventOutputDto dto){
        return new Event(dto.getEventId(),
                dto.getEventStatus(),
                dto.getEventName(),
                dto.getArtist(),
                dto.getEventStart(),
                dto.getEventDurationHours(),
                dto.getHall(),
                dto.getEventType(),
                dto.getDescription(),
                dto.getImages(),
                dto.getPriceRanges().toString(),
                dto.getManagers().toString());
    }

    private Single<RestTicketsDto> onGetEventInfoSuccess(Response<RestTicketsDto> response) throws IOException {
        if (response.isSuccessful()){
            Log.d(TAG, "onGetEventInfoSuccess: " + response.body());
            RestTicketsDto eventInfo = response.body();
            return Single.just(eventInfo);
        }else {
            Log.d(TAG, "onGetEventInfoSuccess: " + response.errorBody().string());
            throw new RuntimeException("Server error! Call to support");
        }
    }

    private EventInfo mapEventInfoDtoToModel(RestTicketsDto dto){
        return new EventInfo(dto.eventId, dto.maxPrice, dto.minPrice, dto.restTick);
    }
}
