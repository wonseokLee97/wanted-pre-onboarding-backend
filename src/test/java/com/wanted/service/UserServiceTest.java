package com.wanted.service;

import com.wanted.dto.response.ApiResponseDto;
import com.wanted.dto.response.UserSignInResponseDto;
import com.wanted.entity.User;
import com.wanted.repository.UserRepository;
import com.wanted.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void signUp() {
        // given
        String userEmail = "test@example.com";
        String userPwd = "testpassword";

        // when
        when(passwordEncoder.encode(userPwd)).thenReturn("encodedPassword");
        ApiResponseDto response = userService.signUp(userEmail, userPwd);

        // then
        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getMessage()).isEqualTo("User singUp success");
        assertThat(response.getResponseData()).isNull();
    }



    @Test
    void signIn() {
        // given
        String email = "test@example.com";
        String password = "testpassword";
        String encodedPassword = "encodedPassword";
        User user = User.builder()
                .email(email)
                .password(encodedPassword)
                .build();


        // when
        when(userRepository.existsByEmail(email)).thenReturn(true);
        when(userRepository.findByEmail(email)).thenReturn(user);
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);
        when(jwtTokenProvider.createToken(email)).thenReturn("token");

        ApiResponseDto response = userService.signIn(email, password);

        // then
        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getMessage()).isEqualTo("User signIn success (로그인 성공, 토큰 발급)");
        assertThat(response.getResponseData()).isNotNull();
        assertTrue(response.getResponseData() instanceof UserSignInResponseDto);
        assertThat(((UserSignInResponseDto) response.getResponseData()).getEmail()).isEqualTo(email);
    }
}