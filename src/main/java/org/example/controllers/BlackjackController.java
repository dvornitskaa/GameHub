package org.example.controllers;

import org.example.enums.TypesOfBets;
import org.example.services.interfaces.BlackjackServiceI;
import org.example.services.interfaces.CasinoServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BlackjackController {
    @Autowired
    private BlackjackServiceI blackjackService;
    @GetMapping("/blackjack")
    public String blackjack(Model model){
        model.addAttribute("blackjackInfo", blackjackService.createBlackjack());
        return "blackjack";
    }
    @PostMapping("/round/{id}")
    public String round(@PathVariable Integer id, Integer betSize, Model model){

        return "blackjack";
    }
}
