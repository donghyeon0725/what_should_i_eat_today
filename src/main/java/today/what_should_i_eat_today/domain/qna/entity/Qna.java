package today.what_should_i_eat_today.domain.qna.entity;


import lombok.*;
import today.what_should_i_eat_today.event.event.QnaReviewEvent;
import today.what_should_i_eat_today.event.service.Events;
import today.what_should_i_eat_today.global.common.entity.BaseEntity;
import today.what_should_i_eat_today.domain.member.entity.Member;

import javax.persistence.*;

@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Qna extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "qna_review_id")
    private QnaReview qnaReview;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Enumerated(EnumType.STRING)
    private QnaType type;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private QnaStatus status;

    public void answerQnaReview(QnaReview qnaReview) {
        this.qnaReview = qnaReview;
        qnaReview.addedToQna(this);
    }

    public void notProcess() {
        this.status = QnaStatus.NOT_PROCESSED;
    }

    public void process() {
        this.status = QnaStatus.PROCESSED;
    }

    public void updateQna(String title, String content, QnaValidator qnaValidator) {
        qnaValidator.updateValidate(title, content, this.status, this.member);
        this.title = title;
        this.content = content;
    }

    public void addQnaReview(QnaReview qnaReview, QnaValidator qnaValidator) {
        qnaValidator.qnaReviewAddValidate(this, qnaReview);
        qnaReview.addedToQna(this);
        this.qnaReview = qnaReview;
        process();

        Events.raise(new QnaReviewEvent(this));
    }

    public void changeStatus(QnaStatus qnaStatus, QnaValidator qnaValidator) {
        qnaValidator.qnaStatusValidate(qnaStatus);
        this.status = qnaStatus;
    }
}
