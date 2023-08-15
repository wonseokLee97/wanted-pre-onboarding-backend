package com.wanted.controller;

import com.wanted.dto.request.UserSignInRequestDto;
import com.wanted.dto.request.UserSignUpRequestDto;
import com.wanted.dto.response.ApiResponseDto;
import com.wanted.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void signUpSuccess() {
        // given
        UserSignUpRequestDto signUpTestDto = UserSignUpRequestDto.builder()
                .email("test@test.com")
                .password("test12345")
                .build();

        ApiResponseDto successResponse = new ApiResponseDto(true, "User singUp success", null);
        when(userService.signUp(anyString(), anyString())).thenReturn(successResponse);

        // when
        ResponseEntity<?> response = userController.signUp(signUpTestDto);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(((ApiResponseDto) response.getBody()).isSuccess()).isTrue();
        assertThat(((ApiResponseDto) response.getBody()).getMessage()).isEqualTo("User singUp success");
    }


    @Test
    void signUpFail() {
        // given
        UserSignUpRequestDto signUpTestDto = UserSignUpRequestDto.builder()
                .email("invalid-email")
                .password("short")
                .build();

        ApiResponseDto failResponse = new ApiResponseDto(false, "User singUp fail: Invalid email or password", null);
        when(userService.signUp(anyString(), anyString())).thenReturn(failResponse);

        // when
        ResponseEntity<?> response = userController.signUp(signUpTestDto);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(((ApiResponseDto) response.getBody()).isSuccess()).isFalse();
        assertThat(((ApiResponseDto) response.getBody()).getMessage()).isEqualTo("User singUp fail: Invalid email or password");
    }


    @Test
    void signInSuccess() {
        // given
        UserSignInRequestDto signInTestDto = UserSignInRequestDto.builder()
                .email("test@test")
                .password("test1234")
                .build();

        ApiResponseDto successResponse = new ApiResponseDto(true, "User signIn success (로그인 성공, 토큰 발급)", "fake_token");
        when(userService.signIn(anyString(), anyString())).thenReturn(successResponse);

        // when
        ResponseEntity<?> response = userController.signIn(signInTestDto);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(((ApiResponseDto) response.getBody()).isSuccess()).isTrue();
        assertThat(((ApiResponseDto) response.getBody()).getMessage()).isEqualTo("User signIn success (로그인 성공, 토큰 발급)");
        assertThat(((ApiResponseDto) response.getBody()).getResponseData()).isEqualTo("fake_token");
    }


    @Test
    void signInFail() {
        // given
        UserSignInRequestDto signInTestDto1 = UserSignInRequestDto.builder()
                .email("test@test")
                .password("uncorrectPWD")
                .build();

        UserSignInRequestDto signInTestDto2 = UserSignInRequestDto.builder()
                .email("notexistEmail")
                .password("test1234")
                .build();

        ApiResponseDto failResponse1 = new ApiResponseDto(false, "User signIn fail (비밀번호가 일치하지 않습니다)", null);
        ApiResponseDto failResponse2 = new ApiResponseDto(false, "User signIn fail (회원정보를 찾을 수 없습니다)", null);

        when(userService.signIn("test@test", "uncorrectPWD")).thenReturn(failResponse1);
        when(userService.signIn("notexistEmail", "test1234")).thenReturn(failResponse2);

        // when
        ResponseEntity<?> response1 = userController.signIn(signInTestDto1);
        ResponseEntity<?> response2 = userController.signIn(signInTestDto2);

        // then
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(((ApiResponseDto) response1.getBody()).isSuccess()).isFalse();
        assertThat(((ApiResponseDto) response1.getBody()).getMessage()).isEqualTo("User signIn fail (비밀번호가 일치하지 않습니다)");
        assertThat(((ApiResponseDto) response1.getBody()).getResponseData()).isNull();

        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(((ApiResponseDto) response2.getBody()).isSuccess()).isFalse();
        assertThat(((ApiResponseDto) response2.getBody()).getMessage()).isEqualTo("User signIn fail (회원정보를 찾을 수 없습니다)");
        assertThat(((ApiResponseDto) response2.getBody()).getResponseData()).isNull();
    }
}