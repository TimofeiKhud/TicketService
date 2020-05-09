package com.example.ticketservicenew.data.auth.login;

import io.reactivex.Completable;

public interface LoginRepository {
    Completable onLogin(String email, String password);

    void onLogout();

    String getUserName();

    void saveUserName(String userName);
}
