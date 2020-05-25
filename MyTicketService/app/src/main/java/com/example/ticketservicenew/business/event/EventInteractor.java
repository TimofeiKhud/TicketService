package com.example.ticketservicenew.business.event;

import com.example.ticketservicenew.business.model.Event;
import com.example.ticketservicenew.business.model.EventInfo;

import io.reactivex.Single;

public interface EventInteractor {
    Single<Event> getEvent(String id);
    Single<EventInfo> getEventInfo();
    void saveId(String eventId);
    String getEventId();

    void saveHallId(int hall);
    int getHallId();
}
