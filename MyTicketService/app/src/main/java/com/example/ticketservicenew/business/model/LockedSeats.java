package com.example.ticketservicenew.business.model;

import java.util.List;
import java.util.Map;

public class LockedSeats {
    Map<Integer, List<String>> lockedSeats;

    public LockedSeats(Map<Integer, List<String>> lockedSeats) {
        this.lockedSeats = lockedSeats;
    }

    public Map<Integer, List<String>> getLockedSeats() {
        return lockedSeats;
    }
}
