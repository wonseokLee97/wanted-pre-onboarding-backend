package com.wanted.controller;

import com.wanted.dto.request.UserSignInRequestDto;
import com.wanted.dto.request.UserSignUpRequestDto;
import com.wanted.dto.response.ApiResponseDto;
import com.wanted.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "유저 관련 API")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    public ResponseEntity<?> signUp(@RequestBody UserSignUpRequestDto userRequestDto){
        LOGGER.info("[signUp] 회원가입을 수행합니다. email : {}, password : ****", userRequestDto.getEmail());
        ApiResponseDto apiResponseDto = userService.signUp(userRequestDto.getEmail(), userRequestDto.getPassword());
        LOGGER.info("{}", apiResponseDto);

        // 회원가입에 성공한경우
        if (apiResponseDto.isSuccess()) {
            return new ResponseEntity(apiResponseDto, HttpStatus.OK);
        }
        // 회원가입에 실패한 경우
        else {
            return new ResponseEntity(apiResponseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/signin")
    @Operation(summary = "로그인")
    public ResponseEntity<?> signIn(@RequestBody UserSignInRequestDto userLoginDto){
        LOGGER.info("[signIn] 로그인을 시도합니다. id : {}, pw : ****", userLoginDto.getEmail());
        ApiResponseDto apiResponseDto = userService.signIn(userLoginDto.getEmail(), userLoginDto.getPassword());

        // 로그인에 성공한경우
        if (apiResponseDto.isSuccess()) {
            return new ResponseEntity(apiResponseDto, HttpStatus.OK);
        }
        // 로그인에 실패한 경우
        else {
            return new ResponseEntity(apiResponseDto, HttpStatus.UNAUTHORIZED);
        }

    }
}
