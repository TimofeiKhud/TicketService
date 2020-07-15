package com.example.ticketservicenew.presentation.shoppingcart.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.ticketservicenew.App;
import com.example.ticketservicenew.business.model.BookingInfo;
import com.example.ticketservicenew.business.model.Seat;
import com.example.ticketservicenew.business.shoppingcart.ShoppingCartInteractor;

import com.example.ticketservicenew.di.shoppingcart.ShoppingCartModule;
import com.example.ticketservicenew.presentation.shoppingcart.view.ShoppingCartView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ShoppingCartPresenter extends MvpPresenter<ShoppingCartView> {
    private static final String TAG = ShoppingCartPresenter.class.getName();
@Inject
ShoppingCartInteractor interactor;
Disposable disposable;
    Disposable eventDisposable;

    public ShoppingCartPresenter() {
        App.get().plusShoppingCart(new ShoppingCartModule()).inject(this);
    }

    public void onShowBookingInfo(/*String eventId, List<Seat> bookedSeats*/){
        BookingInfo info = interactor.getBookingInfo();
        if(info == null){
            getViewState().showEmptyCart();
            return;
        }

        getViewState().showBookingInfo(info);
        eventDisposable = interactor.getEvent()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(event -> {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM", Locale.US);
                    String date = dateFormat.format(event.getEventStart());
                    getViewState().showTitle(event.getArtist() + " | " + event.getEventName() + " | " + date);
                });
    }

    //confirm booking cancel
    public void onDialogConfirm() {
        disposable = interactor.onBookingCancel()
                .subscribe(() -> {
                    Log.d(TAG, "booking complete");
                    getViewState().showPrewView();
                },
                        error -> Log.d(TAG, "booking cancel error: " + error.getMessage())
                );
    }

    public void onDialogCancel() {
        getViewState().hideConfirmationDialog();
    }

    public void onPayClicked() {
        BookingInfo info = interactor.getBookingInfo();
        getViewState().showNextView(interactor.getEventId(), info.getNumTicketsBooked(), (float)info.getTotalPrice());
    }

    public void onDeleteSelectionClicked() {
        getViewState().showConfirmationDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(disposable != null){
            disposable.dispose();
        }
        if(eventDisposable != null){
            eventDisposable.dispose();
        }
        App.get().clearShoppingCartComponent();
    }
}
