package today.what_should_i_eat_today.domain.qna.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import today.what_should_i_eat_today.domain.qna.entity.QnaStatus;
import today.what_should_i_eat_today.domain.qna.entity.QnaType;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.InvalidStatusException;

@Data
@NoArgsConstructor
public class QnaCommand {
    private Long qnaId;

    private QnaType type;

    private QnaStatus qnaStatus;

    private String title;

    private String qnaContent;

    @Builder
    public QnaCommand(Long qnaId, QnaType type, QnaStatus qnaStatus, String title, String qnaContent) {
        this.qnaId = qnaId;
        this.type = type;
        this.qnaStatus = qnaStatus;
        this.title = title;
        this.qnaContent = qnaContent;
    }

    public QnaCommand(Long qnaId) {
        this.qnaId = qnaId;
    }

    public void createValidate() {

        if (this.title.length() < 2)
            throw new InvalidStatusException(ErrorCode.INVALID_INPUT_VALUE);

        if (this.title.length() > 255)
            throw new InvalidStatusException(ErrorCode.INVALID_INPUT_VALUE);

        if (!StringUtils.hasText(this.qnaContent))
            throw new InvalidStatusException(ErrorCode.INVALID_INPUT_VALUE);
    }
}
