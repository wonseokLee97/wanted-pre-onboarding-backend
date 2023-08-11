package com.wanted.dto.request;

import lombok.*;

@Data
@Builder
@RequiredArgsConstructor
public class UserLoginRequestDto {
    private final String email; //아이디
    private final String password; //비밀번호
}
