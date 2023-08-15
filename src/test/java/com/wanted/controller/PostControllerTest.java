package com.wanted.controller;

import com.wanted.dto.request.PostRequestDto;
import com.wanted.dto.response.ApiResponseDto;
import com.wanted.dto.response.PostResponseDto;
import com.wanted.entity.Post;
import com.wanted.entity.User;
import com.wanted.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class PostControllerTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addPost() {
        // given
        User user = User.builder()
                .id(1L)
                .email("test@test")
                .password("test1234")
                .build();

        PostRequestDto postRequestDto = new PostRequestDto("testTitle", "testContent");

        // when
        when(postService.addPost("testTitle", "testContent", user)).thenReturn(new ApiResponseDto(true, "Post successfully added", null));
        ResponseEntity<?> response = postController.addPost(postRequestDto, user);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(((ApiResponseDto) response.getBody()).isSuccess()).isTrue();
        assertThat(((ApiResponseDto) response.getBody()).getMessage()).isEqualTo("Post successfully added");
    }

    @Test
    void addPostUnauthorized() {
        // given
        PostRequestDto postRequestDto = new PostRequestDto("testTitle", "testContent");

        // when
        ResponseEntity<?> response = postController.addPost(postRequestDto, null); // Unauthorized user

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(((ApiResponseDto) response.getBody()).isSuccess()).isFalse();
        assertThat(((ApiResponseDto) response.getBody()).getMessage()).isEqualTo("Unauthorized");
    }


    @Test
    void listPost() {
        // given
        int page = 0;
        int size = 10;
        List<PostResponseDto> postList = Arrays.asList(
                new PostResponseDto(1L, "Title 1", "Content 1"),
                new PostResponseDto(2L, "Title 2", "Content 2"),
                new PostResponseDto(3L, "Title 3", "Content 3"),
                new PostResponseDto(4L, "Title 4", "Content 4"),
                new PostResponseDto(5L, "Title 5", "Content 5")
        );


        // when
        when(postService.listPost(page, size)).thenReturn(new ApiResponseDto(true, "PostList successfully load", postList));
        ResponseEntity<?> response = postController.listPost(page, size);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(((ApiResponseDto) response.getBody()).isSuccess()).isTrue();
        assertThat(((ApiResponseDto) response.getBody()).getMessage()).isEqualTo("PostList successfully load");
        assertThat(((ApiResponseDto) response.getBody()).getResponseData()).isEqualTo(postList);
    }


    @Test
    void detailPost() {
        // given
        String postId = "1";
        Post post = Post.builder()
                .id(Long.parseLong(postId))
                .title("Test Title")
                .content("Test Content")
                .build();

        when(postService.detailPost(postId)).thenReturn(new ApiResponseDto(true, "PostDetail successfully load", post.toString()));

        // when
        ResponseEntity<?> response = postController.detailPost(postId);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(((ApiResponseDto) response.getBody()).isSuccess()).isTrue();
        assertThat(((ApiResponseDto) response.getBody()).getMessage()).isEqualTo("PostDetail successfully load");
        assertThat(((ApiResponseDto) response.getBody()).getResponseData()).isEqualTo(post.toString());
    }


    @Test
    void editPost() {
        // given
        User user = User.builder()
                .id(1L)
                .email("test@test")
                .password("test1234")
                .build();

        String postId = "1";
        PostRequestDto postRequestDto = new PostRequestDto("Edited Title", "Edited Content");

        // when
        when(postService.editPost(postId, postRequestDto, user))
                .thenReturn(new ApiResponseDto(true, "Post successfully edit", null));
        ResponseEntity<?> response = postController.editPost(postId, postRequestDto, user);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(((ApiResponseDto) response.getBody()).isSuccess()).isTrue();
        assertThat(((ApiResponseDto) response.getBody()).getMessage()).isEqualTo("Post successfully edit");
    }


    @Test
    void deletePost() {
        // given
        User user = User.builder()
                .id(1L)
                .email("test@test")
                .password("test1234")
                .build();

        String postId = "1";

        // when
        when(postService.deletePost(postId, user))
                .thenReturn(new ApiResponseDto(true, "Post successfully delete", null));
        ResponseEntity<?> response = postController.deletePost(postId, user);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(((ApiResponseDto) response.getBody()).isSuccess()).isTrue();
        assertThat(((ApiResponseDto) response.getBody()).getMessage()).isEqualTo("Post successfully delete");
    }
}