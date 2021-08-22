package today.what_should_i_eat_today.domain.post.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import today.what_should_i_eat_today.domain.post.application.PostService;
import today.what_should_i_eat_today.global.security.CurrentUser;
import today.what_should_i_eat_today.global.security.UserPrincipal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostApi {

    private final PostService postService;

    @PostMapping("/posts/{id}/like")
    public ResponseEntity<?> updateLike(@CurrentUser UserPrincipal principal, @PathVariable Long postId) {

        Long userId = principal.getId();

        postService.updateLike(postId, userId);

        return null;
    }
}
