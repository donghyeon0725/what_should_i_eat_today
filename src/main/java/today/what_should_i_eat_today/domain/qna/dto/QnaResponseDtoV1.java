package today.what_should_i_eat_today.domain.qna.dto;

import lombok.Data;
import today.what_should_i_eat_today.domain.member.dto.MemberResponseDto;
import today.what_should_i_eat_today.domain.qna.entity.Qna;
import today.what_should_i_eat_today.domain.qna.entity.QnaStatus;
import today.what_should_i_eat_today.domain.qna.entity.QnaType;

@Data
public class QnaResponseDtoV1 {

    private Long id;

    private QnaType type;

    private String title;

    private String content;

    private QnaStatus status;


    public QnaResponseDtoV1(Qna qna) {
        this.id = qna.getId();
        this.type = qna.getType();
        this.title = qna.getTitle();
        this.content = qna.getContent();
        this.status = qna.getStatus();
    }
}
