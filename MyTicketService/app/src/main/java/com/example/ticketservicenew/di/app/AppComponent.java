package com.example.ticketservicenew.di.app;

import com.example.ticketservicenew.di.auth.login.LoginComponent;
import com.example.ticketservicenew.di.auth.login.LoginModule;
import com.example.ticketservicenew.di.auth.registration.RegComponent;
import com.example.ticketservicenew.di.auth.registration.RegModule;
import com.example.ticketservicenew.di.event.EventComponent;
import com.example.ticketservicenew.di.event.EventModule;
import com.example.ticketservicenew.di.eventlist.EventListComponent;
import com.example.ticketservicenew.di.eventlist.EventListModule;
import com.example.ticketservicenew.di.hall.HallComponent;
import com.example.ticketservicenew.di.hall.HallModule;
import com.example.ticketservicenew.di.paying.PayingComponent;
import com.example.ticketservicenew.di.paying.PayingModule;
import com.example.ticketservicenew.di.paymentsuccess.PaymentSuccessComponent;
import com.example.ticketservicenew.di.paymentsuccess.PaymentSuccessModule;
import com.example.ticketservicenew.di.shoppingcart.ShoppingCartComponent;
import com.example.ticketservicenew.di.shoppingcart.ShoppingCartModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    LoginComponent plusLogin(LoginModule module);
    RegComponent plusReg(RegModule module);
    EventListComponent plusList(EventListModule module);
    EventComponent plusEvent(EventModule module);
    HallComponent plusHall(HallModule module);
    ShoppingCartComponent plusShoppingCart(ShoppingCartModule module);
    PayingComponent plusPaying(PayingModule module);
    PaymentSuccessComponent plusPaymentSuccess(PaymentSuccessModule module);
}
