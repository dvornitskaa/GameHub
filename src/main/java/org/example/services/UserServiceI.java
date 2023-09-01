package org.example.services;

import org.example.enums.ComplexOperations;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserServiceI {
    String reversedWord(String inputReversible);

    boolean fibonacciOrNot(Integer inputNumber);

    void incrementRandom();

    Integer getRandomIncrement();

    Integer getMaxNumber();
    String arithmeticOperationWithComplexNumbers(Double realNumber1, Double imagineNumber1, Double realNumber2, Double imagineNumber2, ComplexOperations nameOfOperation);
}
