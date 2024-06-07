package com.cash.atm.controller;


import com.cash.atm.dto.ATMDTO;
import com.cash.atm.services.ATMServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ATMController {
    private final ATMServices atmServices;

    //Instance to ATMService
    @Autowired
    public ATMController(ATMServices atmServices) {
        this.atmServices = atmServices;
    }

    //Enter to ATM
    @GetMapping("/")
    public String home(Model model) {
        //Call to DTO
        ATMDTO atmDTO = atmServices.getTotalEntity(1L);
        //Add attribute to view
        model.addAttribute("atmDTO", atmDTO);
        //Return view
        return "index";
    }

    @PostMapping("/withdraw")
    public String withdrawCash(@RequestParam double amount, Model model){
        String result = atmServices.withdrawCash(amount);
        String ultimateConsult = String.valueOf(amount);
        model.addAttribute("result", result);
        model.addAttribute("ultimateConsult", ultimateConsult);
        return "index";
    }


}
