package com.example.ticketservicenew.presentation.eventlist.view;

import android.view.View;

import androidx.paging.PagedList;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.ticketservicenew.business.model.Event;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface EventListView extends MvpView {
    void showFilters();
    void onEventClicked();
    void showEvents(PagedList<Event> filteredEvents);
    void showProgress();
    void hideProgress();

    //int getLastVisibleEventPosition(); void scrollEventList(RecyclerView recyclerview)
}
