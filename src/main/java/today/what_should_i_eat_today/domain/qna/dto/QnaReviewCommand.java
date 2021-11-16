package today.what_should_i_eat_today.domain.qna.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;

@EqualsAndHashCode(callSuper = true)
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
