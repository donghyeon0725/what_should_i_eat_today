package today.what_should_i_eat_today.domain.post.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import today.what_should_i_eat_today.domain.activity.dto.PostCreateCommand;
import today.what_should_i_eat_today.domain.activity.dto.PostCreateRequest;
import today.what_should_i_eat_today.domain.activity.dto.PostUpdateCommand;
import today.what_should_i_eat_today.domain.activity.dto.PostUpdateRequest;
import today.what_should_i_eat_today.domain.post.application.PostService;
import today.what_should_i_eat_today.domain.post.dto.PostResponseDtoV1;
import today.what_should_i_eat_today.domain.post.entity.Post;
import today.what_should_i_eat_today.global.security.CurrentUser;
import today.what_should_i_eat_today.global.security.UserPrincipal;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostApi {

    private final PostService postService;

    @GetMapping("/posts/{id}")
    public ResponseEntity<?> getPost(@PathVariable("id") Long postId) {

        Post post = postService.getPost(postId);

        return ResponseEntity.ok(new PostResponseDtoV1(post));
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getPosts(@RequestParam(required = false, name = "memberId") Long memberId, @PageableDefault Pageable pageable) {

        Page<Post> posts = postService.getPosts(pageable);


        return ResponseEntity.ok(posts.map(PostResponseDtoV1::new));
    }

    @GetMapping("/posts/my")
    public ResponseEntity<?> getPosts(@CurrentUser UserPrincipal userPrincipal, @PageableDefault Pageable pageable) {
        Page<Post> posts = postService.getPostByMember(userPrincipal.getId(), pageable);

        return ResponseEntity.ok(posts.map(PostResponseDtoV1::new));
    }


    @GetMapping("/posts/liked")
    public ResponseEntity<?> getPostsLiked(@CurrentUser UserPrincipal userPrincipal, @PageableDefault Pageable pageable) {

        Page<Post> posts = postService.getPostsLiked(userPrincipal.getId(), pageable);

        return ResponseEntity.ok(posts.map(PostResponseDtoV1::new));
    }


    @GetMapping("/posts/favorite")
    public ResponseEntity<?> getPostsFavorite(@CurrentUser UserPrincipal userPrincipal, @PageableDefault Pageable pageable) {

        Page<Post> posts = postService.getPostsLiked(userPrincipal.getId(), pageable);


        return ResponseEntity.ok(posts.map(PostResponseDtoV1::new));
    }

    @GetMapping("/posts/foods/{id}")
    public ResponseEntity<?> getPostsByFoodId(@PageableDefault Pageable pageable, @PathVariable("id") Long foodId) {

        Page<Post> posts = postService.getPostsByFoodId(foodId, pageable);

        return ResponseEntity.ok(posts);
    }

    @PostMapping("/posts/{id}/like")
    public ResponseEntity<?> updateLike(@CurrentUser UserPrincipal principal, @PathVariable("id") Long postId) {

        Long userId = principal.getId();

        return ResponseEntity.ok(postService.updateLike(postId, userId));
    }

    @PostMapping("/posts/{id}/favorite")
    public ResponseEntity<?> updateFavorite(@CurrentUser UserPrincipal principal, @PathVariable("id") Long postId) {

        Long userId = principal.getId();

        return ResponseEntity.ok(postService.updateFavorite(postId, userId));
    }

    @PostMapping("/posts")
    public ResponseEntity<?> createPost(@CurrentUser UserPrincipal principal, @ModelAttribute @Valid PostCreateRequest requestDto) {

        PostCreateCommand postCreateCommand = requestDto.toCommand(principal.getId());

        Post post = postService.createPost(postCreateCommand);

        return ResponseEntity.ok(post);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<?> updatePost(@CurrentUser UserPrincipal principal, @ModelAttribute @Valid PostUpdateRequest requestDto, @PathVariable("id") Long postId) {

        PostUpdateCommand postUpdateCommand = requestDto.toCommand(principal.getId(), postId);

        Post post = postService.updatePost(postUpdateCommand);

        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<?> deletePost(@CurrentUser UserPrincipal principal, @PathVariable("id") Long postId) {

        postService.deletePost(postId, principal.getId());

        return ResponseEntity.ok("deleted");
    }
}
