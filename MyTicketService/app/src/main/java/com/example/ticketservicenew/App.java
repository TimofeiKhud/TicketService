package com.example.ticketservicenew;

import android.app.Application;

import com.example.ticketservicenew.di.app.AppComponent;
import com.example.ticketservicenew.di.app.AppModule;

import com.example.ticketservicenew.di.app.DaggerAppComponent;
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

import timber.log.Timber;

public class App extends Application {
    private static App app;
private AppComponent appComponent;
private LoginComponent loginComponent;
private RegComponent regComponent;
private EventListComponent listComponent;
private EventComponent eventComponent;
private HallComponent hallComponent;
private ShoppingCartComponent shoppingCartComponent;
private PayingComponent payingComponent;
private PaymentSuccessComponent paymentSuccessComponent;

    public App() {
        app = this;
    }

    public static App get(){
        return app;
    }

    @Override
    public void onCreate() {
        Timber.plant(new Timber.DebugTree());
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        super.onCreate();
    }

    public LoginComponent plusLogin(LoginModule module){
        if(loginComponent == null){
            loginComponent = appComponent.plusLogin(module);
        }
        return loginComponent;
    }

    public RegComponent plusReg(RegModule module){
        if(regComponent == null){
            regComponent = appComponent.plusReg(module);
        }
        return regComponent;
    }

    public EventListComponent plusList(EventListModule module){
        if(listComponent == null){
            listComponent = appComponent.plusList(module);
        }
        return listComponent;
    }

    public EventComponent plusEvent(EventModule module){
        if(eventComponent == null){
            eventComponent = appComponent.plusEvent(module);
        }
        return eventComponent;
    }

    public HallComponent plusHall(HallModule module){
        if(hallComponent == null){
            hallComponent = appComponent.plusHall(module);
        }
        return hallComponent;
    }

    public ShoppingCartComponent plusShoppingCart(ShoppingCartModule module){
        if(shoppingCartComponent == null){
            shoppingCartComponent = appComponent.plusShoppingCart(module);
        }
        return shoppingCartComponent;
    }

    public PayingComponent plusPaying(PayingModule module){
        if(payingComponent == null){
            payingComponent = appComponent.plusPaying(module);
        }
        return payingComponent;
    }

    public PaymentSuccessComponent plusPaymentSuccess(PaymentSuccessModule module){
        if(paymentSuccessComponent == null){
            paymentSuccessComponent = appComponent.plusPaymentSuccess(module);
        }
        return paymentSuccessComponent;
    }

    public void clearRegComponent(){
        regComponent = null;
    }
    public void clearLoginComponent(){
        loginComponent = null;
    }
    public void clearEventListComponent(){
        listComponent = null;
    }
    public void clearEventComponent(){
        eventComponent = null;
    }
    public void clearHallComponent(){
        hallComponent = null;
    }
    public void clearShoppingCartComponent(){
        shoppingCartComponent = null;
    }
    public void clearPaying(){
        payingComponent = null;
    }
    public void clearPaymentSuccess(){
        paymentSuccessComponent = null;
    }
}
