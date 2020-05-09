package com.example.ticketservicenew.business.eventlist;

import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.PagedList;
import androidx.paging.RxPagedListBuilder;

import com.example.ticketservicenew.business.model.Event;
import com.example.ticketservicenew.data.eventlist.EventListRepository;
import com.example.ticketservicenew.data.eventlist.datasource.EventListDataSource;
import com.example.ticketservicenew.data.eventlist.datasource.EventListDataSourceFactory;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

public class EventListInteractorImpl implements EventListInteractor{
    private static final String TAG = "EventListInteractorImpl";
    private EventListRepository repository;


    public EventListInteractorImpl(EventListRepository repository) {
        this.repository = repository;
    }

    @Override
    public void onSearchTextChanged(String newText) {
         repository.onSearchTextChanged(newText);
    }

    @Override
    public boolean onFilterSelect(Pair<Long, Long> dateRange, List<Integer> categories) {
        return repository.onFilterSelect(dateRange, categories);
    }

//    @Override
//    public Single<List<Event>> onEventListLoading(int offset, int limit) {
//        Log.d(TAG, " loading events ");
//        return repository.onEventListLoading(offset, limit);
//    }

//    @Override
//    public Single<List<Event>> onFiltersSet(Pair<Long, Long> dateRange, List<Integer> categories) {
//        return repository.onFiltersSet(dateRange, categories);
//    }
}
