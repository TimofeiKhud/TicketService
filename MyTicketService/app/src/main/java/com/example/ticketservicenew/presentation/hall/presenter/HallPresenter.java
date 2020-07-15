package com.example.ticketservicenew.presentation.hall.presenter;


import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.ticketservicenew.App;
import com.example.ticketservicenew.business.hall.HallInteractor;
import com.example.ticketservicenew.business.model.Seat;
import com.example.ticketservicenew.di.hall.HallModule;
import com.example.ticketservicenew.presentation.hall.view.HallView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@InjectViewState
public class HallPresenter extends MvpPresenter<HallView> {
    public static final String TAG = HallPresenter.class.getName();

    @Inject
    HallInteractor interactor;
    Disposable bookingDisposable;
    Disposable getHallStructureDisposable;

    private double totalPrice;
    private int totalTickets;

    public HallPresenter() {
        App.get().plusHall(new HallModule()).inject(this);
    }

    public void onShowHallStructure(String eventId, int hallId) {
        interactor.saveId(eventId);
        getHallStructureDisposable = interactor.getHallStructure(eventId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(hallStructure -> {
                    Timber.d("on hall structure recieved: " + "event id: " + eventId);
                    interactor.savePriceList(hallStructure.getPriceList());
                    getViewState().showHallStructure(hallStructure, hallId);
                    getViewState().showPriceList(hallStructure.getPriceList());
                });
    }

    public void onBookTicketsClicked(List<Seat> seats){
        getViewState().showProgress();
        bookingDisposable = interactor.onSeatsBooking(seats)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    interactor.onBookingSuccess();
                    onBookingSuccess();},
                        error-> {onBookingError(error.getMessage()); });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getHallStructureDisposable.dispose();
        if(bookingDisposable != null){
            bookingDisposable.dispose();
        }
        App.get().clearHallComponent();
    }

    private void onBookingSuccess(){
        getViewState().hideProgress();
        getViewState().showNextView();
    }

    private void onBookingError(String error){
        getViewState().hideProgress();
        getViewState().showError(error);
    }

    public boolean onSeatClicked(boolean isSeatSelected, String seatInfo, int selectedSeatsNum) {
        Seat seat = mapStringToModel(seatInfo);
        if(isSeatSelected){
            try {
                interactor.onSeatSelected(selectedSeatsNum);
            }catch (RuntimeException exception){
                getViewState().showError(exception.getMessage());
                return false;
            }
            totalPrice += seat.getPrice();
            totalTickets++;
        }else{
            totalPrice -= seat.getPrice();
            totalTickets--;
        }
            getViewState().showSelectedSeatsInfo(isSeatSelected, seat, totalPrice, totalTickets);
        return true;
    }

    private Seat mapStringToModel(String seatInfo){
        String[] s = seatInfo.split("-");
        return new Seat(s[0], s[1], Double.parseDouble(s[2]));
    }
}
