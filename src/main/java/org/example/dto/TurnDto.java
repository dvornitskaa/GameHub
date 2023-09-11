package org.example.dto;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.entities.BlackjackUser;

@Data //getter
//@AllArgsConstructor
@NoArgsConstructor

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TurnDto {
//    Integer id;
//    Integer userId;
//    @ManyToOne
//    @JoinColumn(name="user_id", nullable=false)
//    BlackjackUser blackjackUser;
    Integer bet_Size;
    String result;
    public TurnDto(int bet_Size, String result) {
        this.bet_Size = bet_Size;
        this.result = result;
    }
}
