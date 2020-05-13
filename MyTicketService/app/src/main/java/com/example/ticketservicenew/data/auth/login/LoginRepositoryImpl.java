package com.example.ticketservicenew.data.auth.login;

import com.example.ticketservicenew.data.dto.LoginInDto;
import com.example.ticketservicenew.data.dto.LoginOutDto;
import com.example.ticketservicenew.data.provider.store.StoreProvider;
import com.example.ticketservicenew.data.provider.web.Api;

import java.io.IOException;

import io.reactivex.Completable;
import retrofit2.Response;

public class LoginRepositoryImpl implements LoginRepository{
    private Api api;
    private StoreProvider storeProvider;

    public LoginRepositoryImpl(Api api, StoreProvider storeProvider) {
        this.api = api;
        this.storeProvider = storeProvider;
    }

    @Override
    public Completable onLogin(String email, String password) {
        LoginInDto dto = new LoginInDto(email, password);
        return Completable.fromSingle(api.login(dto).doOnSuccess(this::onLoginSuccess));
    }

    @Override
    public void onLogout() {
        if(storeProvider.getToken() != null){
            storeProvider.clearToken();
            storeProvider.clearUserName();
        }
    }

    @Override
    public String getUserName() {
        return storeProvider.getUserName();
    }

    @Override
    public void saveUserName(String userName) {
        storeProvider.saveUserName(userName);
    }

    private void onLoginSuccess(Response<LoginOutDto> response) throws IOException {
        if(response.isSuccessful()){
            storeProvider.saveToken(response.body().getToken());
        } else if (response.code() == 400 || response.code() == 401){
            throw new RuntimeException(response.errorBody().string());
        }else{
            throw new RuntimeException("Server error! Please call to support.");
        }
    }

}
