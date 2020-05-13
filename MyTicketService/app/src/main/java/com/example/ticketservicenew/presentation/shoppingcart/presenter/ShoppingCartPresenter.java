package com.example.ticketservicenew.presentation.shoppingcart.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.ticketservicenew.App;
import com.example.ticketservicenew.business.model.Seat;
import com.example.ticketservicenew.business.shoppingcart.ShoppingCartInteractor;

import com.example.ticketservicenew.di.shoppingcart.ShoppingCartModule;
import com.example.ticketservicenew.presentation.shoppingcart.view.ShoppingCartView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

@InjectViewState
public class ShoppingCartPresenter extends MvpPresenter<ShoppingCartView> {
    private static final String TAG = ShoppingCartPresenter.class.getName();
@Inject
ShoppingCartInteractor interactor;
Disposable disposable;

    public ShoppingCartPresenter() {
        App.get().plusShoppingCart(new ShoppingCartModule()).inject(this);
    }

    public void onShowBookedSeats(String eventId, List<Seat> bookedSeats){
        if(eventId == null){
            return;
        }
        interactor.saveId(eventId);
        if(bookedSeats == null){
            return;
        }
        interactor.saveBookedSeats(bookedSeats);
        getViewState().setBookedSeats(bookedSeats);

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

    public void onPay() {
        getViewState().showNextView(interactor.getEventId(), interactor.getBookedSeats());
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
        App.get().clearShoppingCartComponent();
    }
}
