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
    @Value("${twentyOne}")
    private Integer twentyOne;
    @Value("${aceValue}")
    private Integer aceValue;
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
        BlackjackUser blackjackUser = blackjackRepository.findById(id).get();
        List<Deck> usersCards = blackjackUser.getUsersCards();
        List<Deck> dealersCards = blackjackUser.getDealersCards();

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

            // Добавляем 1 случайные карты в dealersCards и удаляем их из deckList
            for (int i = 0; i < 1; i++) {
                int randomIndex = random.nextInt(deckList.size());
                Deck dealerCard = deckList.get(randomIndex);
                dealersCards.add(dealerCard);
                deckList.remove(randomIndex);
            }
        }
        blackjackUser.setDeposit(deposit);
//        blackjackUser.setUsersCards(usersCards);
//        blackjackUser.setDealersCards(dealersCards);
        blackjackRepository.save(blackjackUser);
        return new BlackjackDto(id, deposit, usersCards, dealersCards, "");

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
        return new BlackjackDto(id, blackjackUser.getDeposit(), blackjackUser.getUsersCards(), blackjackUser.getDealersCards(), "");
    }

    @Override
    public BlackjackDto stand(Integer id, Integer betSize) {
        BlackjackUser blackjackUser = blackjackRepository.findById(id).orElse(null);
        int usersSum = 0;
        //assert blackjackUser != null;
        int deposit = blackjackUser.getDeposit();
        StringBuilder message = new StringBuilder();
        boolean ace = false;
        boolean aceInDealerHand = false;
        boolean failureBeforeDealersTurn = false;
        for (Deck card : blackjackUser.getUsersCards()) {
            usersSum += card.getNumericValue();
            if (card.getNumericValue() == aceValue) {
                ace = true;
            }
            if (ace && usersSum > twentyOne) {
                usersSum -= (aceValue - 1);
            }
        }

        //blackjackRepository.save(blackjackUser);
        int dealerSum = blackjackUser.getDealersCards().get(0).getNumericValue();
        if (usersSum <= twentyOne) {
            //дальше играем за диллерв
            while (dealerSum < 17) {
                Random random = new Random();
                int randomIndex = random.nextInt(deckList.size());
                Deck dealerCard = deckList.get(randomIndex);
                blackjackUser.getDealersCards().add(dealerCard);
                deckList.remove(randomIndex);
                dealerSum += dealerCard.getNumericValue();
                if (dealerCard.getNumericValue() == aceValue) {
                    aceInDealerHand = true;
                }
                if (aceInDealerHand && dealerSum > twentyOne) {
                    usersSum -= (aceValue - 1);
                }
            }
            //blackjackRepository.save(blackjackUser);

        } else {
            deposit -= betSize;
            message.append("bust, you lose");
            blackjackUser.setMessage(message.toString());
            blackjackRepository.save(blackjackUser);
            return new BlackjackDto(id, deposit, blackjackUser.getUsersCards(), blackjackUser.getDealersCards(), blackjackUser.getMessage());
        }
        // проверка кто победил
        if (dealerSum > twentyOne) {
            deposit += betSize * 3 / 2;
            message.append("you won ");
        }

        if (usersSum == twentyOne && dealerSum == twentyOne) {
            if (blackjackUser.getDealersCards().size() == 2 && blackjackUser.getUsersCards().size() != 2) {
                deposit -= betSize;
                message.append("you lose ");
            } else if (blackjackUser.getDealersCards().size() != 2 && blackjackUser.getUsersCards().size() == 2) {
                deposit += betSize * 3 / 2;
                message.append("you won ");
            } else {
                message.append("draw ");
            }
            if (blackjackUser.getUsersCards().size() == 2) {
                message.append("you got Blackjack");
            }
        }
        if (usersSum > dealerSum) {
            deposit += betSize * 3 / 2;
            message.append("you won ");
        }
        if (usersSum == dealerSum) {
            message.append("draw ");
        }

        blackjackUser.setMessage(message.toString());
        blackjackRepository.save(blackjackUser);
        return new BlackjackDto(id, deposit, blackjackUser.getUsersCards(), blackjackUser.getDealersCards(), blackjackUser.getMessage());
    }

}
