package org.example.controllers;

import org.example.enums.ComplexOperations;
import org.example.services.interfaces.ComplexServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ComplexController {
    @Autowired
    private ComplexServiceI complexService;
    @PostMapping("/complex")
    public String complex(Double realNumber1, Double imagineNumber1, Double realNumber2, Double imagineNumber2, ComplexOperations submitButton, Model model){

        String resultOfOperation = complexService.arithmeticOperationWithComplexNumbers(realNumber1, imagineNumber1, realNumber2, imagineNumber2, submitButton);
        model.addAttribute("resultOfOperation", resultOfOperation);
        return "index";
    }
}
