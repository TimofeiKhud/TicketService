package com.example.ticketservicenew.data.dto.objectsForDto;

import com.example.ticketservicenew.business.model.LockedSeats;

import java.util.List;

public class LockedSeatsRequestDto {
    String eventId;
    List<LockedSeatsDto> lockedSeats;

    public LockedSeatsRequestDto(String eventId, List<LockedSeatsDto> lockedSeats) {
        this.eventId = eventId;
        this.lockedSeats = lockedSeats;
    }

    public String getEventId() {
        return eventId;
    }

    public List<LockedSeatsDto> getLockedSeats() {
        return lockedSeats;
    }
}
