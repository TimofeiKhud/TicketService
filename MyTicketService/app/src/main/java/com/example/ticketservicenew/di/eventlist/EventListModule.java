package com.example.ticketservicenew.di.eventlist;

import com.example.ticketservicenew.business.eventlist.EventListInteractor;
import com.example.ticketservicenew.business.eventlist.EventListInteractorImpl;
import com.example.ticketservicenew.data.dto.mapper.EventBookingDtoMapper;
import com.example.ticketservicenew.data.eventlist.EventListRepository;
import com.example.ticketservicenew.data.eventlist.EventListRepositoryImpl;
import com.example.ticketservicenew.data.eventlist.OnFiltersChangedListener;
import com.example.ticketservicenew.data.eventlist.datasource.EventListDataSourceFactory;
import com.example.ticketservicenew.data.provider.web.Api;


import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class EventListModule {

    @Provides
    @EventListScope
    EventBookingDtoMapper provideEventBookingDtoMapper(){
        return new EventBookingDtoMapper();
    }

    @Provides
    @EventListScope
    EventListRepository provideEventListRepo(Api api, EventBookingDtoMapper mapper){
        return new EventListRepositoryImpl(api, mapper);
    }

    @Provides
    @EventListScope
    CompositeDisposable provideCompositeDisposable(){
        return new CompositeDisposable();
    }

    @Provides
    @EventListScope
    EventListDataSourceFactory provideEventListDataSourceFactory(EventListRepository repository, CompositeDisposable disposable){
        return new EventListDataSourceFactory(repository, disposable);
    }

    @Provides
    @EventListScope
    EventListInteractor provideEventListInteractor(EventListRepository repository){
        return new EventListInteractorImpl(repository);
    }

}
