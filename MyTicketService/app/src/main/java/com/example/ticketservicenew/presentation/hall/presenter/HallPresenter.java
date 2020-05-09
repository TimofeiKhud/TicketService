package com.example.ticketservicenew.presentation.hall.presenter;


import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.ticketservicenew.App;
import com.example.ticketservicenew.business.hall.HallInteractor;
import com.example.ticketservicenew.business.model.HallStructure;
import com.example.ticketservicenew.business.model.Seat;
import com.example.ticketservicenew.di.hall.HallModule;
import com.example.ticketservicenew.presentation.hall.view.HallView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.ticketservicenew.presentation.hall.view.HallFragment.TAG;

@InjectViewState
public class HallPresenter extends MvpPresenter<HallView> {
    public static final String TAG = HallPresenter.class.getName();
    @Inject
    HallInteractor interactor;
    Disposable bookingDisposable;

    public HallPresenter() {
        App.get().plusHall(new HallModule()).inject(this);
    }

    public Single<HallStructure> getHallStructure(String id, boolean isShort){
        return interactor.getHallStructure(id, isShort);
    }

    public void bookTickets(List<Seat> seats){

        if (seats.isEmpty()){
            getViewState().showNotificationToast("Please select seats for booking");
            return;
        }
        getViewState().showProgress();
        bookingDisposable = interactor.onSeatsBooked(seats)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> {Log.d(TAG, "booking complete");
                    onBookingSuccess(interactor.getEventId(), seats);})
                .doOnError(error-> {onBookingError(TAG + "booking error"); })
                .subscribe();
                //.subscribe(() -> {Log.d(TAG, "booking complete");
                //onBookingSuccess(interactor.getEventId(), seats);}, error-> onBookingError(TAG + "booking error"));
       //return interactor.onSeatsBooked(seats);


    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "on Destroy");
        super.onDestroy();
        if(bookingDisposable != null){
            bookingDisposable.dispose();
        }
        App.get().clearHallComponent();
    }

    private void onBookingSuccess(String eventId, List<Seat> seats){
        Log.d(TAG, "on booking success");
        getViewState().hideProgress();
        getViewState().showNextView(eventId, seats);
    }

    private void onBookingError(String error){
        getViewState().hideProgress();
        getViewState().showError(error);
    }

    public String getId() {
        return interactor.getEventId();
    }

}
