package today.what_should_i_eat_today.domain.post.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import today.what_should_i_eat_today.domain.activity.dto.PostCreateCommand;
import today.what_should_i_eat_today.domain.activity.dto.PostRequest;
import today.what_should_i_eat_today.domain.post.application.PostService;
import today.what_should_i_eat_today.domain.post.entity.Post;
import today.what_should_i_eat_today.global.security.CurrentUser;
import today.what_should_i_eat_today.global.security.UserPrincipal;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostApi {

    private final PostService postService;

    @PostMapping("/posts/{id}/like")
    public ResponseEntity<?> updateLike(@CurrentUser UserPrincipal principal, @PathVariable("id") Long postId) {

        Long userId = principal.getId();

        return ResponseEntity.ok(postService.updateLike(postId, userId));
    }

    @PostMapping("/posts")
    public ResponseEntity<?> createPost(@CurrentUser UserPrincipal principal, @ModelAttribute @Valid PostRequest requestDto) {

        PostCreateCommand postCreateCommand = requestDto.toCommand(principal.getId());

        Post post = postService.createPost(postCreateCommand);

        return ResponseEntity.ok(post);
    }
}
