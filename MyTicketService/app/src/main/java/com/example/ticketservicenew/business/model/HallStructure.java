package com.example.ticketservicenew.business.model;

import android.util.Pair;

import java.util.List;
import java.util.Map;

public class HallStructure {
    Map<Pair<Double, String>, List<Integer>> priceRanges;
    Map<Integer, List<String>> lockedSeats;
    List<Pair<Double,String>> priceList;

    public HallStructure(Map<Pair<Double, String>, List<Integer>> priceRanges, Map<Integer, List<String>> lockedSeats, List<Pair<Double,String>> priceList) {
        this.priceRanges = priceRanges;
        this.lockedSeats = lockedSeats;
        this.priceList = priceList;
    }

    public Map<Pair<Double, String>, List<Integer>> getPriceRanges() {
        return priceRanges;
    }

    public Map<Integer, List<String>> getLockedSeats() {
        return lockedSeats;
    }

    public List<Pair<Double, String>> getPriceList() {
        return priceList;
    }
}
