package today.what_should_i_eat_today.domain.post.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import today.what_should_i_eat_today.domain.activity.dto.PostCreateCommand;
import today.what_should_i_eat_today.domain.activity.dto.PostCreateRequest;
import today.what_should_i_eat_today.domain.activity.dto.PostUpdateCommand;
import today.what_should_i_eat_today.domain.activity.dto.PostUpdateRequest;
import today.what_should_i_eat_today.domain.post.application.PostService;
import today.what_should_i_eat_today.domain.post.dto.PostResponseDto;
import today.what_should_i_eat_today.domain.post.dto.PostResponseDtoV1;
import today.what_should_i_eat_today.domain.post.entity.Post;
import today.what_should_i_eat_today.global.security.CurrentUser;
import today.what_should_i_eat_today.global.security.UserPrincipal;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostApi {

    private final PostService postService;

    /**
     * 글 단 건 조회
     */
    @GetMapping("/posts/{id}")
    @Secured("ROLE_USER")
    public ResponseEntity<?> getPost(@CurrentUser UserPrincipal principal, @PathVariable("id") Long postId) {

        Post post = postService.getPost(principal, postId);

        return ResponseEntity.ok(PostResponseDto.from(post));
    }

    /**
     * 글 리스트 조회
     */
    @GetMapping("/posts")
    @Secured("ROLE_USER")
    public ResponseEntity<?> getPosts(@RequestParam(required = false, name = "memberId") Long memberId, @PageableDefault Pageable pageable) {

        Page<Post> posts = postService.getPosts(pageable);


        return ResponseEntity.ok(posts.map(PostResponseDtoV1::new));
    }

    /**
     * 내가 작성한 글 리스트 조회
     */
    @GetMapping("/posts/my")
    @Secured("ROLE_USER")
    public ResponseEntity<?> getPosts(@CurrentUser UserPrincipal userPrincipal, @PageableDefault Pageable pageable) {
        Page<Post> posts = postService.getPostByMember(userPrincipal.getId(), pageable);

        return ResponseEntity.ok(posts.map(PostResponseDtoV1::new));
    }

    /**
     * 내가 작성한 글 갯수 조회
     */
    @GetMapping("/posts/my/count")
    @Secured("ROLE_USER")
    public ResponseEntity<?> myPostCount(@CurrentUser UserPrincipal principal) {

        return ResponseEntity.ok(postService.myPostCount(principal.getId()));
    }

    /**
     * 내가 찜한 글 리스트 조회
     */
    @GetMapping("/posts/favorite")
    @Secured("ROLE_USER")
    public ResponseEntity<?> getPostsFavorite(@CurrentUser UserPrincipal userPrincipal, @PageableDefault Pageable pageable) {

        Page<Post> posts = postService.getPostsFavorite(userPrincipal.getId(), pageable);


        return ResponseEntity.ok(posts.map(PostResponseDtoV1::new));
    }

    /**
     * 내가 찜한 글 갯수 조회
     */
    @GetMapping("/posts/favorite/count")
    @Secured("ROLE_USER")
    public ResponseEntity<?> favoriteCount(@CurrentUser UserPrincipal principal) {

        return ResponseEntity.ok(postService.favoriteCount(principal.getId()));
    }

    /**
     * 글 리스트 랜덤 10개 조회
     */
    @GetMapping("/posts/random")
    public ResponseEntity<?> getPostsRandom() {

        List<Post> posts = postService.getRandomPostList();

        return ResponseEntity.ok(posts.stream().map(PostResponseDtoV1::new));
    }

    /**
     * 최근 올라온 글 리스트 조회
     */
    @GetMapping("/posts/recently")
    public ResponseEntity<?> getPostsRecently(@CurrentUser UserPrincipal principal, @PageableDefault @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Post> postPage = postService.getPostsRecently(principal, pageable);

        return ResponseEntity.ok(postPage.map(PostResponseDtoV1::fromPostRecentlyDto));
    }

    /**
     * 현재 음식의 최근 게시물 가져오기
     */
    @GetMapping("/posts/foods/{id}")
    public ResponseEntity<?> getRecentPostsOfCurrentFood(
            @CurrentUser UserPrincipal principal,
            @PathVariable("id") Long foodId,
            @PageableDefault @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {

        Page<Post> postPage = postService.getRecentPostsOfCurrentFood(principal, foodId, pageable);

        return ResponseEntity.ok(postPage.map(PostResponseDtoV1::fromPostRecentlyDto));
    }

    /**
     * 글 찜 하기
     */
    @PostMapping("/posts/{id}/favorite")
    @Secured("ROLE_USER")
    public ResponseEntity<?> favorite(@CurrentUser UserPrincipal principal, @PathVariable("id") Long postId) {
        postService.favorite(postId, principal.getId());

        return ResponseEntity.ok().build();
    }

    /**
     * 글 찜 삭제하기
     */
    @DeleteMapping("/posts/{id}/favorite")
    @Secured("ROLE_USER")
    public ResponseEntity<?> unFavorite(@CurrentUser UserPrincipal principal, @PathVariable("id") Long postId) {

        postService.unFavorite(postId, principal.getId());

        return ResponseEntity.ok().build();
    }

    /**
     * 좋아요 한 글 리스트 조회
     */
    @GetMapping("/posts/liked")
    @Secured("ROLE_USER")
    public ResponseEntity<?> getPostsLiked(@CurrentUser UserPrincipal userPrincipal, @PageableDefault Pageable pageable) {

        Page<Post> posts = postService.getPostsLiked(userPrincipal.getId(), pageable);

        return ResponseEntity.ok(posts.map(PostResponseDtoV1::new));
    }

    /**
     * 글 좋아요 갯수 조회
     */
    @GetMapping("/posts/liked/count")
    @Secured("ROLE_USER")
    public ResponseEntity<?> likeCount(@CurrentUser UserPrincipal principal) {

        return ResponseEntity.ok(postService.likeCount(principal.getId()));
    }

    /**
     * 글 좋아요 하기
     */
    @PostMapping("/posts/{id}/like")
    @Secured("ROLE_USER")
    public ResponseEntity<?> like(@CurrentUser UserPrincipal principal, @PathVariable("id") Long postId) {

        postService.like(postId, principal.getId());

        return ResponseEntity.ok().build();
    }

    /**
     * 글 좋아요 삭제하기
     */
    @DeleteMapping("/posts/{id}/like")
    @Secured("ROLE_USER")
    public ResponseEntity<?> dislike(@CurrentUser UserPrincipal principal, @PathVariable("id") Long postId) {

        postService.dislike(postId, principal.getId());

        return ResponseEntity.ok().build();
    }

    /**
     * 글 작성하기
     */
    @PostMapping("/posts")
    @Secured("ROLE_USER")
    public ResponseEntity<?> createPost(@CurrentUser UserPrincipal principal, MultipartFile file, @ModelAttribute @Valid PostCreateRequest requestDto) {
        PostCreateCommand postCreateCommand = requestDto.toCommand(principal.getId(), file);

        Post post = postService.createPost(postCreateCommand);

        return ResponseEntity.ok(new PostResponseDtoV1(post));
    }


    /**
     * 내가 작성한 글 수정하기
     */
    @PutMapping("/posts/{id}")
    @Secured("ROLE_USER")
    public ResponseEntity<?> updatePost(@CurrentUser UserPrincipal principal, @ModelAttribute @Valid PostUpdateRequest requestDto, @PathVariable("id") Long postId) {

        PostUpdateCommand postUpdateCommand = requestDto.toCommand(principal.getId(), postId);

        Post post = postService.updatePost(postUpdateCommand);

        return ResponseEntity.ok(post);
    }

    /**
     * 내가 작성한 글 삭제하기
     */
    @DeleteMapping("/posts/{id}")
    @Secured("ROLE_USER")
    public ResponseEntity<?> deletePost(@CurrentUser UserPrincipal principal, @PathVariable("id") Long postId) {

        postService.deletePost(postId, principal.getId());

        return ResponseEntity.ok("deleted");
    }
}
