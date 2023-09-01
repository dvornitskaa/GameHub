package org.example.services;

import org.example.enums.ComplexOperations;
import org.springframework.stereotype.Service;

@Service
public class ComplexService implements ComplexServiceI{
    public String arithmeticOperationWithComplexNumbers(Double realNumber1, Double imagineNumber1, Double realNumber2, Double imagineNumber2, ComplexOperations nameOfOperation) {
        StringBuilder res = new StringBuilder("");
        Double realNumberRes = 0.0;
        Double imagineNumberRes = 0.0;

        switch (nameOfOperation) {
            case ADDITION -> {
                realNumberRes = realNumber1 + realNumber2;
                imagineNumberRes = imagineNumber1 + imagineNumber2;
            }
            case DIVISION -> {
                realNumberRes = (realNumber1 * realNumber2 + imagineNumber1 * imagineNumber2) / (realNumber2 * realNumber2 + imagineNumber2 * imagineNumber2);
                imagineNumberRes = (imagineNumber1 * realNumber2 - realNumber1 * imagineNumber2) / (realNumber2 * realNumber2 + imagineNumber2 * imagineNumber2);
            }
            case SUBTRACTION -> {
                realNumberRes = realNumber1 - realNumber2;
                imagineNumberRes = imagineNumber1 - imagineNumber2;
            }
            case MULTIPLICATION -> {
                realNumberRes = realNumber1 * realNumber2 - imagineNumber1 * imagineNumber2;
                imagineNumberRes = realNumber1 * imagineNumber2 + imagineNumber1 * realNumber2;
            }
            default -> {
                realNumberRes = 0.0;
                imagineNumberRes = 0.0;
            }
        }

        if (imagineNumberRes > 0){
            res.append(realNumberRes);
            res.append("+");
            res.append(imagineNumberRes);
            res.append("i");
        }
        if (imagineNumberRes < 0){
            res.append(realNumberRes);
            res.append(imagineNumberRes);
            res.append("i");
        }
        if (imagineNumberRes == 0){
            res.append(realNumberRes);
        }
        return res.toString();
    }
}
