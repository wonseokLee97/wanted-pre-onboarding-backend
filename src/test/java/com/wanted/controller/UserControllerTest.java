package com.wanted.controller;

import com.wanted.dto.request.UserSignInRequestDto;
import com.wanted.dto.request.UserSignUpRequestDto;
import com.wanted.dto.response.ApiResponseDto;
import com.wanted.security.JwtTokenProvider;
import com.wanted.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Mock
    private UserService userService; // UserService 의 Mock 객체

    @InjectMocks
    private UserController userController; // UserController 의 Mock 객체 주입 대상


    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this); // Mock 객체 초기화
    }

    @Test
    void signUpSuccess() {
        // given : 회원가입을 진행
        UserSignUpRequestDto signUpTestDto = UserSignUpRequestDto.builder()
                .email("test@test.com")
                .password("test1234")
                .build();

        // when : 회원가입이 성공했을 때 예상되는 결과값
        ResponseEntity<ApiResponseDto> response = userController.signUp(signUpTestDto);


        // then : 실제 결과값을 대조
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().isSuccess()).isTrue();
        assertThat(response.getBody().getMessage()).isEqualTo("User singUp success");
    }

    @Test
    void signUpFail() {
        // given : 회원가입을 진행

        // 아이디 형식이 올바르지 않은 경우
        UserSignUpRequestDto signUpTestDto1 = UserSignUpRequestDto.builder()
                .email("test")
                .password("test1234")
                .build();

        // 비밀번호 형식이 올바르지 않은 경우
        UserSignUpRequestDto signUpTestDto2 = UserSignUpRequestDto.builder()
                .email("test@test")
                .password("test")
                .build();

        // when
        ResponseEntity<ApiResponseDto> response1 = userController.signUp(signUpTestDto1);
        ResponseEntity<ApiResponseDto> response2 = userController.signUp(signUpTestDto2);

        // then
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response1.getBody().isSuccess()).isFalse();
        assertThat(response1.getBody().getMessage()).isEqualTo("User singUp fail");

        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response1.getBody().isSuccess()).isFalse();
        assertThat(response2.getBody().getMessage()).isEqualTo("User singUp fail");
    }

    @Test
    void signInSuccess() {
        // given
        UserSignInRequestDto signInTestDto = UserSignInRequestDto.builder()
                .email("test@test")
                .password("test1234")
                .build();


        // when
        ResponseEntity<ApiResponseDto> response = userController.signIn(signInTestDto);


        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().isSuccess()).isTrue();
        assertThat(response.getBody().getMessage()).isEqualTo("User signIn success (로그인 성공, 토큰 발급)");
        assertThat(response.getBody().getResponseData()).isNotEqualTo(null);
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

        // when
        ResponseEntity<ApiResponseDto> response1 = userController.signIn(signInTestDto1);
        ResponseEntity<ApiResponseDto> response2 = userController.signIn(signInTestDto2);

        // then
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response1.getBody().isSuccess()).isFalse();
        assertThat(response1.getBody().getMessage()).isEqualTo("User singIn fail (비밀번호가 일치하지 않습니다)");
        assertThat(response1.getBody().getResponseData()).isEqualTo(null);

        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response2.getBody().isSuccess()).isFalse();
        assertThat(response2.getBody().getMessage()).isEqualTo("User singIn fail (회원정보를 찾을 수 없습니다)");
        assertThat(response2.getBody().getResponseData()).isEqualTo(null);
    }
}