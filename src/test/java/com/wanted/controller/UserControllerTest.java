package com.wanted.controller;

import com.wanted.dto.request.UserSignupRequestDto;
import com.wanted.dto.response.ApiResponseDto;
import com.wanted.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Mock
    private UserService userService; // UserService의 Mock 객체

    @InjectMocks
    private UserController userController; // UserController의 Mock 객체 주입 대상

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this); // Mock 객체 초기화
    }

    @Test
    void signUpSuccess() {
        // given : 회원가입을 진행
        UserSignupRequestDto dto = UserSignupRequestDto.builder()
                .email("test@test.com")
                .password("test1234")
                .build();

        // when
        when(userService.signUp(dto.getEmail(), dto.getPassword()))
                .thenReturn(
                        ApiResponseDto.builder()
                                .success(false)
                                .message("User singUp fail")
                                .responseData(null)
                                .build()
                );

        ResponseEntity<ApiResponseDto> response = userController.signUp(dto);


        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().isSuccess()).isTrue();
        assertThat(response.getBody().getMessage()).isEqualTo("회원가입 성공");
    }

    @Test
    void signUpFail() {
//        // given : 회원가입을 진행
//        UserSignupRequestDto dto = UserSignupRequestDto.builder()
//                .email("test@test.com")
//                .password("test1234")
//                .build();
//
//        // when
//        when(userService.signUp(dto.getEmail(), dto.getPassword()))
//                .thenReturn(
//                        ApiResponseDto.builder()
//                                .success()
//                                .message()
//                                .responseData()
//                                .build()
//                );
//
//        // then


    }

    @Test
    void signIn() {
        // given : 뭔가가 주어졌는데

        // when : 이걸 실행했을 때

        // then : 이런 결과가 나와야 해
    }
}