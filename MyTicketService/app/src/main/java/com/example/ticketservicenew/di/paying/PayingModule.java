package com.example.ticketservicenew.di.paying;

import com.example.ticketservicenew.business.hall.HallInteractor;
import com.example.ticketservicenew.business.hall.HallInteractorImpl;
import com.example.ticketservicenew.business.paying.PayingInteractor;
import com.example.ticketservicenew.business.paying.PayingInteractorImpl;
import com.example.ticketservicenew.data.hall.HallRepository;
import com.example.ticketservicenew.data.hall.HallRepositoryImpl;
import com.example.ticketservicenew.data.paying.PayingRepository;
import com.example.ticketservicenew.data.paying.PayingRepositoryImpl;
import com.example.ticketservicenew.data.provider.store.StoreProvider;
import com.example.ticketservicenew.data.provider.web.Api;

import dagger.Module;
import dagger.Provides;

@Module
public class PayingModule {
    @Provides
    @PayingScope
    PayingRepository providePayingRepo(Api api, StoreProvider storeProvider){
        return new PayingRepositoryImpl(api, storeProvider);
    }

    @Provides
    @PayingScope
    PayingInteractor providePayingInteractor(PayingRepository repository){
        return new PayingInteractorImpl(repository);
    }
}
