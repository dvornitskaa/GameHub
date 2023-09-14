package org.example.dto;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.entities.BlackjackUser;
import org.example.entities.Turn;

import java.util.List;

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
    Integer betSize;
    String result;
    public TurnDto(int betSize, String result) {
        this.betSize = betSize;
        this.result = result;
    }

    @Override
    public String toString() {
        return "betSize=" + betSize +
                ", result='" + result ;
    }
}
