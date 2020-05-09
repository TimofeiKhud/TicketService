package com.example.ticketservicenew.business.auth.registration;

import com.example.ticketservicenew.business.auth.EmailValidException;
import com.example.ticketservicenew.business.auth.PasswordValidException;
import com.example.ticketservicenew.data.auth.registration.RegRepository;
import com.example.ticketservicenew.data.provider.web.Api;

import io.reactivex.Completable;

public class RegInteractorImpl implements RegInteractor{
RegRepository repository;

    public RegInteractorImpl(RegRepository repository) {
        this.repository = repository;
    }

    @Override
    public Completable onRegistration(String email, String password) {
        try {
            isEmailValid(email);
            isPasswordValid(password);
            return repository.onRegistration(email, password);
        }catch (Throwable throwable){
            return Completable.error(throwable);
        }
    }

    private void isEmailValid(String email){
        if(!email.contains("@")){
            throw new EmailValidException("Email must contain @");
        }
        if(email.length() - email.indexOf(".") < 2){
            throw new EmailValidException("Email must contain at least 2 symbols after last dot");
        }
    }

    private void isPasswordValid(String password){
        if(password.length() < 4){
            throw new PasswordValidException("Password length must be at least 3 symbols");
        }
    }
}
