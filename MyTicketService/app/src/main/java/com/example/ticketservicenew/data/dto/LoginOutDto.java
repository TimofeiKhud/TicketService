package com.example.ticketservicenew.data.dto;

import java.util.List;

public class LoginOutDto {
    String token;
    List<String> roles;

    public LoginOutDto(String token, List<String> roles) {
        this.token = token;
        this.roles = roles;
    }

    public LoginOutDto() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
