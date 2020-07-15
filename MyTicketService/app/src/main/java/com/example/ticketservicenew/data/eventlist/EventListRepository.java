package com.example.ticketservicenew.data.eventlist;

import android.util.Pair;

import androidx.lifecycle.LiveData;

import com.example.ticketservicenew.business.model.Event;
import com.example.ticketservicenew.data.dto.admin.EventOutputDto;
import com.example.ticketservicenew.data.eventlist.datasource.EventListDataSource;

import java.util.List;
import java.util.Observable;

import io.reactivex.Single;

public interface EventListRepository {
    Single<List<Event>> getEvents(int offset, int limit);
    void onSearchTextChanged(String newText);
    boolean onFilterSelect(Pair<Long, Long> dateRange, List<Integer> categories);
    void setOnFiltersChangedListener(OnFiltersChangedListener listener);
    void onInitialLoading();
}
