package org.example.services;

import org.example.dto.BlackjackDto;
import org.example.dto.CasinoDto;
import org.example.entities.BlackjackUser;
import org.example.entities.Casino;
import org.example.enums.Deck;
import org.example.repositories.BlackjackRepository;
import org.example.services.interfaces.BlackjackServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class BlackjackService implements BlackjackServiceI {
    @Value("${deposit}")
    private Integer deposit;
    @Autowired
    private BlackjackRepository blackjackRepository;
    List<Deck> deckList = new ArrayList<>();

    @Override
    public BlackjackDto createBlackjack() {
        BlackjackUser blackjackUser = new BlackjackUser(deposit);
        Integer id = blackjackRepository.save(blackjackUser).getId();
        return new BlackjackDto(id, deposit);
    }

    @Override
    public BlackjackDto playRound(Integer betSize, Integer id) {
        List<Deck> usersCards = new ArrayList<>();
        List<Deck> dealersCards = new ArrayList<>();

        BlackjackUser blackjackUser = blackjackRepository.findById(id).get();
        int deposit = blackjackUser.getDeposit();
        // Добавляем все элементы перечисления Deck в список
        for (Deck card : Deck.values()) {
            deckList.add(card);
        }
        // Создаем объект Random для генерации случайных чисел
        Random random = new Random();
        if (deposit >= betSize && betSize > 0) {
            // Добавляем по две случайные карты в usersCards и удаляем их из deckList
            for (int i = 0; i < 2; i++) {
                int randomIndex = random.nextInt(deckList.size());
                Deck userCard = deckList.get(randomIndex);
                usersCards.add(userCard);
                deckList.remove(randomIndex);
            }

            // Добавляем по две случайные карты в dealersCards и удаляем их из deckList
            for (int i = 0; i < 2; i++) {
                int randomIndex = random.nextInt(deckList.size());
                Deck dealerCard = deckList.get(randomIndex);
                dealersCards.add(dealerCard);
                deckList.remove(randomIndex);
            }
        }
        blackjackUser.setDeposit(deposit);
        blackjackUser.setUsersCards(usersCards);
        blackjackUser.setDealersCards(dealersCards);
        blackjackRepository.save(blackjackUser);
        return new BlackjackDto(id, deposit, usersCards, dealersCards);

    }

    @Override
    public BlackjackDto hit(Integer id) {

        Random random = new Random();

        // Получаем объект blackjackUser по id
        BlackjackUser blackjackUser = blackjackRepository.findById(id).orElse(null);

        if (blackjackUser != null) {
            // Добавляем по одной случайной карте в usersCards и удаляем ее из deckList
            int randomIndex = random.nextInt(deckList.size());
            Deck userCard = deckList.get(randomIndex);
            blackjackUser.getUsersCards().add(userCard);
            deckList.remove(randomIndex);

            // Сохраняем обновленный объект blackjackUser в репозитории
            blackjackRepository.save(blackjackUser);
        }

        // Здесь можно вернуть какой-то результат, например, обновленный BlackjackDto
        return new BlackjackDto(id, blackjackUser.getDeposit(), blackjackUser.getUsersCards(), blackjackUser.getDealersCards());
    }

}
