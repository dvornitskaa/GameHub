package org.example.services;

import org.example.enums.ComplexOperations;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserServiceI {
    String reversedWord(String inputReversible);

    boolean fibonacciOrNot(Integer inputNumber);

    void incrementRandom();

    Integer getRandomIncrement();

    Integer getMaxNumber();

}
