package com.example.ticketservicenew.business.auth.registration;

import io.reactivex.Completable;
public interface RegInteractor {
    Completable onRegistration(String email, String password);
}
