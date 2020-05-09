package com.example.ticketservicenew.presentation.paying.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.ticketservicenew.App;
import com.example.ticketservicenew.business.model.Seat;
import com.example.ticketservicenew.business.paying.PayingInteractor;
import com.example.ticketservicenew.di.paying.PayingModule;
import com.example.ticketservicenew.presentation.paying.view.PayingView;

import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class PayingPresenter extends MvpPresenter<PayingView> {
    public static final String TAG = PayingPresenter.class.getName();

    @Inject
    PayingInteractor interactor;

    public PayingPresenter() {
        App.get().plusPaying(new PayingModule()).inject(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        App.get().clearPaying();
    }

    public void setBookedSeats(String eventId, List<Seat> seats) {
        if(eventId == null){
            return;
        }
        interactor.saveId(eventId);
        if(seats == null){
            return;
        }
        interactor.saveBookedSeats(seats);
    }

    public void onPaymentSuccess() {
        interactor.sellTickets();
        getViewState().showNextView(interactor.getEventId(), interactor.getBookedSeats());
    }
}
