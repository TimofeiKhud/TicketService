package com.example.ticketservicenew.data.dto;

public class PasswordRecoveryDto {
    public String email;

    public PasswordRecoveryDto(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
