package com.example.ticketservicenew.presentation.shoppingcart.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.ticketservicenew.business.model.Seat;

import java.util.List;
import java.util.Map;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface ShoppingCartView extends MvpView {

    void showProgress();

    void hideProgress();

    @StateStrategyType(SingleStateStrategy.class)
    void showNextView(String id, List<Seat> seats);

    @StateStrategyType(SingleStateStrategy.class)
    void showError(String error);

    void showConfirmationDialog();

    void showPrewView();

    void hideConfirmationDialog();

    void setBookedSeats(List<Seat> seats);
}
