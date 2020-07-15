package com.example.ticketservicenew.presentation.auth.login.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.ticketservicenew.App;
import com.example.ticketservicenew.business.auth.login.LoginInteractor;
import com.example.ticketservicenew.di.auth.login.LoginModule;
import com.example.ticketservicenew.presentation.auth.AuthView;


import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class LoginPresenter extends MvpPresenter<AuthView> {
    @Inject
    LoginInteractor interactor;
    Disposable disposable;

    public LoginPresenter() {
        App.get().plusLogin(new LoginModule()).inject(this);
    }

    public void onLogin(String email, String password){
        getViewState().showProgress();
        disposable = interactor.onLogin(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> onSuccess(email), throwable -> onError(throwable.getMessage()));
    }

    public void onDialogClicked(){
        getViewState().hideError();
    }

    public void onSuccess(String userName){
        interactor.saveUserName(userName);
        getViewState().hideProgress();
        getViewState().showRegisteredUserState(userName);
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
        App.get().clearLoginComponent();
    }

    public void onLogout() {
        interactor.onLogout();
        getViewState().showNotRegisteredUserState();
    }

    public void onSetLoginState(){
        String userName = interactor.getUserName();
        if(userName == null){
            getViewState().showNotRegisteredUserState();
        }else{
            getViewState().showRegisteredUserState(userName);
        }
    }

    public void onRegistrationClicked() {
        getViewState().showNextView();
    }
}
