package today.what_should_i_eat_today.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import today.what_should_i_eat_today.domain.member.dto.MemberResponseDto;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.post.dto.PostResponseDtoV1;
import today.what_should_i_eat_today.domain.post.entity.Post;
import today.what_should_i_eat_today.domain.review.entity.Review;
import today.what_should_i_eat_today.domain.review.entity.ReviewStatus;
import today.what_should_i_eat_today.domain.review.entity.ReviewType;

import javax.persistence.*;
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

    private ReviewType reviewType;

    private String content;

    private ReviewStatus status;

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
}
