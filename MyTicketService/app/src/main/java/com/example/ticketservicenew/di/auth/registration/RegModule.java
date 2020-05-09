package com.example.ticketservicenew.di.auth.registration;

import com.example.ticketservicenew.business.auth.registration.RegInteractor;
import com.example.ticketservicenew.business.auth.registration.RegInteractorImpl;
import com.example.ticketservicenew.data.auth.registration.RegRepository;
import com.example.ticketservicenew.data.auth.registration.RegRepositoryImpl;
import com.example.ticketservicenew.data.provider.store.StoreProvider;
import com.example.ticketservicenew.data.provider.web.Api;

import dagger.Module;
import dagger.Provides;

@Module
public class RegModule {

    @Provides
    @RegScope
    RegRepository provideRegRepo(Api api){
        return new RegRepositoryImpl(api);
    }

    @Provides
    @RegScope
    RegInteractor provideRegInteractor(RegRepository repository){
        return new RegInteractorImpl(repository);
    }
}
