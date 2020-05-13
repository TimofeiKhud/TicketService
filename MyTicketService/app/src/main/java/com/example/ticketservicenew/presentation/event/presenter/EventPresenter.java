package com.example.ticketservicenew.presentation.event.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.ticketservicenew.App;
import com.example.ticketservicenew.R;
import com.example.ticketservicenew.business.event.EventInteractor;
import com.example.ticketservicenew.business.model.Event;
import com.example.ticketservicenew.business.model.EventInfo;
import com.example.ticketservicenew.di.event.EventModule;
import com.example.ticketservicenew.presentation.event.view.EventView;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class EventPresenter extends MvpPresenter<EventView> {
    @Inject
    EventInteractor interactor;

    public EventPresenter() {
        App.get().plusEvent(new EventModule()).inject(this);
    }

    public Single<Event> getEvent(String id){
        return interactor.getEvent(id);
    }

    public Single<EventInfo> getEventInfo(String id){return interactor.getEventInfo(id);}

    @Override
    public void onDestroy() {
        super.onDestroy();
        App.get().clearEventComponent();
    }

    public void onShowEvent(String eventId) {
        Disposable disposable = interactor.getEvent(eventId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(eventInfo -> {
                    ticketsAvailable.setText(String.format("%s%d", getString(R.string.tickets_available), eventInfo.getRestTick()));
                    priceRange.setText(String.format("Price range: %s € - %s €", eventInfo.getMinPrice(), eventInfo.getMaxPrice()));
                });
    }
}
