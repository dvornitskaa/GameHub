package org.example.controllers;

import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
//@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserController {
    @Autowired
    private UserService userService;
    private String rememberedWord = "";
    private String reversedWord = "";


    @GetMapping("exit")
    public String parting(Model model, @RequestParam String userName) {
        if (userName.equals("Denis")) {
            userName = "teacher";
        }
        model.addAttribute("name", userName);
        return "bye.html";
    }

    @GetMapping("hay")
    public String howAreYou() {
        return "hay.html";
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("rememberedWord", rememberedWord);
        model.addAttribute("reversedWord", reversedWord);
        return "index";
    }

    @PostMapping("/reverse")
    public String reverse(String inputReversible, Model model) {
        reversedWord = userService.reversedWord(inputReversible);
        model.addAttribute("reversedWord", reversedWord);
        model.addAttribute("rememberedWord", rememberedWord);
        if (inputReversible.equals(rememberedWord)) {
            return "secondPage";
        }
        return "index";
    }

    @PostMapping("/remember")
    public String remember(String inputRememberable) {
        rememberedWord = inputRememberable;
        return "redirect:/";
    }

    @PostMapping("/fibonacci")
    public String fibonacci(Integer inputNumber, Model model) {
        boolean isFibonacci = userService.fibonacciOrNot(inputNumber);
        if (isFibonacci) {
            return "redirect:/thirdPage";
        }
        model.addAttribute("message", "Нет, это не число Фибоначчи");
        return "secondPage";
    }

    @GetMapping("/thirdPage")
    public String thirdPage(Model model) {
        model.addAttribute("randomIncrement", userService.getRandomIncrement());
        model.addAttribute("maxNumber", userService.getMaxNumber());
        return "thirdPage";
    }


    @PostMapping("/increase")
    public String increase(Model model) {
        userService.incrementRandom();
        model.addAttribute("randomIncrement", userService.getRandomIncrement());
        model.addAttribute("maxNumber", userService.getMaxNumber());
        return "thirdPage";
    }


}