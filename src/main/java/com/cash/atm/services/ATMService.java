package com.cash.atm.services;

import com.cash.atm.dto.ATMDTO;
import com.cash.atm.entity.ATMEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ATMService {
    private static final List<Object[]> atmInitialized = new ArrayList<Object[]>();
    private final ATMEntity totalEntity = new ATMEntity();

    public ATMService() {
        initializeATM();
    }

    //Introduce a cash (coins and bill) in ATM
    public void initializeATM() {
        //fisrt: quantity of bills, second: denomination, third: type of bill
        Object[][] values = {
                {2.0, 1000.0, "Billete"},
                {5.0, 500.0, "Billete"},
                {10.0, 200.0, "Billete"},
                {20.0, 100.0, "Billete"},
                {30.0, 50.0, "Billete"},
                {40.0, 20.0, "Billete"},
                {50.0, 10.0, "Moneda"},
                {100.0, 5.0, "Moneda"},
                {200.0, 2.0, "Moneda"},
                {300.0, 1.0, "Moneda"},
                {100.0, 0.50, "Moneda"}
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

    public String withdrawCash(double amount) {

            if (amount > totalEntity.getTotal()) {
                return "Insufficient funds in the ATM.";
            }

            double remainingAmount = amount;
            StringBuilder resultMessage = new StringBuilder("<br><p>ATM regreso: </p>");
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
                    resultMessage.append("<p> ").append((int)numNotes).append(" de ").append(denomination).append(" ").append(type).append("</p>");

                    if (remainingAmount == 0) {
                        break;
                    }
                }
            }

            if (remainingAmount > 0) {
                return "Unable to dispense the requested amount with available denominations.";
            }

            totalEntity.setTotal(totalEntity.getTotal() - amount);

            resultMessage.append("<br><p>Billetes que quedan:<p>");
            for (Object[] atm : atmInitialized) {
                double atmCount = (double)atm[0];
                double atmValue = (double)atm[1];
                String atmType = (String)atm[2];
                resultMessage.append("<p>Cantidad: ").append((int)atmCount).append(" de ").append(atmValue).append(atmType).append("</p>");
            }

            return resultMessage.toString();
        }
}