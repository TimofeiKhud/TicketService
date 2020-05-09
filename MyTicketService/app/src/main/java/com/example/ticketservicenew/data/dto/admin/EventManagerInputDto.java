package com.example.ticketservicenew.data.dto.admin;

import java.util.List;

public class EventManagerInputDto {
    public int eventStatus;
    // Status of current event: 0 - cancelled; 1 - waiting; 2 - finished.
    public String eventName, artist, description;
    public List<String> images; // img urls

    public EventManagerInputDto() {
    }

    public EventManagerInputDto(int eventStatus, String eventName, String artist, String description, List<String> images) {
        this.eventStatus = eventStatus;
        this.eventName = eventName;
        this.artist = artist;
        this.description = description;
        this.images = images;
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
}
