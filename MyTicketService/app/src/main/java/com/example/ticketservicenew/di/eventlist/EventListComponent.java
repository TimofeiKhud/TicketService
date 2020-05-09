package com.example.ticketservicenew.di.eventlist;


import com.example.ticketservicenew.presentation.eventlist.presenter.EventListPresenter;

import dagger.Subcomponent;

@Subcomponent(modules = {EventListModule.class})
@EventListScope
public interface EventListComponent {
    void inject(EventListPresenter presenter);
}


