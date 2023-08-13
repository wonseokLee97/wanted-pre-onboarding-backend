package com.wanted.service;

import com.wanted.dto.response.ApiResponseDto;
import com.wanted.entity.Board;
import com.wanted.entity.User;
import com.wanted.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(BoardService.class);


    // 과제 3. 새로운 게시글을 생성하는 엔드포인트
    public ApiResponseDto addPost(String title, String content, User user) {
        try {
            Board post = boardRepository.save(Board.builder()
                    .title(title)
                    .content(content)
                    .user(user)
                    .build());

            return new ApiResponseDto(
                    true,
                    "Post successfully added",
                    post
            );
        } catch (Exception e) {
            LOGGER.error("Error while adding post: " + e.getMessage());
            return new ApiResponseDto(
                    false,
                    "Failed to add post: " + e.getMessage(),
                    null
            );
        }
    }


    // 과제 4. 게시글 목록을 조회하는 엔드포인트
    // 반드시 Pagination 기능을 구현해 주세요.
    public ApiResponseDto listBoard() {
        try {
            List<Board> boardList = boardRepository.findAll();
            return new ApiResponseDto(
                    true,
                    "BoardList successfully load",
                    boardList
            );
        } catch (Exception e) {
            LOGGER.error("Error while load list: " + e.getMessage());
            return new ApiResponseDto(
                    false,
                    "Failed to load list: " + e.getMessage(),
                    null
            );
        }
    }


    // 과제 5. 특정 게시글을 조회하는 엔드포인트
    // 게시글의 ID를 받아 해당 게시글을 조회하는 엔드포인트를 구현해 주세요.
    public ApiResponseDto detailPost(String postid) {
        try {
            Board board = boardRepository.findById(Long.valueOf(postid)).orElseThrow();
            return new ApiResponseDto(
                    true,
                    "PostDetail successfully load",
                    board
            );

        } catch (Exception e) {
            LOGGER.error("Error while read Post: " + e.getMessage());
            return new ApiResponseDto(
                    false,
                    "Failed to read Post: " + e.getMessage(),
                    null
            );
        }
    }

    // 과제 6. 특정 게시글을 수정하는 엔드포인트
    // 게시글의 ID와 수정 내용을 받아 해당 게시글을 수정하는 엔드포인트를 구현해 주세요.
    // 게시글을 수정할 수 있는 사용자는 게시글 작성자만이어야 합니다.
    public ApiResponseDto editPost(String postid) {
        try {
            Board board = boardRepository.findById(Long.valueOf(postid)).orElseThrow();
            

            return new ApiResponseDto(
                    true,
                    "PostDetail successfully load",
                    board
            );

        } catch (Exception e) {
            LOGGER.error("Error while read Post: " + e.getMessage());
            return new ApiResponseDto(
                    false,
                    "Failed to read Post: " + e.getMessage(),
                    null
            );
        }
    }




    // 과제 7. 특정 게시글을 삭제하는 엔드포인트
    // 게시글의 ID를 받아 해당 게시글을 삭제하는 엔드포인트를 구현해 주세요.
    // 게시글을 삭제할 수 있는 사용자는 게시글 작성자만이어야 합니다.
}
