package org.example.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserService implements UserServiceI {
    private Integer randomIncrement = 1;
    private Integer maxNumber = 1;

    public Integer getRandomIncrement() {
        return randomIncrement;
    }

    public Integer getMaxNumber() {
        return maxNumber;
    }


    public String reversedWord(String inputReversible) {
        return new StringBuilder(inputReversible).reverse().toString();
    }

    public boolean fibonacciOrNot(Integer inputNumber) {
        List<Integer> listFibonacci = new ArrayList<>();
        listFibonacci.add(0);
        listFibonacci.add(1);
        int i = 2;
        while (true) {
            listFibonacci.add(listFibonacci.get(i - 1) + listFibonacci.get(i - 2));
            if (Objects.equals(inputNumber, listFibonacci.get(i))) {
                return true;
            }
            if (inputNumber < listFibonacci.get(i)) {
                break;
            }
            i++;
        }
        return false;
    }

    @Override
    public void incrementRandom() {
        if (Math.random() >= 0.5) {
            randomIncrement++;
        } else {
            randomIncrement = 1;
        }
        if (randomIncrement > maxNumber) {
            maxNumber = randomIncrement;
        }
    }

    @Override
    public String arithmeticOperationWithComplexNumbers(Double realNumber1, Double imagineNumber1, Double realNumber2, Double imagineNumber2, String nameOfOperation) {
        StringBuilder res = new StringBuilder("");
        Double realNumberRes = 0.0;
        Double imagineNumberRes = 0.0;
        if (nameOfOperation.equals("addition")) {
            realNumberRes = realNumber1 + realNumber2;
            imagineNumberRes = imagineNumber1 + imagineNumber2;
        }
        if (nameOfOperation.equals("subtraction")) {
            realNumberRes = realNumber1 - realNumber2;
            imagineNumberRes = imagineNumber1 - imagineNumber2;
        }
        if (nameOfOperation.equals("multiplication")) {
            realNumberRes = realNumber1 * realNumber2 - imagineNumber1 * imagineNumber2;
            imagineNumberRes = realNumber1 * imagineNumber2 + imagineNumber1 * realNumber2;
        }
        if (nameOfOperation.equals("division")) {
            realNumberRes = (realNumber1 * realNumber2 + imagineNumber1 * imagineNumber2)/(realNumber2*realNumber2 + imagineNumber2* imagineNumber2);
            imagineNumberRes = (imagineNumber1 * realNumber2- realNumber1* imagineNumber2)/(realNumber2*realNumber2 + imagineNumber2* imagineNumber2);
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
