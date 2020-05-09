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
    //Observable<List<Event>> onEventListLoading(int offset, int limit);

    //List<Integer> getCategoryFilter();
    //boolean setCategoryFilter(List<Integer> selected);
    //Pair<Long, Long> getDateFilter();
    //boolean setDateFilter(Pair<Long, Long> dates);
    //Single<List<Event>> onFiltersSet(Pair<Long, Long> dateRange, List<Integer> categories);
    Single<List<Event>> getEvents(int offset, int limit);
    void onSearchTextChanged(String newText);
    boolean onFilterSelect(Pair<Long, Long> dateRange, List<Integer> categories);
    void setOnFiltersChangedListener(OnFiltersChangedListener listener);

    void onInitialLoading();
    //void setDataSource(EventListDataSource dataSource);
    //String getSearchFilter();
    //boolean setSearchFilter(String searchNew);
}
