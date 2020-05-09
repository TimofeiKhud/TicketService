package com.example.ticketservicenew.data.dto.admin;



import com.example.ticketservicenew.data.dto.objectsForDto.PriceRanges;

import java.util.List;
import java.util.Set;

public class EventOutputDto {
    public String eventId;
    public int eventStatus;
    public String eventName;
    public String artist;
    public long eventStart; // $int64
    public int eventDurationHours;
    public int hall; //hallId
    public int eventType;
    public String description;
    public List<String> images;
    public Set<PriceRanges> priceRanges; // TODO create priceranges class, it it really a set?
    public Set<String> managers;

    public EventOutputDto() {
    }

    public EventOutputDto(String eventId,
                          int eventStatus,
                          String eventName,
                          String artist,
                          long eventStart,
                          int eventDurationHours,
                          int hall,
                          int eventType,
                          String description,
                          List<String> images,
                          Set<PriceRanges> priceRanges,
                          Set<String> managers) {
        this.eventId = eventId;
        this.eventStatus = eventStatus;
        this.eventName = eventName;
        this.artist = artist;
        this.eventStart = eventStart;
        this.eventDurationHours = eventDurationHours;
        this.hall = hall;
        this.eventType = eventType;
        this.description = description;
        this.images = images;
        this.priceRanges = priceRanges;
        this.managers = managers;
    }

    public String getEventId() {
        return eventId;
    }

    public int getEventStatus() {
        return eventStatus;
    }

    public String getEventName() {
        return eventName;
    }

    public String getArtist() {
        return artist;
    }

    public long getEventStart() {
        return eventStart;
    }

    public int getEventDurationHours() {
        return eventDurationHours;
    }

    public int getHall() {
        return hall;
    }

    public int getEventType() {
        return eventType;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getImages() {
        return images;
    }

    public Set<PriceRanges> getPriceRanges() {
        return priceRanges;
    }

    public Set<String> getManagers() {
        return managers;
    }
}
