package com.example.ticketservicenew.di.hall;

import com.example.ticketservicenew.presentation.hall.presenter.HallPresenter;

import dagger.Subcomponent;

@Subcomponent(modules = {HallModule.class})
@HallScope
public interface HallComponent {
    void inject(HallPresenter presenter);
}
