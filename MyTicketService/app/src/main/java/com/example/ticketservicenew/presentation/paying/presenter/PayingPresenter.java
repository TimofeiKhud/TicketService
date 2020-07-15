package com.example.ticketservicenew.presentation.paying.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.ticketservicenew.App;
import com.example.ticketservicenew.business.model.BookingInfo;
import com.example.ticketservicenew.business.paying.PayingInteractor;
import com.example.ticketservicenew.di.paying.PayingModule;
import com.example.ticketservicenew.presentation.paying.view.PayingView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class PayingPresenter extends MvpPresenter<PayingView> {
    public static final String TAG = PayingPresenter.class.getName();
    Disposable disposable;
    @Inject
    PayingInteractor interactor;

    public PayingPresenter() {
        App.get().plusPaying(new PayingModule()).inject(this);
    }

    public void onShowBookingInfo(String eventId, String title) {
        BookingInfo info = interactor.getBookingInfo(eventId);
        getViewState().showBookingInfo(title, info);
    }

    public void onPaymentSuccess() {
        getViewState().showProgress();
        disposable = interactor.sellTickets()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> {
                    Log.d(TAG, "booking complete");
                    interactor.onSellSuccess();
                    onSellSuccess();})
                .doOnError(error-> {
                    onSellError(error.getMessage()); })
                .subscribe();
        getViewState().showNextView(interactor.getEventId());
    }

    private void onSellError(String message) {
        getViewState().hideProgress();

        getViewState().showError(message);
    }

    private void onSellSuccess() {
        getViewState().hideProgress();
        getViewState().showNextView(interactor.getEventId());
    }

    public double getTotalPrice(){
        return interactor.getTotalPrice();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(disposable != null) {
            disposable.dispose();
        }
        App.get().clearPaying();
    }
}
