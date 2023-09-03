package org.example.services.interfaces;

import org.example.dto.CasinoDto;
import org.example.enums.TypesOfBets;

public interface CasinoServiceI {

    CasinoDto makeBet(Integer betSize, TypesOfBets typesOfBets, Integer cellNumber, Integer id);
    CasinoDto createCasino();

}
