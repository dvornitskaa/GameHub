package org.example.entities;

import jakarta.persistence.*;
import lombok.*;

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
}
