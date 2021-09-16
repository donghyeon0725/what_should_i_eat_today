package today.what_should_i_eat_today.domain.report.dto;

import lombok.Data;
import today.what_should_i_eat_today.domain.report.entity.ReportStatus;
import today.what_should_i_eat_today.domain.report.entity.ReportType;

@Data
public class ReportRequestDto {
    private Long id;

    private ReportType type;

    private String title;

    private String content;

    private ReportStatus status;

    private Long postId;

    private Long reportedMemberId;

    private Long reviewId;

    public ReportCommand getCommand() {
        if (this.postId != null) {
            this.type = ReportType.POST;
            return PostReportCommand.builder().id(id).title(title).content(content).postId(postId).build();
        }

        if (this.reportedMemberId != null) {
            this.type = ReportType.PROFILE;
            return ProfileReportCommand.builder().id(id).title(title).content(content).reportedMemberId(reportedMemberId).build();
        }

        if (this.reviewId != null) {
            this.type = ReportType.REVIEW;
            return ReviewReportCommand.builder().id(id).title(title).content(content).reviewId(reviewId).build();
        }


        return null;
    }

}
