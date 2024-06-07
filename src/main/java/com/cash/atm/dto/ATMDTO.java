package com.cash.atm.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ATMDTO {
    private double total;


    @Override
    public String toString() {
        return String.valueOf(total);
    }
}
