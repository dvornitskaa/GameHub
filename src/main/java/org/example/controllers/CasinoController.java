package org.example.controllers;

import org.example.dto.CasinoDto;
import org.example.enums.TypesOfBets;
import org.example.services.interfaces.CasinoServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CasinoController {
    @Autowired
    private CasinoServiceI casinoService;
    @GetMapping("/casino")
    public String casino(Model model){
        model.addAttribute("casinoInfo", casinoService.createCasino());
        return "casino";
    }
    @PostMapping("/bet/{id}")
    public String bet(@PathVariable Integer id, Integer betSize, TypesOfBets submitButton, Integer cellNumber, Model model) {
        CasinoDto casinoDto = casinoService.makeBet(betSize,  submitButton,  cellNumber, id);
        model.addAttribute("casinoInfo", casinoDto);
        return "casino";
    }

}
