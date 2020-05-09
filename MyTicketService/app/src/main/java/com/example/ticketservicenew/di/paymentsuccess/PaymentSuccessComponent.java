package com.example.ticketservicenew.di.paymentsuccess;

import com.example.ticketservicenew.presentation.paymentsuccess.presenter.PaymentSuccessPresenter;

import dagger.Subcomponent;

@Subcomponent(modules = {PaymentSuccessModule.class})
@PaymentSuccessScope
public interface PaymentSuccessComponent {
    void inject(PaymentSuccessPresenter presenter);
}
