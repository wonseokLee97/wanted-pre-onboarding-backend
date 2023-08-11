package com.wanted.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@Builder
@RequiredArgsConstructor
public class ApiResponseDto {
    private final boolean success;
    private final String message;
    private final Object responseData;
}
