package today.what_should_i_eat_today.domain.qna.dto;

import lombok.Data;
import today.what_should_i_eat_today.domain.member.dto.MemberResponseDto;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.qna.entity.Qna;
import today.what_should_i_eat_today.domain.qna.entity.QnaReview;
import today.what_should_i_eat_today.domain.qna.entity.QnaStatus;
import today.what_should_i_eat_today.domain.qna.entity.QnaType;

import javax.persistence.*;

@Data
public class QnaResponseDto {

    private Long id;

    private QnaReviewResponseDto qnaReview;

    private MemberResponseDto member;

    private QnaType type;

    private String title;

    private String content;

    private QnaStatus status;


    public QnaResponseDto(Qna qna) {
        this.id = qna.getId();
        this.qnaReview = new QnaReviewResponseDto(qna.getQnaReview());
        this.member = new MemberResponseDto(qna.getMember());
        this.type = qna.getType();
        this.title = qna.getTitle();
        this.content = qna.getContent();
        this.status = qna.getStatus();
    }
}
