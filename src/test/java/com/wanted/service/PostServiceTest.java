package com.wanted.service;

import com.wanted.dto.request.PostRequestDto;
import com.wanted.dto.response.ApiResponseDto;
import com.wanted.entity.Post;
import com.wanted.entity.User;
import com.wanted.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

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
        when(postRepository.save(any(Post.class))).thenReturn(new Post());
        ApiResponseDto response = postService.addPost(postRequestDto.getTitle(), postRequestDto.getContent(), user);

        // then
        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getMessage()).isEqualTo("Post successfully added");
    }

    @Test
    void listPost() {
        // given
        int page = 0;
        int size = 10;
        List<Post> postList = new ArrayList<>();
        postList.add(new Post());
        postList.add(new Post());
        postList.add(new Post());

        Page<Post> postListPageable = new PageImpl<>(postList);

        // when
        when(postRepository.findAll(any(Pageable.class))).thenReturn(postListPageable);
        ApiResponseDto response = postService.listPost(page, size);


        // then
        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getMessage()).isEqualTo("PostList successfully load");
        assertThat(response.getResponseData()).isNotNull();
        assertThat(response.getResponseData()).isInstanceOf(List.class);
        assertThat(((List<?>) response.getResponseData())).hasSize(3);
    }


    @Test
    void detailPost() {
        // given
        Long postId = 1L;
        Post post = Post.builder()
                .id(postId)
                .title("testTitle")
                .content("testContent")
                .build();

        // 예상되는 결과값
        PostRequestDto expectedResponseData = PostRequestDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .build();

        // when
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        ApiResponseDto response = postService.detailPost(postId.toString());

        // then
        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getMessage()).isEqualTo("PostDetail successfully load");
        assertThat(response.getResponseData()).isNotNull();
        assertThat(response.getResponseData()).isEqualTo(expectedResponseData);
    }

    @Test
    void editPost() {
        // given
        User user = User.builder()
                .id(1L) // 작성자 ID 설정
                .email("test@test")
                .password("test1234")
                .build();

        Long postId = 1L;
        Post post = Post.builder()
                .id(postId)
                .title("Original Title")
                .content("Original Content")
                .user(user) // 작성자 정보 설정
                .build();

        PostRequestDto postRequestDto = new PostRequestDto("Edited Title", "Edited Content");


        // when
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0)); // 수정한 Post 객체를 그대로 반환하도록 설정
        ApiResponseDto response = postService.editPost(postId.toString(), postRequestDto, user);
        System.out.println(response);

        // 변환된 PostRequestDto 생성
        PostRequestDto expectedResponseData = PostRequestDto.builder()
                .title("Edited Title")
                .content("Edited Content")
                .build();


        // then
        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getMessage()).isEqualTo("Post successfully edit");
        assertThat(response.getResponseData()).isNotNull();
        assertThat(response.getResponseData()).isEqualTo(expectedResponseData);
    }


    @Test
    void editPostFail() {
        // given
        User user = User.builder()
                .id(1L) // 작성자 ID 설정
                .email("test@test")
                .password("test1234")
                .build();

        User user2 = User.builder()
                .id(2L) // 작성자 ID 설정
                .email("otherman@test")
                .password("test1234")
                .build();

        Long postId = 1L;
        Post post = Post.builder()
                .id(postId)
                .title("Original Title")
                .content("Original Content")
                .user(user) // 작성자 정보 설정
                .build();

        PostRequestDto postRequestDto = new PostRequestDto("Edited Title", "Edited Content");


        // when
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0)); // 수정한 Post 객체를 그대로 반환하도록 설정
        ApiResponseDto response = postService.editPost(postId.toString(), postRequestDto, user2);

        // then
        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getMessage()).isEqualTo("Unauthorized");
        assertThat(response.getResponseData()).isNull();
    }

    @Test
    void deletePost() {
        // given
        User user = User.builder()
                .id(1L) // 작성자 ID 설정
                .email("test@test")
                .password("test1234")
                .build();

        Long postId = 1L;
        Post post = Post.builder()
                .id(postId)
                .title("Original Title")
                .content("Original Content")
                .user(user) // 작성자 정보 설정
                .build();


        // when
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        ApiResponseDto response = postService.deletePost(String.valueOf(postId), user);

        // then
        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getMessage()).isEqualTo("Post successfully delete");
        assertThat(response.getResponseData()).isNull();
    }

    @Test
    void deletePostFail() {
        // given
        User user = User.builder()
                .id(1L) // 작성자 ID 설정
                .email("test@test")
                .password("test1234")
                .build();

        User user2 = User.builder()
                .id(2L) // 작성자 ID 설정
                .email("otherman@test")
                .password("test1234")
                .build();

        Long postId = 1L;
        Post post = Post.builder()
                .id(postId)
                .title("Original Title")
                .content("Original Content")
                .user(user) // 작성자 정보 설정
                .build();

        PostRequestDto postRequestDto = new PostRequestDto("Edited Title", "Edited Content");


        // when
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        ApiResponseDto response = postService.deletePost(String.valueOf(postId), user2);

        // then
        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getMessage()).isEqualTo("Unauthorized");
        assertThat(response.getResponseData()).isNull();
    }
}