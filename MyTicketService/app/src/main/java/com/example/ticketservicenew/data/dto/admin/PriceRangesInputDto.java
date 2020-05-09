package com.example.ticketservicenew.data.dto.admin;
import com.example.ticketservicenew.data.dto.objectsForDto.PriceRanges;

import java.util.Set;

public class PriceRangesInputDto {
    Set<PriceRanges> priceRanges;


    public PriceRangesInputDto(Set<PriceRanges> priceRanges) {
        this.priceRanges = priceRanges;
    }

    public PriceRangesInputDto() {
    }

    public Set<PriceRanges> getPriceRanges() {
        return priceRanges;
    }

    public void setPriceRanges(Set<PriceRanges> priceRanges) {
        this.priceRanges = priceRanges;
    }
}
