package com.wanted.dto.response;

import lombok.*;

@Data
@Builder
@RequiredArgsConstructor
public class UserSignInResponseDto {
    private final String token;
    private final String email;
}
