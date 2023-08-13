package com.wanted.service;

import com.wanted.dto.request.PostRequestDto;
import com.wanted.dto.response.ApiResponseDto;
import com.wanted.entity.Post;
import com.wanted.entity.User;
import com.wanted.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(PostService.class);



    public ApiResponseDto addPost(String title, String content, User user) {
        try {
            Post post = postRepository.save(Post.builder()
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



    public ApiResponseDto listPost(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);

            Page<Post> postListPageable = postRepository.findAll(pageable);
            return new ApiResponseDto(
                    true,
                    "BoardList successfully load",
                    postListPageable
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



    public ApiResponseDto detailPost(String postid) {
        try {
            Post post = postRepository.findById(Long.valueOf(postid)).orElseThrow();
            return new ApiResponseDto(
                    true,
                    "PostDetail successfully load",
                    post
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


    public ApiResponseDto editPost(String postid, PostRequestDto postRequestDto, User user) {
        try {
            Post post = postRepository.findById(Long.valueOf(postid)).orElseThrow();

            // 게시글을 수정할 수 있는 사용자는 게시글 작성자만이어야 합니다.
            if (post.getUser().getId() != user.getId()) {
                return new ApiResponseDto(
                        false,
                        "Unauthorized",
                        null);
            }

            Post updatedPost = Post.builder()
                    .id(post.getId())
                    .title(postRequestDto.getTitle())
                    .content(postRequestDto.getContent())
                    .user(post.getUser())
                    .build();

            updatedPost = postRepository.save(updatedPost);

            return new ApiResponseDto(
                    true,
                    "Post successfully edit",
                    updatedPost
            );

        } catch (Exception e) {
            LOGGER.error("Error while edit Post: " + e.getMessage());
            return new ApiResponseDto(
                    false,
                    "Failed to edit Post: " + e.getMessage(),
                    null
            );
        }
    }


    public ApiResponseDto deletePost(String postid, User user) {
        try {
            Post post = postRepository.findById(Long.valueOf(postid)).orElseThrow();

            // 게시글을 삭제할 수 있는 사용자는 게시글 작성자만이어야 합니다.
            if (post.getUser().getId() != user.getId()) {
                return new ApiResponseDto(
                        false,
                        "Unauthorized",
                        null);
            }

            postRepository.deleteById(Long.valueOf(postid));

            return new ApiResponseDto(
                    true,
                    "Post successfully delete",
                    null
            );

        } catch (Exception e) {
            LOGGER.error("Error while delete Post: " + e.getMessage());
            return new ApiResponseDto(
                    false,
                    "Failed to delete Post: " + e.getMessage(),
                    null
            );
        }
    }





}
