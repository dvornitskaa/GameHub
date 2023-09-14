package org.example.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.enums.Deck;

import java.util.ArrayList;
import java.util.List;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "blackjack_users")
public class BlackjackUser {
    @Id
    @Column(unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    @NonNull
    Integer deposit;
    Integer betSize;
    List<Deck> usersCards = new ArrayList<>();
    List<Deck> dealersCards = new ArrayList<>();
    String message;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "blackjackUser")
    List<Turn> turns = new ArrayList<>();
//    public BlackjackUser(Integer deposit) {
//        this.deposit = deposit;
////        this.usersCards = new ArrayList<>();
////        this.dealersCards = new ArrayList<>();
//    }


}
