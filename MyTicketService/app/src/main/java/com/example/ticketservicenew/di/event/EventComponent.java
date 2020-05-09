package com.example.ticketservicenew.di.event;

import com.example.ticketservicenew.presentation.event.presenter.EventPresenter;

import dagger.Subcomponent;

@Subcomponent(modules = {EventModule.class})
@EventScope
public interface EventComponent {
    void inject(EventPresenter presenter);
}
