package today.what_should_i_eat_today.domain.qna.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import today.what_should_i_eat_today.domain.admin.entity.Admin;
import today.what_should_i_eat_today.domain.qna.entity.Qna;
import today.what_should_i_eat_today.domain.qna.entity.QnaStatus;
import today.what_should_i_eat_today.domain.qna.entity.QnaType;

import javax.persistence.Lob;

@Data
@NoArgsConstructor
public class QnaReviewCommand extends QnaCommand {

    @Lob
    private String qnaReviewContent;

    private Long qnaReviewId;

    public QnaReviewCommand(Long qnaId, String qnaReviewContent) {
        super(qnaId);
        this.qnaReviewContent = qnaReviewContent;
    }
}
