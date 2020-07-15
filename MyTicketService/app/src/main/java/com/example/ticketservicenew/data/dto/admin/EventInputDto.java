package com.example.ticketservicenew.data.dto.admin;

import java.util.List;

public class EventInputDto {

    public List<String> loginManagers;
    public long eventStart;
    public int eventDurationHours;
    public int hall;
    public int eventType;

    public EventInputDto(List<String> loginManagers, long eventStart, int eventDurationHours, int hall, int eventType) {
        this.loginManagers = loginManagers;
        this.eventStart = eventStart;
        this.eventDurationHours = eventDurationHours;
        this.hall = hall;
        this.eventType = eventType;
    }

}
