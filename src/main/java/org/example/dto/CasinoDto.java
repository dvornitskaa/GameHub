package org.example.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data //getter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CasinoDto {
    @NonNull
    Integer id;
    @NonNull
    Integer deposit;
    String betResult;
}
