package com.example.ticketservicenew.di.hall;

import com.example.ticketservicenew.business.hall.HallInteractor;
import com.example.ticketservicenew.business.hall.HallInteractorImpl;
import com.example.ticketservicenew.data.hall.HallRepository;
import com.example.ticketservicenew.data.hall.HallRepositoryImpl;
import com.example.ticketservicenew.data.provider.store.StoreProvider;
import com.example.ticketservicenew.data.provider.web.Api;

import dagger.Module;
import dagger.Provides;

@Module
public class HallModule {
    @Provides
    @HallScope
    HallRepository provideHallRepo(Api api, StoreProvider store){
        return new HallRepositoryImpl(api, store);
    }

    @Provides
    @HallScope
    HallInteractor provideHallInteractor(HallRepository repository){
        return new HallInteractorImpl(repository);
    }
}
