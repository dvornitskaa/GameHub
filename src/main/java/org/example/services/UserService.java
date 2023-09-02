package org.example.services;

import org.example.services.interfaces.UserServiceI;
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


}
