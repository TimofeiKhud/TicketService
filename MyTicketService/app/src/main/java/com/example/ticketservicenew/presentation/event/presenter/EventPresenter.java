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
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class EventPresenter extends MvpPresenter<EventView> {
    CompositeDisposable compositeDisposable;
    @Inject
    EventInteractor interactor;

    public EventPresenter() {
        compositeDisposable = new CompositeDisposable();
        App.get().plusEvent(new EventModule()).inject(this);
    }

    public void onShowEvent(String eventId) {
        interactor.saveId(eventId);
        Disposable eventDisposable = interactor.getEvent(eventId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(event -> {
                    interactor.saveHallId(event.getHall());
                    getViewState().showEvent(event);
                });

        Disposable eventInfoDisposable = interactor.getEventInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(eventInfo -> {
                    getViewState().showEventInfo(eventInfo);
                });

        compositeDisposable.addAll(eventDisposable, eventInfoDisposable);
    }

    public void onBuyTicketsClicked() {
        getViewState().showNextView(interactor.getEventId(), interactor.getHallId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
        App.get().clearEventComponent();
    }
}
