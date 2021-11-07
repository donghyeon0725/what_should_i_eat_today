package today.what_should_i_eat_today.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import today.what_should_i_eat_today.domain.member.dto.MemberResponseDto;
import today.what_should_i_eat_today.domain.post.dto.PostResponseDtoV1;
import today.what_should_i_eat_today.domain.review.entity.Review;
import today.what_should_i_eat_today.domain.review.entity.ReviewStatus;
import today.what_should_i_eat_today.domain.review.entity.ReviewType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDto {
    private Long id;

    private MemberResponseDto member;

    private PostResponseDtoV1 post;

    private ReviewResponseDto parent;

    private List<ReviewResponseDto> child = new ArrayList<>();

    private Long childCount;

    private Long total;

    private ReviewType reviewType;

    private String content;

    private ReviewStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public ReviewResponseDto(Review review) {
        this.id = review.getId();
        this.member = new MemberResponseDto(review.getMember());
        this.post = new PostResponseDtoV1(review.getPost());
        this.parent = new ReviewResponseDto();
        this.parent.setId(review.getId());
        this.reviewType = review.getReviewType();
        this.content = review.getContent();
        this.status = review.getStatus();
    }

    public static ReviewResponseDto from(Review review) {
        ReviewResponseDto dto = new ReviewResponseDto();
        dto.id = review.getId();
        dto.content = review.getContent();
        dto.member = new MemberResponseDto(review.getMember());
        dto.reviewType = review.getReviewType();
        dto.status = review.getStatus();
        dto.createdAt = review.getCreatedAt();
        dto.updatedAt = review.getUpdatedAt();
        dto.childCount = review.getChildCount();
        return dto;
    }


    public static ReviewResponseDto childFrom(Review review) {
        ReviewResponseDto dto = new ReviewResponseDto();
        dto.id = review.getId();
        dto.content = review.getContent();
        dto.member = new MemberResponseDto(review.getMember());
        dto.reviewType = review.getReviewType();
        dto.status = review.getStatus();
        dto.createdAt = review.getCreatedAt();
        dto.updatedAt = review.getUpdatedAt();
        dto.parent = ReviewResponseDto.from(review.getParent());
        return dto;
    }
}
