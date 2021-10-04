package today.what_should_i_eat_today.domain.review.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import today.what_should_i_eat_today.domain.member.application.MemberFindService;
import today.what_should_i_eat_today.domain.member.dto.MemberResponseDto;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.review.application.ReviewService;
import today.what_should_i_eat_today.domain.review.dto.ReviewDto;
import today.what_should_i_eat_today.domain.review.dto.ReviewResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReviewApi {

    private final ReviewService reviewService;

    @GetMapping(value = "/reviews/{id}")
    public ResponseEntity<ReviewResponseDto> getPost(@PathVariable Long id) {

        return ResponseEntity.ok(new ReviewResponseDto(reviewService.findById(id)));
    }
}
