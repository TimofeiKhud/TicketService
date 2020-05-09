package com.example.ticketservicenew.presentation.auth.registration.presenter;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.ticketservicenew.App;
import com.example.ticketservicenew.business.auth.registration.RegInteractor;
import com.example.ticketservicenew.di.auth.registration.RegModule;
import com.example.ticketservicenew.presentation.auth.AuthView;


import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class RegPresenter extends MvpPresenter<AuthView> {
    @Inject
    RegInteractor interactor;
    Disposable disposable;

    public RegPresenter() {
        App.get().plusReg(new RegModule()).inject(this);
    }

    public void onRegistration(String email, String password){
        getViewState().showProgress();
        disposable = interactor.onRegistration(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> onSuccess(), throwable -> onError(throwable.getMessage()));
    }

    public void onDialogClicked(){
        getViewState().hideError();
    }

    public void onSuccess(){
        getViewState().hideProgress();
        getViewState().showNextView();
    }

    public void onError(String error){
        getViewState().hideProgress();
        getViewState().showError(error);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(disposable != null){
            disposable.dispose();
        }
        App.get().clearRegComponent();
    }
}