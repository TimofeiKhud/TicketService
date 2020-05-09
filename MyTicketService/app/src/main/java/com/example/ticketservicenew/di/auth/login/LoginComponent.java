package com.example.ticketservicenew.di.auth.login;



import com.example.ticketservicenew.presentation.auth.login.presenter.LoginPresenter;

import dagger.Subcomponent;

@Subcomponent(modules = {LoginModule.class})
@LoginScope
public interface LoginComponent {
    void inject(LoginPresenter presenter);
}
