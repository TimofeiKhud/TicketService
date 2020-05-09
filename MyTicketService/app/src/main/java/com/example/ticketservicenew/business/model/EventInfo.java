package com.example.ticketservicenew.business.model;

public class EventInfo {
    private String eventId;
    private double maxPrice;
    private double minPrice;
    private int restTick;

    public EventInfo(String eventId, double maxPrice, double minPrice, int restTick) {
        this.eventId = eventId;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.restTick = restTick;
    }

    public String getEventId() {
        return eventId;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public int getRestTick() {
        return restTick;
    }

    @Override
    public String toString() {
        return "EventInfo{" +
                "eventId='" + eventId + '\'' +
                ", maxPrice=" + maxPrice +
                ", minPrice=" + minPrice +
                ", restTick=" + restTick +
                '}';
    }
}
