package org.example.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.enums.Deck;

import java.util.ArrayList;
import java.util.List;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@Table(name = "blackjack_users")
public class BlackjackUser {
    @Id
    @Column(unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NonNull
    private Integer deposit;
    @NonNull
    private List<Deck> usersCards = new ArrayList<>();
    @NonNull
    private List<Deck> dealersCards = new ArrayList<>();
//    public BlackjackUser(Integer deposit) {
//        this.deposit = deposit;
////        this.usersCards = new ArrayList<>();
////        this.dealersCards = new ArrayList<>();
//    }



}
