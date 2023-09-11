package org.example.dto;

import io.micrometer.common.lang.Nullable;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.enums.Deck;

import java.util.List;

@Data //getter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlackjackDto {
    @NonNull
    Integer id;
    @NonNull
    Integer deposit;
    Integer betSize;
    List<Deck> usersCards;
    List<Deck> dealersCards;
    String message;
    Integer firstDiceNumber;
    Integer secondDiceNumber;

    public BlackjackDto(@NonNull Integer id, @NonNull Integer deposit, Integer betSize, String message, Integer firstDiceNumber, Integer secondDiceNumber) {
        this.id = id;
        this.deposit = deposit;
        this.betSize = betSize;
        this.message = message;
        this.firstDiceNumber = firstDiceNumber;
        this.secondDiceNumber = secondDiceNumber;
    }

    public BlackjackDto(@NonNull Integer id, @NonNull Integer deposit, Integer betSize, List<Deck> usersCards, List<Deck> dealersCards, String message) {
        this.id = id;
        this.deposit = deposit;
        this.betSize = betSize;
        this.usersCards = usersCards;
        this.dealersCards = dealersCards;
        this.message = message;
    }
}
