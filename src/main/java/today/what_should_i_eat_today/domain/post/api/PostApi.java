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
import today.what_should_i_eat_today.domain.post.dto.PostResponseDto;
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

        return ResponseEntity.ok(post);
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getPosts(@PageableDefault Pageable pageable) {

        Page<Post> posts = postService.getPosts(pageable);

        return ResponseEntity.ok(posts);
    }

    @GetMapping("/posts/foods/{id}")
    public ResponseEntity<?> getPostsByFoodId(@PageableDefault Pageable pageable, @PathVariable("id") Long foodId) {

        PostResponseDto postResponseDto = postService.getPostsByFoodId(foodId, pageable);

        return ResponseEntity.ok(postResponseDto);
    }

    @PostMapping("/posts/{id}/like")
    public ResponseEntity<?> updateLike(@CurrentUser UserPrincipal principal, @PathVariable("id") Long postId) {

        Long userId = principal.getId();

        return ResponseEntity.ok(postService.updateLike(postId, userId));
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
