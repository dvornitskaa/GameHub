package org.example.services.interfaces;

import org.example.dto.BlackjackDto;
import org.example.dto.CasinoDto;
import org.example.dto.TurnDto;
import org.example.entities.Turn;
import org.example.enums.TypesOfBets;

import java.util.List;

public interface BlackjackServiceI {
    BlackjackDto createBlackjack();
    BlackjackDto playRound(Integer betSize, Integer id);
    BlackjackDto hit(Integer id);
    BlackjackDto stand(Integer id);
    List<Integer> getDeposits ();
    Integer getMaxDeposit ();
    BlackjackDto evenOddCheck(Integer betSize, String submitButton,Integer id);
    List<TurnDto> getAllBetsAndResults();
    double getWinCoefficient();
}
