package today.what_should_i_eat_today.domain.review.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import today.what_should_i_eat_today.domain.review.application.ReviewService;
import today.what_should_i_eat_today.domain.review.dto.ReviewCommand;
import today.what_should_i_eat_today.domain.review.dto.ReviewOfPostResponseDto;
import today.what_should_i_eat_today.domain.review.dto.ReviewResponseDto;
import today.what_should_i_eat_today.domain.review.dto.WriteReviewRequestDto;
import today.what_should_i_eat_today.domain.review.entity.Review;
import today.what_should_i_eat_today.global.security.CurrentUser;
import today.what_should_i_eat_today.global.security.UserPrincipal;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReviewApi {

    private final ReviewService reviewService;

    @GetMapping("/reviews/posts/{postId}")
    public ResponseEntity<?> getReviewsForPost(
            @PathVariable("postId") Long postId,
            @PageableDefault @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {

        ReviewCommand command = ReviewCommand.builder()
                .postId(postId)
                .build();

        Page<Review> reviewPage = reviewService.getReviewList(command, pageable);

        Long totalCountReviewsOfPost = reviewService.getTotalCountReviewsOfPost(postId);

        return ResponseEntity.ok(ReviewOfPostResponseDto.from(reviewPage, totalCountReviewsOfPost));
    }

    @GetMapping("/reviews/{reviewId}/posts/{postId}")
    public ResponseEntity<?> getReviewsForReview(
            @PathVariable("reviewId") Long reviewId,
            @PathVariable("postId") Long postId
    ) {
        List<Review> reviews = reviewService.getReviewsForReview(postId, reviewId);

        return ResponseEntity.ok(reviews.stream().map(ReviewResponseDto::childFrom));
    }

    @PostMapping("/reviews/posts/{id}")
    @Secured("ROLE_USER")
    public ResponseEntity<?> writeReviewForPost(@PathVariable("id") Long postId, @RequestBody WriteReviewRequestDto dto) {
        reviewService.createReview(dto.toCommand(postId));

        return ResponseEntity.ok("OK");
    }

    @PostMapping("/reviews/{reviewId}/posts/{postId}")
    @Secured("ROLE_USER")
    public ResponseEntity<?> writeReviewForReview(
            @PathVariable("reviewId") Long reviewId,
            @PathVariable("postId") Long postId,
            @RequestBody WriteReviewRequestDto dto
    ) {

        reviewService.replyReview(dto.toCommand(reviewId, postId));

        return ResponseEntity.ok("OK");
    }

    @PutMapping("/reviews/{reviewId}")
    @Secured("ROLE_USER")
    public ResponseEntity<?> updateReview(
            @CurrentUser UserPrincipal principal,
            @PathVariable("reviewId") Long reviewId,
            @RequestBody WriteReviewRequestDto dto
    ) {

        reviewService.updateReview(dto.toCommandAsReviewsId(reviewId, principal.getId()));

        return ResponseEntity.ok("OK");
    }

    @DeleteMapping("/reviews/{reviewId}")
    @Secured("ROLE_USER")
    public ResponseEntity<?> updateReview(
            @CurrentUser UserPrincipal principal,
            @PathVariable("reviewId") Long reviewId
    ) {

        reviewService.deleteReview(reviewId, principal.getId());

        return ResponseEntity.ok("OK");
    }


}
