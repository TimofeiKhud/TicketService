package com.example.ticketservicenew.presentation.paymentsuccess.view;

import com.arellomobile.mvp.MvpView;
import com.example.ticketservicenew.business.model.BookingInfo;

public interface PaymentSuccessView extends MvpView {
    void showNextView();

    void showPaymentInfo(String title, BookingInfo info);
}
