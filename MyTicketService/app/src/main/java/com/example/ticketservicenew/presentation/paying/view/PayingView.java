package com.example.ticketservicenew.presentation.paying.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.ticketservicenew.business.model.Seat;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface PayingView extends MvpView {
    //
    //    @Override
    //    public void showProgress() {
    //        progressBar.setVisibility(View.VISIBLE);
    //        payBtn.setEnabled(false);
    //    }
    //
    //    @Override
    //    public void hideProgress() {
    //        progressBar.setVisibility(View.GONE);
    //        payBtn.setEnabled(true);
    //    }
    //
    void showNextView(String id, List<Seat> seats);

    //
    void showError(String error);
//    void showProgress();
//    void hideProgress();
//    @StateStrategyType(SingleStateStrategy.class)
//    void showNextView();
//    @StateStrategyType(SingleStateStrategy.class)
//    void showError(String error);
}
