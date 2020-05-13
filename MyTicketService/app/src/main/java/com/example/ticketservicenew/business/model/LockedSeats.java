package com.example.ticketservicenew.business.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class LockedSeats {
    Map<String, List<String>> lockedSeats;

    public LockedSeats(Map<String, List<String>> lockedSeats) {
        this.lockedSeats = lockedSeats;
    }

    public Map<String, List<String>> getLockedSeats() {
        return lockedSeats;
    }
}
