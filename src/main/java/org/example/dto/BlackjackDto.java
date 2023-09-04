package org.example.dto;

import io.micrometer.common.lang.Nullable;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.enums.Deck;

import java.util.List;

@Data //getter
@AllArgsConstructor
@NoArgsConstructor
//@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlackjackDto {
    @NonNull
    Integer id;
    @NonNull
    Integer deposit;
    @NonNull
    List<Deck> usersCards;
    @NonNull
    List<Deck> dealersCards;
    public BlackjackDto(Integer id, Integer deposit) {
        this.id = id;
        this.deposit = deposit;
    }
}
