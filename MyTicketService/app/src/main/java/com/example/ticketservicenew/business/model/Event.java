package com.example.ticketservicenew.business.model;

import java.util.List;
import java.util.Objects;

public class Event {
    private String eventId;
    private int eventStatus;
    private String eventName;
    private String artist;
    private long eventStart;
    private int eventDurationHours;
    private int hall;
    private int eventType;
    private String description;
    private List<String> images;
    private String priceRanges;
    private String managers;

    public Event(String eventId, String eventName, long eventStart, int eventDurationHours, int hall, int eventType, String managers) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventStart = eventStart;
        this.eventDurationHours = eventDurationHours;
        this.hall = hall;
        this.eventType = eventType;
        this.managers = managers;
    }

    public Event(String eventId, long eventStart, int eventDurationHours, int hall, int eventType, String managers) {
        this.eventId = eventId;
        this.eventStart = eventStart;
        this.eventDurationHours = eventDurationHours;
        this.hall = hall;
        this.eventType = eventType;
        this.managers = managers;
    }

    public Event(String eventId, int eventStatus, String eventName, String artist, long eventStart, int eventDurationHours, int hall, int eventType, String description, List<String> images, String priceRanges, String managers) {
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

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public int getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(int eventStatus) {
        this.eventStatus = eventStatus;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getEventStart() {
        return eventStart;
    }

    public void setEventStart(long eventStart) {
        this.eventStart = eventStart;
    }

    public int getEventDurationHours() {
        return eventDurationHours;
    }

    public void setEventDurationHours(int eventDurationHours) {
        this.eventDurationHours = eventDurationHours;
    }

    public int getHall() {
        return hall;
    }

    public void setHall(int hall) {
        this.hall = hall;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getPriceRanges() {
        return priceRanges;
    }

    public void setPriceRanges(String priceRanges) {
        this.priceRanges = priceRanges;
    }

    public String getManagers() {
        return managers;
    }

    public void setManagers(String managers) {
        this.managers = managers;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId='" + eventId + '\'' +
                ", eventStatus=" + eventStatus +
                ", eventName='" + eventName + '\'' +
                ", artist='" + artist + '\'' +
                ", eventStart=" + eventStart +
                ", eventDurationHours=" + eventDurationHours +
                ", hall=" + hall +
                ", eventType=" + eventType +
                ", description='" + description + '\'' +
                ", images='" + images + '\'' +
                ", priceRanges='" + priceRanges + '\'' +
                ", managers='" + managers + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return eventId.equals(event.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId);
    }
}
