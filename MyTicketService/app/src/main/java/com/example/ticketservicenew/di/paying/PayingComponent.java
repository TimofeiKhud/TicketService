package com.example.ticketservicenew.di.paying;

import com.example.ticketservicenew.presentation.hall.presenter.HallPresenter;
import com.example.ticketservicenew.presentation.paying.presenter.PayingPresenter;

import dagger.Subcomponent;

@Subcomponent(modules = {PayingModule.class})
@PayingScope
public interface PayingComponent {
    void inject(PayingPresenter presenter);
}
