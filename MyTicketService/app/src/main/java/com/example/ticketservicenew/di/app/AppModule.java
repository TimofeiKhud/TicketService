package com.example.ticketservicenew.di.app;

import android.content.Context;

import com.example.ticketservicenew.data.provider.store.SprefStoreProvider;
import com.example.ticketservicenew.data.provider.store.StoreProvider;
import com.example.ticketservicenew.data.provider.web.Api;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {
    private final Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideContext(){
        return context;
    }

    @Provides
    @Singleton
    Gson provideGson(){
        return new Gson();
    }

    @Provides
    @Singleton
    OkHttpClient provideHttpClient(){
        return new OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl("https://ticketserviceapp.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    Api provideApi(Retrofit retrofit){
        return retrofit.create(Api.class);

    }

    @Provides
    @Singleton
    StoreProvider provideStore(Context context){
        return new SprefStoreProvider(context);
    }

}
