package com.example.ticketservicenew.data.dto.admin;


import com.example.ticketservicenew.data.dto.objectsForDto.ColorProgress;

import java.util.List;

public class ProgressDto {
    List<ColorProgress> cprogress;

    public ProgressDto(List<ColorProgress> cprogress) {
        this.cprogress = cprogress;
    }

    public ProgressDto() {
    }

    public List<ColorProgress> getCprogress() {
        return cprogress;
    }

    public void setCprogress(List<ColorProgress> cprogress) {
        this.cprogress = cprogress;
    }
}
