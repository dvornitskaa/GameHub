package org.example.services;

import org.example.enums.ComplexOperations;

public interface ComplexServiceI {
    String arithmeticOperationWithComplexNumbers(Double realNumber1, Double imagineNumber1, Double realNumber2, Double imagineNumber2, ComplexOperations nameOfOperation);

}
