package com.example.ticketservicenew.data.dto;

import com.example.ticketservicenew.data.dto.admin.PriceRangeDto;
import com.example.ticketservicenew.data.dto.objectsForDto.LockedSeatsDto;

import java.util.List;

public class HallStructureDto {
    List<LockedSeatsDto> lockedSeats;
    List<PriceRangeDto> priceRanges;

    public HallStructureDto() {
    }

    public HallStructureDto(List<LockedSeatsDto> lockedSeats, List<PriceRangeDto> priceRanges) {
        this.lockedSeats = lockedSeats;
        this.priceRanges = priceRanges;
    }

    public List<LockedSeatsDto> getLockedSeats() {
        return lockedSeats;
    }

    public List<PriceRangeDto> getPriceRanges() {
        return priceRanges;
    }
}
