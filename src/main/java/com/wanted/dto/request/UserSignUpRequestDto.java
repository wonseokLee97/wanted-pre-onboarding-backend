package com.wanted.dto.request;

import lombok.*;

@Data
@Builder
@RequiredArgsConstructor
public class UserSignupRequestDto {
    private final String email;
    private final String password;
}
