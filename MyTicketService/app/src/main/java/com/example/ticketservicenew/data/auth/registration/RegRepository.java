package com.example.ticketservicenew.data.auth.registration;

import io.reactivex.Completable;

public interface RegRepository {
    Completable onRegistration(String email, String password);
}
