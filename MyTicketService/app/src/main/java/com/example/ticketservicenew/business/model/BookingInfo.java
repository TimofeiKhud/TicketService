package com.example.ticketservicenew.business.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains info about current booking
 */
public class BookingInfo {
   // private String eventId;
    private List<LockedSeats> lockedSeats;
    private int ticketsNum;
    private double totalPrice;

    public BookingInfo(/*String eventId,*/ List<LockedSeats> lockedSeats, double totalPrice) {
        //this.eventId = eventId;
        this.lockedSeats = lockedSeats;
        this.totalPrice = totalPrice;
        for(LockedSeats seats : lockedSeats){
            ticketsNum += seats.getSeats().size();
        }
    }

    public BookingInfo() {
        //this.eventId = eventId;
        lockedSeats = new ArrayList<>();
    }

    public void addBookedSeat(String row, String seat){
        for(LockedSeats seatsInRow : lockedSeats){
            if(seatsInRow.getRow().equals(row)){
                seatsInRow.addSeat(seat);
                ticketsNum++;
                return;
            }
        }
        List<String> seats = new ArrayList<>();
        seats.add(seat);
        ticketsNum++;
        lockedSeats.add(new LockedSeats(row, seats));
    }

//    public String getEventId() {
//        return eventId;
//    }

    public List<LockedSeats> getLockedSeats() {
        return lockedSeats;
    }

    public int getNumTicketsBooked(){
        return ticketsNum;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    //    public double getTotalPrice(List<Price> priceList){
//        double totalPrice = 0;
//            for(LockedSeats seats : lockedSeats){
//                boolean priceFound = false;
//                for(Price price : priceList) {
//                    if(priceFound){
//                        break;
//                    }
//                    for(String row : price.getRows()) {
//                        if (row.equals(seats.getRow())) {
//                            priceFound = true;
//                            totalPrice += price.getPrice() * seats.getSeats().size();
//                            break;
//                        }
//                    }
//                }
//            }
//        return totalPrice;
//    }
}
