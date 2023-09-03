package org.example.dto;

import io.micrometer.common.lang.Nullable;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.enums.Deck;

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
    @NonNull
    Deck firstCard;
    @NonNull
    Deck secondCard;
    @NonNull
    Deck firstDealersCard;
    @NonNull
    Deck secondDealersCard;
    @Nullable
    Deck thirdCard;
    @Nullable
    Deck thirdDealersCard;
    public BlackjackDto(Integer id, Integer deposit) {
        this.id = id;
        this.deposit = deposit;
    }
}
