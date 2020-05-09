package com.example.ticketservicenew.business.auth.login;

import com.example.ticketservicenew.business.auth.EmailValidException;
import com.example.ticketservicenew.business.auth.PasswordValidException;
import com.example.ticketservicenew.data.auth.login.LoginRepository;

import io.reactivex.Completable;

public class LoginInteractorImpl implements LoginInteractor{
    LoginRepository repository;

    public LoginInteractorImpl(LoginRepository repository) {
        this.repository = repository;
    }

    @Override
    public Completable onLogin(String email, String password) {
        try{
            isEmailValid(email);
            isPasswordValid(password);
            return repository.onLogin(email, password);
        }catch (Throwable throwable){
            return Completable.error(throwable);
        }
    }

    @Override
    public void onLogout() {
       repository.onLogout();
    }

    @Override
    public String getUserName() {
        return repository.getUserName();
    }

    @Override
    public void saveUserName(String userName) {
        repository.saveUserName(userName);
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
