package com.example.ticketservicenew.data.dto;

public class ErrorDto {
    int code;
    String message;

    public ErrorDto(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorDto() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

