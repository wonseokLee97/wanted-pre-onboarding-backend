package com.wanted.controller;

import com.wanted.dto.request.PostRequestDto;
import com.wanted.dto.response.ApiResponseDto;
import com.wanted.entity.User;
import com.wanted.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Board", description = "게시판 관련 API")
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 과제 3. 새로운 게시글을 생성하는 엔드포인트
    @PostMapping("/create")
    @Operation(summary = "새로운 게시글을 생성")
    public ResponseEntity<?> addPost (@RequestBody PostRequestDto postRequestDto,
                                      @Parameter(hidden = true)
                                      @AuthenticationPrincipal User user){
        
        ApiResponseDto apiResponseDto;

        // 수정/삭제/등록 엔드포인트는 기본적으로 로그인 회원만 접근이 가능합니다.
        if (user == null) {
            apiResponseDto = new ApiResponseDto(false, "Unauthorized", null);
            return new ResponseEntity<>(apiResponseDto, HttpStatus.FORBIDDEN);
        }

        apiResponseDto = postService.addPost(postRequestDto.getTitle(), postRequestDto.getContent(), user);
        if (apiResponseDto.isSuccess()) {
            return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(apiResponseDto, HttpStatus.BAD_REQUEST);
        }
    }


    // 과제 4. 게시글 목록을 조회하는 엔드포인트
    // 반드시 Pagination 기능을 구현해 주세요.
    @GetMapping("/list")
    @Operation(summary = "게시글 목록을 조회")
    // 페이지네이션 구현
    public ResponseEntity<?> listPost (@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size){
        
        ApiResponseDto apiResponseDto = postService.listPost(page, size);

        if (apiResponseDto.isSuccess()) {
            return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(apiResponseDto, HttpStatus.BAD_REQUEST);
        }
    }


    // 과제 5. 특정 게시글을 조회하는 엔드포인트
    // 게시글의 ID를 받아 해당 게시글을 조회하는 엔드포인트를 구현해 주세요.
    @GetMapping("/{postid}")
    @Operation(summary = "게시글 상세 조회")
    public ResponseEntity<?> detailPost (@PathVariable String postid){
        ApiResponseDto apiResponseDto = postService.detailPost(postid);

        if (apiResponseDto.isSuccess()) {
            return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(apiResponseDto, HttpStatus.BAD_REQUEST);
        }
    }


    // 과제 6. 특정 게시글을 수정하는 엔드포인트
    // 게시글의 ID와 수정 내용을 받아 해당 게시글을 수정하는 엔드포인트를 구현해 주세요.
    @PutMapping("/{postid}")
    @Operation(summary = "게시글 수정")
    public ResponseEntity<?> editPost (@PathVariable String postid,
                                       @RequestBody PostRequestDto postRequestDto,
                                       @Parameter(hidden = true)
                                       @AuthenticationPrincipal User user){
        ApiResponseDto apiResponseDto = postService.editPost(postid, postRequestDto, user);

        // 수정/삭제/등록 엔드포인트는 기본적으로 로그인 회원만 접근이 가능합니다.
        if (user == null) {
            apiResponseDto = new ApiResponseDto(false, "Unauthorized", null);
            return new ResponseEntity<>(apiResponseDto, HttpStatus.FORBIDDEN);
        }


        if (apiResponseDto.isSuccess()) {
            return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(apiResponseDto, HttpStatus.BAD_REQUEST);
        }
    }



    // 과제 7. 특정 게시글을 삭제하는 엔드포인트
    // 게시글의 ID를 받아 해당 게시글을 삭제하는 엔드포인트를 구현해 주세요.
    @DeleteMapping("/{postid}")
    @Operation(summary = "게시글 삭제")
    public ResponseEntity<?> deletePost (@PathVariable String postid,
                                         @Parameter(hidden = true)
                                         @AuthenticationPrincipal User user){
        ApiResponseDto apiResponseDto = postService.deletePost(postid, user);

        // 수정/삭제/등록 엔드포인트는 기본적으로 로그인 회원만 접근이 가능합니다.
        if (user == null) {
            apiResponseDto = new ApiResponseDto(false, "Unauthorized", null);
            return new ResponseEntity<>(apiResponseDto, HttpStatus.FORBIDDEN);
        }

        if (apiResponseDto.isSuccess()) {
            return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(apiResponseDto, HttpStatus.BAD_REQUEST);
        }
    }

}
