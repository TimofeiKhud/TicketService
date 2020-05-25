package com.example.ticketservicenew.presentation.paymentsuccess.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.ticketservicenew.App;
import com.example.ticketservicenew.business.model.BookingInfo;
import com.example.ticketservicenew.business.model.Seat;
import com.example.ticketservicenew.business.paying.PayingInteractor;
import com.example.ticketservicenew.business.paymentsuccess.PaymentSuccessInteractor;
import com.example.ticketservicenew.di.paying.PayingModule;
import com.example.ticketservicenew.di.paymentsuccess.PaymentSuccessModule;
import com.example.ticketservicenew.presentation.paying.presenter.PayingPresenter;
import com.example.ticketservicenew.presentation.paymentsuccess.view.PaymentSuccessView;

import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class PaymentSuccessPresenter extends MvpPresenter<PaymentSuccessView> {
    public static final String TAG = PayingPresenter.class.getName();

    @Inject
    PaymentSuccessInteractor interactor;

    public PaymentSuccessPresenter() {
        App.get().plusPaymentSuccess(new PaymentSuccessModule()).inject(this);
    }

    public void onShowPaymentInfo(String eventId, String title) {
        //interactor.saveId(eventId);
        BookingInfo info = interactor.getPaymentInfo(eventId);
        getViewState().showPaymentInfo(title, info);
    }

    public void onDownloadClicked() {
        //TODO download tickets in dwg format
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        interactor.clearBookingInfo();
        App.get().clearPaymentSuccess();
    }


}
