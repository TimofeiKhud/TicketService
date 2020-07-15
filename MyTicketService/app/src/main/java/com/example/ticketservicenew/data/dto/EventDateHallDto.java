package com.example.ticketservicenew.data.dto;

import java.util.List;

public class EventDateHallDto {
    public long dateFrom;
    public long dateTo;

    public EventDateHallDto(long dateFrom, long dateTo) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public EventDateHallDto() {
    }

    public long getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(long dateFrom) {
        this.dateFrom = dateFrom;
    }

    public long getDateTo() {
        return dateTo;
    }

    public void setDateTo(long dateTo) {
        this.dateTo = dateTo;
    }

    public static class LoginOutDto {
        public String token;
        public List<String> roles;

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
}
