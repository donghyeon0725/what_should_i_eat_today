package today.what_should_i_eat_today.domain.post.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import today.what_should_i_eat_today.domain.activity.dto.PostCreateCommand;
import today.what_should_i_eat_today.domain.activity.dto.PostCreateRequest;
import today.what_should_i_eat_today.domain.activity.dto.PostUpdateCommand;
import today.what_should_i_eat_today.domain.activity.dto.PostUpdateRequest;
import today.what_should_i_eat_today.domain.post.application.PostService;
import today.what_should_i_eat_today.domain.post.dto.PostResponseDtoV1;
import today.what_should_i_eat_today.domain.post.entity.Post;
import today.what_should_i_eat_today.global.security.CurrentUser;
import today.what_should_i_eat_today.global.security.UserPrincipal;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

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

    @GetMapping("/posts/my/count")
    public ResponseEntity<?> myPostCount(@CurrentUser UserPrincipal principal) {

        return ResponseEntity.ok(postService.myPostCount(principal.getId()));
    }

    @GetMapping("/posts/favorite")
    public ResponseEntity<?> getPostsFavorite(@CurrentUser UserPrincipal userPrincipal, @PageableDefault Pageable pageable) {

        Page<Post> posts = postService.getPostsFavorite(userPrincipal.getId(), pageable);


        return ResponseEntity.ok(posts.map(PostResponseDtoV1::new));
    }

    @GetMapping("/posts/favorite/count")
    public ResponseEntity<?> favoriteCount(@CurrentUser UserPrincipal principal) {

        return ResponseEntity.ok(postService.favoriteCount(principal.getId()));
    }

    @GetMapping("/posts/foods/{id}")
    public ResponseEntity<?> getPostsByFoodId(@PageableDefault Pageable pageable, @PathVariable("id") Long foodId) {

        Page<Post> posts = postService.getPostsByFoodId(foodId, pageable);

        return ResponseEntity.ok(posts);
    }

    @GetMapping("/posts/random")
    public ResponseEntity<?> getPostsRandom() {

        List<Post> posts = postService.getRandomPostList();

        return ResponseEntity.ok(posts.stream().map(PostResponseDtoV1::new));
    }

    @PostMapping("/posts/{id}/favorite")
    public ResponseEntity<?> favorite(@CurrentUser UserPrincipal principal, @PathVariable("id") Long postId) {
        postService.favorite(postId, principal.getId());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/posts/{id}/favorite")
    public ResponseEntity<?> unFavorite(@CurrentUser UserPrincipal principal, @PathVariable("id") Long postId) {

        postService.unFavorite(postId, principal.getId());

        return ResponseEntity.ok().build();
    }


    @GetMapping("/posts/liked")
    public ResponseEntity<?> getPostsLiked(@CurrentUser UserPrincipal userPrincipal, @PageableDefault Pageable pageable) {

        Page<Post> posts = postService.getPostsLiked(userPrincipal.getId(), pageable);

        return ResponseEntity.ok(posts.map(PostResponseDtoV1::new));
    }

    @GetMapping("/posts/liked/count")
    public ResponseEntity<?> likeCount(@CurrentUser UserPrincipal principal) {

        return ResponseEntity.ok(postService.likeCount(principal.getId()));
    }


    @PostMapping("/posts/{id}/like")
    public ResponseEntity<?> like(@CurrentUser UserPrincipal principal, @PathVariable("id") Long postId) {

        postService.like(postId, principal.getId());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/posts/{id}/like")
    public ResponseEntity<?> dislike(@CurrentUser UserPrincipal principal, @PathVariable("id") Long postId) {

        postService.dislike(postId, principal.getId());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/posts")
    public ResponseEntity<?> createPost(@CurrentUser UserPrincipal principal, MultipartFile file, @ModelAttribute @Valid PostCreateRequest requestDto) {
        PostCreateCommand postCreateCommand = requestDto.toCommand(principal.getId(), file);

        Post post = postService.createPost(postCreateCommand);

        return ResponseEntity.ok(new PostResponseDtoV1(post));
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
