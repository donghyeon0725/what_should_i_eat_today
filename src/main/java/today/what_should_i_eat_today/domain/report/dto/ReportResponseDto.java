package today.what_should_i_eat_today.domain.report.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import today.what_should_i_eat_today.domain.member.dto.MemberResponseDto;
import today.what_should_i_eat_today.domain.post.dto.PostResponseDtoV1;
import today.what_should_i_eat_today.domain.report.entity.Report;
import today.what_should_i_eat_today.domain.report.entity.ReportStatus;
import today.what_should_i_eat_today.domain.report.entity.ReportType;
import today.what_should_i_eat_today.domain.review.dto.ReviewDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponseDto {

    private Long id;

    private MemberResponseDto member;

    private MemberResponseDto reportedMember;

    private ReviewDto review;

    private PostResponseDtoV1 post;

    private ReportType type;

    private String title;

    private String content;

    private ReportStatus status;


    public ReportResponseDto(Report report) {
        this.id = report.getId();
        this.member = new MemberResponseDto(report.getMember());
        this.type = report.getType();
        this.title = report.getTitle();
        this.content = report.getContent();
        this.status = report.getStatus();

        if (ReportType.POST.equals(report.getType())) {
        }

        if (ReportType.PROFILE.equals(report.getType())) {
        }

        if (ReportType.REVIEW.equals(report.getType())) {
        }
    }
}
