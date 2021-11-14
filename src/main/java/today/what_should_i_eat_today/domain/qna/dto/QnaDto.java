package today.what_should_i_eat_today.domain.qna.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import today.what_should_i_eat_today.domain.qna.entity.QnaStatus;
import today.what_should_i_eat_today.domain.qna.entity.QnaType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QnaDto {
    private Long qnaId;

    private QnaType type;

    private QnaStatus qnaStatus;

    private String title;

    private String qnaContent;

    private String qnaReviewContent;
}
