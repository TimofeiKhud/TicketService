package com.example.ticketservicenew.business.model;

import android.util.Pair;

import java.util.List;
import java.util.Map;

/**
 * Contains price list and list of booked/sold seats for particular event
 */
public class HallStructure {
    List<Price> priceList;
    List<LockedSeats> lockedSeats;

    public HallStructure(List<Price> priceList, List<LockedSeats> lockedSeats) {
        this.priceList = priceList;
        this.lockedSeats = lockedSeats;
    }

    public List<Price> getPriceList() {
        return priceList;
    }

    public List<LockedSeats> getLockedSeats() {
        return lockedSeats;
    }

}
