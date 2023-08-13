package com.wanted.dto.request;


import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class PostRequestDto {
    private final String title;
    private final String content;
}
