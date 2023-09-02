package org.example.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class CasinoDto {
    @NonNull
    Integer deposit;
    String betResult;
}
