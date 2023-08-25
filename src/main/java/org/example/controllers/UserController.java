package org.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping
    public String greeting(){
        return "index.html";
    }
    @GetMapping("exit")
    public String parting(){
        return "bye.html";
    }
    @GetMapping("hay")
    public String howAreYou(){
        return "hay.html";
    }
}
