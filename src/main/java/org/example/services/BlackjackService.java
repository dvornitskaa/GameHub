package org.example.services;

import org.example.dto.BlackjackDto;
import org.example.dto.CasinoDto;
import org.example.dto.TurnDto;
import org.example.entities.BlackjackUser;
import org.example.entities.Casino;
import org.example.entities.Turn;
import org.example.enums.Deck;
import org.example.repositories.BlackjackRepository;
import org.example.repositories.TurnRepository;
import org.example.services.interfaces.BlackjackServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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
    @Autowired
    private TurnRepository turnRepository;
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
        usersCards.clear();
        List<Deck> dealersCards = blackjackUser.getDealersCards();
        dealersCards.clear();

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
        blackjackUser.setBetSize(betSize);
//        blackjackUser.setUsersCards(usersCards);
//        blackjackUser.setDealersCards(dealersCards);
        blackjackRepository.save(blackjackUser);
        return new BlackjackDto(id, deposit, betSize, usersCards, dealersCards, "");

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
        return new BlackjackDto(id, blackjackUser.getDeposit(), blackjackUser.getBetSize(), blackjackUser.getUsersCards(), blackjackUser.getDealersCards(), "");
    }

    @Override
    public BlackjackDto stand(Integer id) {
        BlackjackUser blackjackUser = blackjackRepository.findById(id).get();
        int usersSum = 0;
        int betSize = blackjackUser.getBetSize();
        //assert blackjackUser != null;
        int deposit = blackjackUser.getDeposit();
        StringBuilder message = new StringBuilder();
        boolean ace = false;
        boolean aceInDealerHand = false;
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
            return new BlackjackDto(id, deposit, betSize, blackjackUser.getUsersCards(), blackjackUser.getDealersCards(), blackjackUser.getMessage());
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
        if (usersSum < dealerSum && dealerSum <= twentyOne) {
            deposit -= betSize;
            message.append("you lose ");
        }

        blackjackUser.setMessage(message.toString());
        blackjackUser.setDeposit(deposit);
        blackjackRepository.save(blackjackUser);
        return new BlackjackDto(id, deposit, betSize, blackjackUser.getUsersCards(), blackjackUser.getDealersCards(), blackjackUser.getMessage());
    }

    @Override
    public List<Integer> getDeposits() {
        //  return blackjackRepository.findAll().stream().map(BlackjackUser::getDeposit).collect(Collectors.toList());
        return blackjackRepository.getAllDeposits();
    }

    @Override
    public Integer getMaxDeposit() {
        return blackjackRepository.getMaxDeposit();
    }

    @Override
    public BlackjackDto evenOddCheck(Integer id, String submitButton, Integer betSize) {
        BlackjackUser blackjackUser = blackjackRepository.findById(id).get();

        Turn turn = new Turn(blackjackUser, betSize);
        Integer deposit = blackjackUser.getDeposit();
        String betRes = "";
        Random random = new Random();
        int coefficient = 0;
        int winSum = 0;
        turnRepository.save(turn);
        if (turn.getId() != 1) {
            coefficient = turnRepository.findById(turn.getId() - 1).get().getComboCoefficient();
        }

        // Генерируем первое случайное число от 1 до 6
        int firstDiceNumber = random.nextInt(6) + 1;

        // Генерируем второе случайное число от 1 до 6
        int secondDiceNumber = random.nextInt(6) + 1;
        int sum = firstDiceNumber + secondDiceNumber;
        if (betSize > 0 && betSize <= deposit) {
            switch (submitButton) {
                case "EVEN" -> {
                    if (sum % 2 == 0) {
                        deposit += (betSize + betSize * coefficient);
                        winSum = (betSize + betSize * coefficient);
                        betRes = "win";
                        coefficient += 1;
                    } else {
                        deposit -= betSize;
                        betRes = "loss";
                        coefficient = 0;
                    }
                }
                case "ODD" -> {
                    if (sum % 2 == 1) {
                        deposit += (betSize + betSize * coefficient);
                        winSum = (betSize + betSize * coefficient);
                        betRes = "win";
                        coefficient += 1;
                    } else {
                        deposit -= betSize;
                        betRes = "loss";
                        coefficient = 0;
                    }
                }
            }
        }
        turn.setResult(betRes);
        turn.setWinSum(winSum);
        turn.setComboCoefficient(coefficient);
        blackjackUser.getTurns().add(turn);
        blackjackUser.setDeposit(deposit);
        blackjackUser.setBetSize(betSize);
        System.out.println(blackjackUser.getTurns());
        blackjackRepository.save(blackjackUser);
        //turnRepository.save(turn);
        return new BlackjackDto(id, deposit, betSize, betRes, firstDiceNumber, secondDiceNumber);
    }

    @Override
    public List<TurnDto> getAllBetsAndResults(Integer id) {
        BlackjackUser blackjackUser = blackjackRepository.findById(id).get();
        List <TurnDto> turnDtoList= new ArrayList<>();

        for (Turn turn: blackjackUser.getTurns()) {
            TurnDto turnDto = new TurnDto();
            turnDto.setResult(turn.getResult());
            turnDto.setBetSize(turn.getBetSize());
            turnDtoList.add(turnDto);

        }
        return turnDtoList;
    }

    @Override
    public double getWinCoefficient() {
        return turnRepository.getWinCoefficient();
    }

    @Override
    public int getMaxCoefficient() {
        return turnRepository.getMaxCoefficient();
    }

    @Override
    public int getMaxWinSum() {
        return turnRepository.getMaxWinSum();
    }


}
