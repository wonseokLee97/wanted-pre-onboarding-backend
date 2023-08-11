package com.wanted.controller;

import com.wanted.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Board", description = "게시판 관련 API")
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/create")
    @Operation(summary = "새로운 게시글을 생성")
    public ResponseEntity<?> addPost (){
        return null;
    }

    @GetMapping("/list")
    @Operation(summary = "게시글 목록을 조회")
    // 페이지네이션 구현
    public ResponseEntity<?> listBoard (){
        return null;
    }

    @GetMapping("/{postid}")
    @Operation(summary = "게시글 상세 조회")
    public ResponseEntity<?> detailPost (){
        return null;
    }

    @PutMapping("/{postid}")
    @Operation(summary = "게시글 수정")
    public ResponseEntity<?> editPost (){
        return null;
    }

    @DeleteMapping("/postid")
    @Operation(summary = "게시글 삭제")
    public ResponseEntity<?> deletePost (){
        return null;
    }

}
