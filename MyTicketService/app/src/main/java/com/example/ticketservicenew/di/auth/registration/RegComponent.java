package com.example.ticketservicenew.di.auth.registration;

import com.example.ticketservicenew.presentation.auth.registration.presenter.RegPresenter;
import dagger.Subcomponent;

@Subcomponent(modules = {RegModule.class})
@RegScope
public interface RegComponent {
    void inject(RegPresenter presenter);
}
