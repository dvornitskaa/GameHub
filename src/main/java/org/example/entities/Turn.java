package org.example.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@RequiredArgsConstructor

@Data
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "turns")
public class Turn {
    @Id
    @Column(unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    BlackjackUser blackjackUser;
    Integer betSize;
    String result;
    Integer comboCoefficient;
    Integer winSum;
    public Turn(BlackjackUser blackjackUser, Integer betSize) {
        this.blackjackUser = blackjackUser;
        this.betSize = betSize;
    }

}
