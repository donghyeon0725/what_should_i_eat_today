package today.what_should_i_eat_today.domain.review.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;
import today.what_should_i_eat_today.domain.review.entity.Review;

@Getter
public class ReviewOfPostResponseDto {

    private Long totalCount;

    private Page<ReviewResponseDto> reviewPage;

    public static ReviewOfPostResponseDto from(Page<Review> reviewPage, Long totalCountReviewsOfPost) {
        ReviewOfPostResponseDto dto = new ReviewOfPostResponseDto();
        dto.totalCount = totalCountReviewsOfPost;
        dto.reviewPage = reviewPage.map(ReviewResponseDto::from);
        return dto;
    }
}
