package com.cash.atm.services;

import com.cash.atm.dto.ATMDTO;
import com.cash.atm.entity.ATMEntity;
import com.cash.atm.exceptions.EmptyInputException;
import com.cash.atm.exceptions.ExceedsATMMoney;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
//Implements dont use import
public class ATMService implements ATMServices{
    private static final List<Object[]> atmInitialized = new ArrayList<Object[]>();
    private final ATMEntity totalEntity = new ATMEntity();

    public ATMService() {
        initializeATM();
    }

    //Introduce a cash (coins and bill) in ATM
    public void initializeATM() {
        //fisrt: quantity of bills, second: denomination, third: type of bill
        Object[][] values = {
                {2.0, 1000.0, "Bill"},
                {5.0, 500.0, "Bill"},
                {10.0, 200.0, "Bill"},
                {20.0, 100.0, "Bill"},
                {30.0, 50.0, "Bill"},
                {40.0, 20.0, "Bill"},
                {50.0, 10.0, "Coins"},
                {100.0, 5.0, "Coins"},
                {200.0, 2.0, "Coins"},
                {300.0, 1.0, "Coins"},
                {100.0, 0.50, "Coins"}
        };
        double grandTotal = 0;

        for (Object[] value : values) {
            double total = (double)value[0] * (double)value[1];
            atmInitialized.add(new Object[]{value[0], value[1], value[2]});
            grandTotal += total;
        }
        totalEntity.setId(1);
        totalEntity.setTotal(grandTotal);
    }

    public ATMDTO convertToDto(ATMEntity entity) {
        ATMDTO dto = new ATMDTO();
        dto.setTotal( entity.getTotal());
        return dto;
    }


    public ATMDTO getTotalEntity(Long id) {
        return convertToDto(totalEntity);
    }

    public String withdrawCash(Double amount){
            if (amount == 0){
                return "This amount is null";
            }

            if (amount > totalEntity.getTotal()) {
                throw new ExceedsATMMoney("ATM");
            }

            double remainingAmount = amount;
            StringBuilder resultMessage = new StringBuilder("<br><p>ATM: </p>");
            List<Object[]> dispensedCash = new ArrayList<>();

            for (Object[] denom : atmInitialized) {
                double quantity = (double) denom[0];
                double denomination = (double) denom[1];
                String type = (String) denom[2];

                if (remainingAmount >= denomination && quantity > 0) {
                    double numNotes = Math.min(quantity, Math.floor(remainingAmount / denomination));
                    remainingAmount -= numNotes * denomination;
                    denom[0] = quantity - numNotes;
                    dispensedCash.add(new Object[]{numNotes, denomination, type});
                    resultMessage.append("<p> ").append((int)numNotes).append(" of ").append(denomination).append(" ").append(type).append("</p>");

                    if (remainingAmount == 0) {
                        break;
                    }
                }
            }

            if (remainingAmount > 0) {
                throw new ExceedsATMMoney("ATM");
            }

            totalEntity.setTotal(totalEntity.getTotal() - amount);

            resultMessage.append("<br><p>Tickets left:<p>");
            for (Object[] atm : atmInitialized) {
                double atmCount = (double)atm[0];
                double atmValue = (double)atm[1];
                String atmType = (String)atm[2];
                resultMessage.append("<p>Quantity: ").append((int)atmCount).append(" of ").append(atmValue).append(" ").append(atmType).append("</p>");
            }

            return resultMessage.toString();
        }
}