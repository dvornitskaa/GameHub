package org.example.controllers;

import org.example.enums.TypesOfBets;
import org.example.services.interfaces.CasinoServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CasinoController {
    @Autowired
    private CasinoServiceI casinoService;
    @GetMapping("/casino")
    public String casino(Model model){
        model.addAttribute("deposit", casinoService.getDeposit());
        return "casino";
    }
    @PostMapping("/bet")
    public String bet(Integer betSize, TypesOfBets submitButton, Integer cellNumber, Model model) {
        String resultOfBet = casinoService.makeBet(betSize,  submitButton,  cellNumber);
        model.addAttribute("resultOfBet", resultOfBet);
        model.addAttribute("deposit", casinoService.getDeposit());
        return "casino";
    }

}
