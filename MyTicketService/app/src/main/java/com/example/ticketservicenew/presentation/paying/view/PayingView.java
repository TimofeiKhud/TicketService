package com.example.ticketservicenew.presentation.paying.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.ticketservicenew.business.model.BookingInfo;
import com.example.ticketservicenew.business.model.Seat;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface PayingView extends MvpView {

    void showNextView(String id);

    void showError(String error);

    void showBookingInfo(String title, BookingInfo info);

    void showProgress();

    void hideProgress();

}
