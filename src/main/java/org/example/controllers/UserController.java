package org.example.controllers;

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
    //    @GetMapping
//    public String greeting(){
//        return "index.html";
//    }
//    @GetMapping("exit")
//    public String parting(Model model, @RequestParam String userName){
//        if (userName.equals("Denis")){
//            userName = "teacher";
//        }
//        model.addAttribute("name", userName);
//        return "bye.html";
//    }
//    @GetMapping("hay")
//    public String howAreYou(){
//        return "hay.html";
//    }
    private String rememberedWord = "";
    private String reversedWord = "";
    private Integer numberFibonacci = 0;
    private String message = "";
    private boolean boolFibonacci = false;
    private Integer randomIncrement = 1;
    private Integer maxNumber = 1;


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("rememberedWord", rememberedWord);
        model.addAttribute("reversedWord", reversedWord);
        return "index";
    }

    @PostMapping("/reverse")
    public String reverse(String inputReversible, Model model) {
        reversedWord = new StringBuilder(inputReversible).reverse().toString();
        model.addAttribute("reversedWord", reversedWord);
        model.addAttribute("rememberedWord", rememberedWord);
        model.addAttribute("message", message);
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
        numberFibonacci = inputNumber;
        List<Integer> listFibonacci = new ArrayList<>();
        listFibonacci.add(0);
        listFibonacci.add(1);
        int i=2;
        while (true){
            listFibonacci.add(listFibonacci.get(i - 1) + listFibonacci.get(i - 2));
            if (Objects.equals(numberFibonacci, listFibonacci.get(i))) {
                message = "Да, это число Фибоначчи";
                boolFibonacci = true;
                return "redirect:/thirdPage";
                //break;
            }
            if (numberFibonacci < listFibonacci.get(i)) {
                message = "Нет, это не число Фибоначчи";
                break;
            }
            i++;
        }
        model.addAttribute("message", message);
        return "secondPage";
    }

    @GetMapping("/thirdPage")
    public String thirdPage(Model model) {
        model.addAttribute("randomIncrement", randomIncrement);
        model.addAttribute("maxNumber", maxNumber);
        return "thirdPage";
    }


    @PostMapping("/increase")
    public String increase(Model model) {
        if (Math.random() >= 0.5) {
            randomIncrement++;
        } else {
            randomIncrement = 1;
        }
        if (randomIncrement > maxNumber) {
            maxNumber = randomIncrement;
        }
        model.addAttribute("randomIncrement", randomIncrement);
        model.addAttribute("maxNumber", maxNumber);
        return "thirdPage";
    }


}