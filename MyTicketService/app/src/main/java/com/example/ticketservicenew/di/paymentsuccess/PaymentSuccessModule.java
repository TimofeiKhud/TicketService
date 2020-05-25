package com.example.ticketservicenew.di.paymentsuccess;

import com.example.ticketservicenew.business.paying.PayingInteractor;
import com.example.ticketservicenew.business.paying.PayingInteractorImpl;
import com.example.ticketservicenew.business.paymentsuccess.PaymentSuccessInteractor;
import com.example.ticketservicenew.business.paymentsuccess.PaymentSuccessInteractorImpl;
import com.example.ticketservicenew.data.paying.PayingRepository;
import com.example.ticketservicenew.data.paying.PayingRepositoryImpl;
import com.example.ticketservicenew.data.paymentsuccess.PaymentSuccesRepository;
import com.example.ticketservicenew.data.paymentsuccess.PaymentSuccessRepositoryImpl;
import com.example.ticketservicenew.data.provider.store.StoreProvider;
import com.example.ticketservicenew.data.provider.web.Api;

import dagger.Module;
import dagger.Provides;

@Module
public class PaymentSuccessModule {
    @Provides
    @PaymentSuccessScope
    PaymentSuccesRepository providePaymentSuccessRepo(Api api, StoreProvider storeProvider){
        return new PaymentSuccessRepositoryImpl(api, storeProvider);
    }

    @Provides
    @PaymentSuccessScope
    PaymentSuccessInteractor providePaymentSuccessInteractor(PaymentSuccesRepository repository){
        return new PaymentSuccessInteractorImpl(repository);
    }
}
