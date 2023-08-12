package com.wanted.dto.request;

import lombok.*;

@Data
@Builder
@RequiredArgsConstructor
public class UserSignUpRequestDto {
    private final String email;
    private final String password;
}
