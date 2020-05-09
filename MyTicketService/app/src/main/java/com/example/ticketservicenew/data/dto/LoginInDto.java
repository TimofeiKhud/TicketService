package com.example.ticketservicenew.data.dto;

public class LoginInDto {
    public String email;
    public String password;

    public LoginInDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginInDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
