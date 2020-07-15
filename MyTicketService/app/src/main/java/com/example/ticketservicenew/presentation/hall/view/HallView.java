package com.example.ticketservicenew.presentation.hall.view;

import android.util.Pair;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.ticketservicenew.business.model.HallStructure;
import com.example.ticketservicenew.business.model.LockedSeats;
import com.example.ticketservicenew.business.model.Price;
import com.example.ticketservicenew.business.model.Seat;

import java.util.List;
import java.util.Map;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface HallView extends MvpView {
    void showProgress();
    void hideProgress();
    @StateStrategyType(SkipStrategy.class)
    void showNextView();
    @StateStrategyType(SingleStateStrategy.class)
    void showError(String error);

    @StateStrategyType(SingleStateStrategy.class)
    void showHallStructure(HallStructure hallStructure, int hallId);

    @StateStrategyType(SingleStateStrategy.class)
    void showPriceList(List<Price> priceList);

    @StateStrategyType(SkipStrategy.class)
    void showSelectedSeatsInfo(boolean isSelected, Seat seat, double totalPrice, int totalTickets);

}
