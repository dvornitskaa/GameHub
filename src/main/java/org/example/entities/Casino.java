package org.example.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@Table(name = "casino_users")
public class Casino {
    @Id
    @Column(unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NonNull
    private Integer deposit;

}
