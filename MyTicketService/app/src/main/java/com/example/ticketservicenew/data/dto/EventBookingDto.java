package com.example.ticketservicenew.data.dto;

import java.util.List;
import java.util.Map;

public class EventBookingDto {
        public String eventId;
        //public List<Map<String, List<String>>> lockedSeats;
        public List<Map<String, List<String>>> lockedSeats;
     // map of row + seat


        public EventBookingDto(String eventId, List<Map<String, List<String>>> lockedSeats) {
                this.eventId = eventId;
                this.lockedSeats = lockedSeats;
        }

        public EventBookingDto() {
        }

        public String getEventId() {
                return eventId;
        }

        public void setEventId(String eventId) {
                this.eventId = eventId;
        }

        public List<Map<String, List<String>>> getLockedSeats() {
                return lockedSeats;
        }

        public void setLockedSeats(List<Map<String, List<String>>> lockedSeats) {
                this.lockedSeats = lockedSeats;
        }
}
