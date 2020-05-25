package com.example.ticketservicenew.presentation.shoppingcart.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.ticketservicenew.business.model.BookingInfo;
import com.example.ticketservicenew.business.model.LockedSeats;
import com.example.ticketservicenew.business.model.Seat;

import java.util.List;
import java.util.Map;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface ShoppingCartView extends MvpView {

    void showProgress();

    void hideProgress();

    void showTitle(String title);

    @StateStrategyType(SkipStrategy.class)
    void showNextView(String eventId, int ticketsNum, float totalPrice);

    @StateStrategyType(SingleStateStrategy.class)
    void showError(String error);

    void showConfirmationDialog();

    void showPrewView();

    void hideConfirmationDialog();

    void showBookingInfo(BookingInfo info);

    void showEmptyCart();
}
