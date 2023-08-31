package org.example.services;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    public String reversedWord(String inputReversible){
        return new StringBuilder(inputReversible).reverse().toString();
    }
}
