package com.example.ticketservicenew.data.provider.store;

public interface StoreProvider {
    boolean saveToken(String token);
    boolean clearToken(); //clear token and username
    String getToken();

    String getUserName();

    boolean saveUserName(String userName);
}
