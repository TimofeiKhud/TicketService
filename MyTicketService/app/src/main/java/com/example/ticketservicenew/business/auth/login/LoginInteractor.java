package com.example.ticketservicenew.business.auth.login;

import io.reactivex.Completable;

public interface LoginInteractor {
    Completable onLogin(String email, String password);
    void onLogout();
    String getUserName();
    void saveUserName(String userName);
}
