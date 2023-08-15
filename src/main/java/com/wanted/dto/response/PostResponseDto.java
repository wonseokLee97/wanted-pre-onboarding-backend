package com.wanted.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class PostResponseDto {
    private final Long id;
    private final String title;
    private final String content;
}
