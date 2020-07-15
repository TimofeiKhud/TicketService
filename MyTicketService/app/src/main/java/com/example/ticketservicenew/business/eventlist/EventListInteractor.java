package com.example.ticketservicenew.business.eventlist;

import android.util.Pair;

import com.example.ticketservicenew.business.model.Event;


import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface EventListInteractor {
    void onSearchTextChanged(String newText);
    boolean onFilterSelect(Pair<Long, Long> dateRange, List<Integer> categories);
}
