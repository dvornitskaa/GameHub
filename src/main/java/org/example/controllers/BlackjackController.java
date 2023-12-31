package org.example.controllers;

import org.example.dto.BlackjackDto;
import org.example.dto.TurnDto;
import org.example.enums.TypesOfBets;
import org.example.services.interfaces.BlackjackServiceI;
import org.example.services.interfaces.CasinoServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BlackjackController {
    @Autowired
    private BlackjackServiceI blackjackService;

    @GetMapping("/blackjack")
    public String blackjack(Model model) {
        model.addAttribute("blackjackInfo", blackjackService.createBlackjack());
        model.addAttribute("deposits", blackjackService.getDeposits());
        model.addAttribute("maxDeposit", blackjackService.getMaxDeposit());

        return "blackjack";
    }


    @PostMapping("/round/{id}")
    public String round(@PathVariable Integer id, Integer betSize, Model model) {
        BlackjackDto blackjackDto = blackjackService.playRound(betSize, id);
        model.addAttribute("blackjackInfo", blackjackDto);
        model.addAttribute("deposits", blackjackService.getDeposits());
        model.addAttribute("maxDeposit", blackjackService.getMaxDeposit());
        return "blackjack";
    }

    @PostMapping("/hitMove/{id}")
    public String hitMove(@PathVariable Integer id, Model model) {
        BlackjackDto blackjackDto = blackjackService.hit(id);
        model.addAttribute("blackjackInfo", blackjackDto);
        model.addAttribute("deposits", blackjackService.getDeposits());
        model.addAttribute("maxDeposit", blackjackService.getMaxDeposit());
        return "blackjack";
    }
    @PostMapping("/standMove/{id}")
    public String standMove(@PathVariable Integer id, Model model) {
        BlackjackDto blackjackDto = blackjackService.stand(id);
        model.addAttribute("blackjackInfo", blackjackDto);
        model.addAttribute("deposits", blackjackService.getDeposits());
        model.addAttribute("maxDeposit", blackjackService.getMaxDeposit());
        return "blackjack";
    }
    @PostMapping("/evenOdd/{id}")
    public String evenOdd(@PathVariable Integer id, String submitButton,Integer betSize, Model model) {
        BlackjackDto blackjackDto = blackjackService.evenOddCheck(id, submitButton, betSize);
        model.addAttribute("blackjackInfo", blackjackDto);
        model.addAttribute("deposits", blackjackService.getDeposits());
        model.addAttribute("maxDeposit", blackjackService.getMaxDeposit());
        model.addAttribute("turnInfo", blackjackService.getAllBetsAndResults(id));
        model.addAttribute("winCoefficient", blackjackService.getWinCoefficient());
        model.addAttribute("comboCoefficient", blackjackService.getMaxCoefficient());
        model.addAttribute("maxWin", blackjackService.getMaxWinSum());

        return "blackjack";
    }

}
