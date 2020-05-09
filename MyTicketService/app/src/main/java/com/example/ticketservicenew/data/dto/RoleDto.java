package com.example.ticketservicenew.data.dto;

public class RoleDto {
    public String email;
    public String role;

    public RoleDto(String email, String role) {
        this.email = email;
        this.role = role;
    }

    public RoleDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
