package today.what_should_i_eat_today.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WriteReviewRequestDto {

    private String content;

    public ReviewCommand toCommand(Long postId) {
        return ReviewCommand.builder()
                .postId(postId)
                .content(content)
                .build();
    }

    public ReviewCommand toCommand(Long reviewId, Long postId) {
        return ReviewCommand.builder()
                .parentId(reviewId)
                .postId(postId)
                .content(content)
                .build();
    }

    public ReviewCommand toCommandAsReviewsId(Long reviewId, Long writerMemberId) {
        return ReviewCommand.builder()
                .id(reviewId)
                .content(content)
                .memberId(writerMemberId)
                .build();
    }
}
