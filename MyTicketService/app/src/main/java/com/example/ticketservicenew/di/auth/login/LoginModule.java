package com.example.ticketservicenew.di.auth.login;

import com.example.ticketservicenew.business.auth.login.LoginInteractor;
import com.example.ticketservicenew.business.auth.login.LoginInteractorImpl;
import com.example.ticketservicenew.data.auth.login.LoginRepository;
import com.example.ticketservicenew.data.auth.login.LoginRepositoryImpl;
import com.example.ticketservicenew.data.provider.store.StoreProvider;
import com.example.ticketservicenew.data.provider.web.Api;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    @Provides
    @LoginScope
    LoginRepository provideLoginRepo(Api api, StoreProvider store){
        return new LoginRepositoryImpl(api, store);
    }

    @Provides
    @LoginScope
    LoginInteractor provideLoginInteractor(LoginRepository repository){
        return new LoginInteractorImpl(repository);
    }
}
