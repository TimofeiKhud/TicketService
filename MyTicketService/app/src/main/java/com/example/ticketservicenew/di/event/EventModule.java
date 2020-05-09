package com.example.ticketservicenew.di.event;

import com.example.ticketservicenew.business.event.EventInteractor;
import com.example.ticketservicenew.business.event.EventInteractorImpl;
import com.example.ticketservicenew.data.event.EventRepository;
import com.example.ticketservicenew.data.event.EventRepositoryImpl;
import com.example.ticketservicenew.data.provider.web.Api;

import dagger.Module;
import dagger.Provides;

@Module
public class EventModule {
    @Provides
    @EventScope
    EventRepository provideEventRepo(Api api){
        return new EventRepositoryImpl(api);
    }

    @Provides
    @EventScope
    EventInteractor provideEventInteractor(EventRepository repository){
        return new EventInteractorImpl(repository);
    }
}
