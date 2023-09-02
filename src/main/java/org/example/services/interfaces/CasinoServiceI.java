package org.example.services.interfaces;

import org.example.enums.TypesOfBets;

public interface CasinoServiceI {
    Integer getDeposit();

    String makeBet(Integer betSize, TypesOfBets typesOfBets, Integer cellNumber);
}
