package com.example.ticketservicenew.business.model;

import android.util.Pair;

import java.util.List;
import java.util.Map;

/**
 * Contains price list and list of booked/sold seats for particular event
 */
public class HallStructure {
    //Map<Pair<Double, String>, List<String>> priceRanges;
    //Map<Integer, List<String>> lockedSeats;
    //List<Pair<Double,String>> priceList;
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

    //    public HallStructure(Map<Pair<Double, String>, List<String>> priceRanges, Map<Integer, List<String>> lockedSeats, List<Pair<Double,String>> priceList) {
//        this.priceRanges = priceRanges;
//        this.lockedSeats = lockedSeats;
//        this.priceList = priceList;
//    }
//
//    public Map<Pair<Double, String>, List<String>> getPriceRanges() {
//        return priceRanges;
//    }
//
//    public Map<Integer, List<String>> getLockedSeats() {
//        return lockedSeats;
//    }
//
//    public List<Pair<Double, String>> getPriceList() {
//        return priceList;
//    }
}
