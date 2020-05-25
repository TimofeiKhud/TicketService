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
    public Single<EventInfo> getEventInfo() {
        return repository.getEventInfo();
    }

    @Override
    public void saveId(String eventId) {
        repository.saveEventId(eventId);
    }

    @Override
    public String getEventId() {
        return repository.getEventId();
    }

    @Override
    public void saveHallId(int hall) {
        repository.saveHallId(hall);
    }

    @Override
    public int getHallId() {
        return repository.getHallId();
    }


}
