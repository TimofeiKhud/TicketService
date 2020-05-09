package com.example.ticketservicenew.data.auth.registration;

import com.example.ticketservicenew.data.dto.StatusDto;
import com.example.ticketservicenew.data.dto.RegUserDto;
import com.example.ticketservicenew.data.provider.web.Api;

import java.io.IOException;

import io.reactivex.Completable;
import retrofit2.Response;

public class RegRepositoryImpl implements RegRepository{
    Api api;

    public RegRepositoryImpl(Api api) {
        this.api = api;
    }

    @Override
    public Completable onRegistration(String email, String password) {
        RegUserDto dto = new RegUserDto(1, "Test", "Test", email, password, "+1234567");
        return Completable.fromSingle(api.registration(dto).doOnSuccess((response) -> onRegistrationSuccess(response)));
    }

    private void onRegistrationSuccess(Response<StatusDto> response) throws IOException {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(response.isSuccessful()){

        }else if(response.code() == 400 || response.code() == 409){
            throw new RuntimeException(response.errorBody().string());
        }else{
            throw new RuntimeException("Server error! Please call to support!");
        }
    }
}
