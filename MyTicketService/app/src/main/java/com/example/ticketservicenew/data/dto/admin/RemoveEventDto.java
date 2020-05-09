package com.example.ticketservicenew.data.dto.admin;

public class RemoveEventDto {
    public String eventId;
    public String cause;

    public RemoveEventDto(String eventId, String cause) {
        this.eventId = eventId;
        this.cause = cause;
    }

    public RemoveEventDto() {
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
