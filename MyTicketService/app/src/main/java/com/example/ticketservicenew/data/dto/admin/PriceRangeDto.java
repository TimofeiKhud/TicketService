package com.example.ticketservicenew.data.dto.admin;

import java.util.List;

public class PriceRangeDto {
    public String color;
    public double price;
    public List<String> rows;

    public PriceRangeDto() {
    }

    public PriceRangeDto(String color, double price, List<String> rows) {
        this.color = color;
        this.price = price;
        this.rows = rows;
    }

    public String getColor() {
        return color;
    }

    public double getPrice() {
        return price;
    }

    public List<String> getRows() {
        return rows;
    }
}
