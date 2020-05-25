package com.example.ticketservicenew.data.event;

import com.example.ticketservicenew.business.model.Event;
import com.example.ticketservicenew.business.model.EventInfo;

import io.reactivex.Single;

public interface EventRepository {
    Single<Event> getEvent(String id);
    Single<EventInfo> getEventInfo();
    void saveEventId(String eventId);
    String getEventId();

    void saveHallId(int hall);
    int getHallId();
}
