package org.example.controllers;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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






}