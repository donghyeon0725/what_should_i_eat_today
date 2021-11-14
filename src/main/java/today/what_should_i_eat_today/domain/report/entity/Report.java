package today.what_should_i_eat_today.domain.report.entity;

import lombok.*;
import today.what_should_i_eat_today.event.event.PostReportEvent;
import today.what_should_i_eat_today.event.event.ProfileReportEvent;
import today.what_should_i_eat_today.event.event.ReviewReportEvent;
import today.what_should_i_eat_today.event.service.Events;
import today.what_should_i_eat_today.global.common.entity.BaseEntity;
import today.what_should_i_eat_today.domain.review.entity.Review;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.post.entity.Post;

import javax.persistence.*;

@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Report extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member reportedMember;

    @ManyToOne(fetch = FetchType.LAZY)
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @Enumerated(EnumType.STRING)
    private ReportType type;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private ReportStatus status;

    public void approve() {
        this.status = ReportStatus.APPROVED;
    }

    public void notApprove() {
        this.status = ReportStatus.NOT_APPROVED;
    }

    // activity 생성
    public void processApprove(ReportType reportType) {
        approve();

        if (reportType == ReportType.PROFILE)
            Events.raise(new ProfileReportEvent(this));
        else if (reportType == ReportType.POST)
            Events.raise(new PostReportEvent(this));
        else if (reportType == ReportType.REVIEW)
            Events.raise(new ReviewReportEvent(this));
    }

    public void processNotApprove() {
        notApprove();
    }
}
