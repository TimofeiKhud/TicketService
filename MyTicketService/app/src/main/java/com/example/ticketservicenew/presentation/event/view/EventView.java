package com.example.ticketservicenew.presentation.event.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.ticketservicenew.business.model.Event;
import com.example.ticketservicenew.business.model.EventInfo;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface EventView extends MvpView {
    void showEvent(Event event);
    void showEventInfo(EventInfo eventInfo);
    @StateStrategyType(SkipStrategy.class)
    void showNextView(String eventId, int hallId);
}
