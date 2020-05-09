package com.example.ticketservicenew.di.shoppingcart;

import com.example.ticketservicenew.presentation.shoppingcart.presenter.ShoppingCartPresenter;

import dagger.Subcomponent;

@Subcomponent(modules = {ShoppingCartModule.class})
@ShoppingCartScope
public interface ShoppingCartComponent {
    void inject(ShoppingCartPresenter presenter);
}
