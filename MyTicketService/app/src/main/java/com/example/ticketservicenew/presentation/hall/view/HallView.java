package com.example.ticketservicenew.presentation.hall.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.ticketservicenew.business.model.Seat;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface HallView extends MvpView {
    void showProgress();
    void hideProgress();
    @StateStrategyType(SkipStrategy.class)
    void showNextView(String eventId, List<Seat> seats);
    @StateStrategyType(SingleStateStrategy.class)
    void showError(String error);
    @StateStrategyType(SingleStateStrategy.class)
    void showNotificationToast(String notification);
}
