package today.what_should_i_eat_today.domain.qna.dto;

import lombok.Data;
import today.what_should_i_eat_today.domain.admin.dto.AdminResponseDto;
import today.what_should_i_eat_today.domain.qna.entity.QnaReview;

@Data
public class QnaReviewResponseDto {
    private Long qnaReviewId;

    private String content;

    public QnaReviewResponseDto(QnaReview qnaReview) {
        this.qnaReviewId = qnaReview.getId();
        this.content = qnaReview.getContent();
    }
}
