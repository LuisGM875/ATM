package com.cash.atm.services;

import  com.cash.atm.dto.ATMDTO;
import  com.cash.atm.entity.ATMEntity;

public interface ATMServices {
    public String withdrawCash(Double amount);
    public ATMDTO convertToDto(ATMEntity entity);
    public ATMDTO getTotalEntity(Long id);
}
