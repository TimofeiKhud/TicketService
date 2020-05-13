package com.example.ticketservicenew.data.eventlist.datasource;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.ticketservicenew.business.model.Event;
import com.example.ticketservicenew.data.eventlist.EventListRepository;

import io.reactivex.disposables.CompositeDisposable;

public class EventListDataSourceFactory extends DataSource.Factory<Integer, Event> {
    private EventListRepository repository;
    private CompositeDisposable compositeDisposable;

    public EventListDataSourceFactory(EventListRepository repository, CompositeDisposable compositeDisposable) {
        this.repository = repository;
        this.compositeDisposable = compositeDisposable;

    }

    @Override
    public DataSource<Integer, Event> create() {
        return new EventListDataSource(repository, compositeDisposable);
    }

}