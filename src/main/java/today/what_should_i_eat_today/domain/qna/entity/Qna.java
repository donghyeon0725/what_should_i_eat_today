package today.what_should_i_eat_today.domain.qna.entity;


import lombok.*;
import today.what_should_i_eat_today.global.common.entity.BaseEntity;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.model.Status;

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

}
