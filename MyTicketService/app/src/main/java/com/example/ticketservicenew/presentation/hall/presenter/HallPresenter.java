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

//    public Single<HallStructure> getHallStructure(String id, boolean isShort){
//        return interactor.getHallStructure(id, isShort);
//    }

    public void onShowHallStructure(String eventId, int hallId) {
        interactor.saveId(eventId);
        getHallStructureDisposable = interactor.getHallStructure(eventId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(hallStructure -> {
                    Timber.d("on hall structure recieved: " + "event id: " + eventId);
                    interactor.savePriceList(hallStructure.getPriceList());
                    //Map<Pair<Double, String>, List<Integer>> priceRanges = hallStructure.getPriceRanges();
                    getViewState().showHallStructure(hallStructure, hallId);
//                    for(Map.Entry<Integer, List<String>> pair : lockedSeats.entrySet()){
//                        Log.d(TAG, "locked seats: row: " + pair.getKey() + "seat:" + pair.getValue());
//                    }
                    //List<Pair<Double,  String>> priceList = hallStructure.getPriceList();
                    getViewState().showPriceList(hallStructure.getPriceList());
//                    for (Pair<Double, String> pair : priceList){
//                        Log.d(TAG,"price: " + pair.first + "color: " + pair.second);
//                    }

                });
    }

    public void onBookTicketsClicked(List<Seat> seats){
//        if (seats.isEmpty()){
//            getViewState().showNotificationToast("Please select seats for booking");
//            return;
//        }
        getViewState().showProgress();
        bookingDisposable = interactor.onSeatsBooking(seats)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {Log.d(TAG, "booking complete");
                    interactor.onBookingSuccess();
                    onBookingSuccess();},
                        error-> {onBookingError(error.getMessage()); });
                //.subscribe(() -> {Log.d(TAG, "booking complete");
                //onBookingSuccess(interactor.getEventId(), seats);}, error-> onBookingError(TAG + "booking error"));
       //return interactor.onSeatsBooked(seats);


    }

//    public String getEventId() {
//        return interactor.getEventId();
//    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "on Destroy");
        super.onDestroy();
        getHallStructureDisposable.dispose();
        if(bookingDisposable != null){
            bookingDisposable.dispose();
        }
        App.get().clearHallComponent();
    }

    private void onBookingSuccess(){
        Log.d(TAG, "on booking success");
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
