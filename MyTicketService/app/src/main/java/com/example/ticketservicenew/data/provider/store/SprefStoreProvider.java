package com.example.ticketservicenew.data.provider.store;

import android.content.Context;

public class SprefStoreProvider implements StoreProvider{
    public static final String SP_AUTH = "AUTH";
    public static final String TOKEN_KEY = "TOKEN";
    public static final String USERNAME_KEY = "USERNAME";
    private Context context;

    public SprefStoreProvider(Context context) {
        this.context = context;
    }

    @Override
    public boolean saveToken(String token) {
        return context.getSharedPreferences(SP_AUTH, Context.MODE_PRIVATE)
                .edit()
                .putString(TOKEN_KEY, token)
                .commit();
    }

    @Override
    public boolean clearToken() {
        return context.getSharedPreferences(SP_AUTH, Context.MODE_PRIVATE)
                .edit()
                .clear()
                .commit();
    }

    @Override
    public String getToken() {
        return context.getSharedPreferences(SP_AUTH, Context.MODE_PRIVATE)
                .getString(TOKEN_KEY, null);
    }

    @Override
    public String getUserName() {
        return context.getSharedPreferences(SP_AUTH, Context.MODE_PRIVATE)
                .getString(USERNAME_KEY, null);
    }

    @Override
    public boolean saveUserName(String userName) {
        return context.getSharedPreferences(SP_AUTH, Context.MODE_PRIVATE)
                .edit()
                .putString(USERNAME_KEY, userName)
                .commit();
    }
}
