package com.example.ticketservicenew.data.dto;

public class StatusDto {
    String status;

    public StatusDto() {
    }

    public StatusDto(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "RegResponseDto{" +
                "status='" + status + '\'' +
                '}';
    }
}
