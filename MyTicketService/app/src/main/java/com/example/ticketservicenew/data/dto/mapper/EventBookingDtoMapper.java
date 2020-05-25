package com.example.ticketservicenew.data.dto.mapper;


import com.example.ticketservicenew.business.model.Event;
import com.example.ticketservicenew.data.dto.admin.EventOutputDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Mapper class used to transform {@link EventOutputDto} in the data layer to {@link Event} in the
 * business layer.
 */
public class EventBookingDtoMapper {

    /**
     * Transform a List of {@link EventOutputDto} into a Collection of {@link Event}.
     *
     * @param eventOutputDtoCollection Object Collection to be transformed.
     * @return {@link Event} if valid {@link EventOutputDto} otherwise null.
     */
public List<Event> mapEventDtoToEvent(Collection<EventOutputDto> eventOutputDtoCollection){
    List<Event> events = new ArrayList<>();
    int i = 0;
    for(EventOutputDto dto : eventOutputDtoCollection){
        Event event = new Event(dto.getEventId(),
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
        events.add(event);
    }
    return events;
}
}
