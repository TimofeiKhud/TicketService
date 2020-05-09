package com.example.ticketservicenew.business.event;

import com.example.ticketservicenew.business.model.Event;
import com.example.ticketservicenew.business.model.EventInfo;
import com.example.ticketservicenew.data.event.EventRepository;

import io.reactivex.Single;

public class EventInteractorImpl implements EventInteractor {
    private static final String TAG = EventInteractorImpl.class.getName();
    EventRepository repository;

    public EventInteractorImpl(EventRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<Event> getEvent(String id){
        return repository.getEvent(id);
    }

    @Override
    public Single<EventInfo> getEventInfo(String id) {
        return repository.getEventInfo(id);
    }




}
