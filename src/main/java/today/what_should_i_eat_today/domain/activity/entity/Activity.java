package today.what_should_i_eat_today.domain.activity.entity;

import lombok.*;
import today.what_should_i_eat_today.domain.qna.entity.Qna;
import today.what_should_i_eat_today.domain.report.entity.ReportType;
import today.what_should_i_eat_today.global.common.entity.BaseEntity;
import today.what_should_i_eat_today.domain.review.entity.Review;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.post.entity.Post;
import today.what_should_i_eat_today.domain.report.entity.Report;

import javax.persistence.*;

@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Activity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    private Report report;

    @ManyToOne(fetch = FetchType.LAZY)
    private Qna qna;

    @Enumerated(EnumType.STRING)
    private ActivityType type;

    @Enumerated(EnumType.STRING)
    private ReportType reportType;

    private String details;

    public void validateAuth(ActivityValidator activityValidator) {
        activityValidator.validateAuth(this);
    }
}
