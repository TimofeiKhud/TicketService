package com.example.ticketservicenew.data.eventlist;

public class EndOfListException extends RuntimeException{

        public EndOfListException() {
            super("End of list!");
        }
}
