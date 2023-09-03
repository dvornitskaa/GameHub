package org.example.services;

import org.example.dto.BlackjackDto;
import org.example.dto.CasinoDto;
import org.example.entities.BlackjackUser;
import org.example.entities.Casino;
import org.example.repositories.BlackjackRepository;
import org.example.services.interfaces.BlackjackServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BlackjackService implements BlackjackServiceI {
    @Value("${deposit}")
    private Integer deposit;
    @Autowired
    private BlackjackRepository blackjackRepository;
    @Override
    public BlackjackDto createBlackjack() {
        BlackjackUser blackjackUser = new BlackjackUser(deposit);
        Integer id = blackjackRepository.save(blackjackUser).getId();
        return new BlackjackDto(id, deposit);
    }
}
