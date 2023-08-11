package com.wanted.service;

import com.wanted.dto.response.ApiResponseDto;
import com.wanted.dto.response.UserSignInResponseDto;
import com.wanted.entity.User;
import com.wanted.repository.UserRepository;
import com.wanted.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // 과제 1. 사용자 회원가입 엔드포인트
    // 이메일과 비밀번호로 회원가입할 수 있는 엔드포인트를 구현해 주세요.
    // 이메일과 비밀번호에 대한 유효성 검사를 구현해 주세요.
    // 이메일 조건: @ 포함
    // 비밀번호 조건: 8자 이상
    // 비밀번호는 반드시 암호화하여 저장해 주세요.
    // 이메일과 비밀번호의 유효성 검사는 위의 조건만으로 진행해 주세요. 추가적인 유효성 검사 조건은 포함하지 마세요.
    public ApiResponseDto signUp(String userEmail, String userPwd) {
        ApiResponseDto apiResponseDto;

        // 유효성 검사 성공시에 회원가입을 진행한다.
        if (isValidate(userEmail, userPwd)) {
            userRepository.save(User.builder()
                    .email(userEmail)
                    .password(passwordEncoder.encode(userPwd))
                    .build());

            apiResponseDto = new ApiResponseDto(
                    true,
                    "User singUp success",
                    null
            );

        } else {
            apiResponseDto = new ApiResponseDto(
                    false,
                    "User singUp fail",
                    null
            );
        }

        return apiResponseDto;
    }


    // 과제 2. 사용자 로그인 엔드포인트
    // 사용자가 올바른 이메일과 비밀번호를 제공하면, 사용자 인증을 거친 후에 JWT(JSON Web Token)를 생성하여 사용자에게 반환하도록 해주세요.
    // 과제 1과 마찬가지로 회원가입 엔드포인트에 이메일과 비밀번호의 유효성 검사기능을 구현해주세요.
    public ApiResponseDto signIn(String email, String password) {
        ApiResponseDto apiResponseDto;
        // 회원 정보 요청
        boolean existedUser = userRepository.existsByEmail(email);

        // 회원 정보 없을 경우
        if(!existedUser){
            apiResponseDto = new ApiResponseDto(
                    false,
                    "User singIn fail (회원정보를 찾을 수 없습니다)",
                    null);
            return apiResponseDto;
        }


        User user = userRepository.findByEmail(email);

        // 패스워드가 일치하지 않을때
        if(!passwordEncoder.matches(password, user.getPassword())){
            apiResponseDto = new ApiResponseDto(
                    false,
                    "User singIn fail (비밀번호가 일치하지 않습니다)",
                    null);
            return apiResponseDto;
        }

        UserSignInResponseDto dto = UserSignInResponseDto.builder()
                .email(user.getEmail())
                .token(jwtTokenProvider.createToken(String.valueOf(user.getEmail()))).build();

        apiResponseDto = new ApiResponseDto(
                true,
                "User signIn success (로그인 성공, 토큰 발급)",
                dto
        );

        return apiResponseDto;
    }



    boolean isValidate(String userEmail, String userPwd) {
        // 이메일 조건: @ 포함
        // 비밀번호 조건: 8자 이상
        if (userEmail.contains("@") && userPwd.length() >= 8) {
            return true;
        }
        return false;
    }
}
