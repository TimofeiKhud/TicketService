package com.example.ticketservicenew.presentation.eventlist;

import com.example.ticketservicenew.business.model.Event;

import java.util.List;

/**
 * To make an interaction between [EventListAdapter] & [EventListFragment]
 * */
public interface OnEventListAdapterListener {

        void showEvent(String eventId);

}
