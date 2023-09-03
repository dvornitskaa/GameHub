package org.example.services;

import org.example.enums.TypesOfBets;
import org.example.services.interfaces.CasinoServiceI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CasinoService implements CasinoServiceI {
    @Value("${deposit}")
    private Integer deposit;
    @Value("${cellsAmount}")
    private Integer cellsAmount;

    public Integer getDeposit() {
        return deposit;
    }

    @Override
    public String makeBet(Integer betSize, TypesOfBets typesOfBets, Integer cellNumber) {
        StringBuilder res = new StringBuilder("");
        Integer randomNumber = (int) (Math.random() * cellsAmount);
        int win = 0;
        if (deposit >= betSize) {
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
        }else {
            res.append("на вашем балансе недостаточно средств");
        }
        if (deposit == 0) {
            res.append(", НУ ТЫ ЛОХ");
        }
        return res.toString();

    }

}
