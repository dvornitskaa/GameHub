package org.example.services;

import org.example.dto.CasinoDto;
import org.example.entities.Casino;
import org.example.enums.TypesOfBets;
import org.example.repositories.CasinoRepository;
import org.example.services.interfaces.CasinoServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CasinoService implements CasinoServiceI {
    @Value("${deposit}")
    private Integer deposit;
    @Value("${cellsAmount}")
    private Integer cellsAmount;
    @Autowired
    private CasinoRepository casinoRepository;


    @Override
    public CasinoDto makeBet(Integer betSize, TypesOfBets typesOfBets, Integer cellNumber, Integer id) {
        StringBuilder res = new StringBuilder("");
        Integer randomNumber = (int) (Math.random() * cellsAmount);
        Casino casino = casinoRepository.findById(id).get();
        int win = 0;
        int deposit = casino.getDeposit();
        if (deposit >= betSize && betSize > 0) {
            switch (typesOfBets) {
                case ZERO -> {
                    if (randomNumber == 0) {
                        win = betSize * (cellsAmount - 2);
                        deposit += win;
                        res.isEmpty();
                        res.append("вы выиграли ");
                        res.append(win);
                    } else {
                        res.append("вы проиграли");
                        deposit -= betSize;
                    }
                }
                case NUMBER -> {
                    if (randomNumber.equals(cellNumber)) {
                        win = betSize * (cellsAmount - 2);
                        deposit += win;
                        res.isEmpty();
                        res.append("вы выиграли ");
                        res.append(win);
                    } else {
                        res.append("вы проиграли");
                        deposit -= betSize;
                    }
                }
                case RED, EVEN -> {
                    if (randomNumber % 2 == 0 && randomNumber != 0) {
                        win = betSize;
                        deposit += win;
                        res.isEmpty();
                        res.append("вы выиграли ");
                        res.append(win);
                    } else {
                        res.append("вы проиграли");
                        deposit -= betSize;
                    }
                }
                case BLACK, ODD -> {
                    if (randomNumber % 2 == 1) {
                        win = betSize;
                        deposit += win;
                        res.isEmpty();
                        res.append("вы выиграли ");
                        res.append(win);
                    } else {
                        res.append("вы проиграли");
                        deposit -= betSize;
                    }
                }
            }
        } else {
            res.append("на вашем балансе недостаточно средств");
        }
        if (deposit == 0) {
            res.append(", НУ ТЫ ЛОХ");
        }
        casino.setDeposit(deposit);
        casinoRepository.save(casino);
        return new CasinoDto(id, deposit, res.toString());

    }

    @Override
    public CasinoDto createCasino() {
        Casino casino = new Casino(deposit);
        Integer id = casinoRepository.save(casino).getId();
        return new CasinoDto(id, deposit);
    }

}
