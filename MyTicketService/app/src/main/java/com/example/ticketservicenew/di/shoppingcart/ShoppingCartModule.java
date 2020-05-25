package com.example.ticketservicenew.di.shoppingcart;

import com.example.ticketservicenew.business.shoppingcart.ShoppingCartInteractor;
import com.example.ticketservicenew.business.shoppingcart.ShoppingCartInteractorImpl;
import com.example.ticketservicenew.data.provider.store.StoreProvider;
import com.example.ticketservicenew.data.provider.web.Api;
import com.example.ticketservicenew.data.shoppingcart.ShoppingCartRepository;
import com.example.ticketservicenew.data.shoppingcart.ShoppingCartRepositoryImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class ShoppingCartModule {
    @Provides
    @ShoppingCartScope
    ShoppingCartRepository provideShoppingCartRepo(Api api, StoreProvider store){
        return new ShoppingCartRepositoryImpl(api, store);
    }

    @Provides
    @ShoppingCartScope
    ShoppingCartInteractor provideShoppingCartInteractor(ShoppingCartRepository repository){
        return new ShoppingCartInteractorImpl(repository);
    }
}
