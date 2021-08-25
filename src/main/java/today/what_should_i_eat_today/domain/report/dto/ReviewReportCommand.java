package today.what_should_i_eat_today.domain.report.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import today.what_should_i_eat_today.domain.report.entity.ReportStatus;
import today.what_should_i_eat_today.domain.report.entity.ReportType;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.InvalidStatusException;

@Data
@Getter
public class ReviewReportCommand extends ReportCommand {

    private Long reviewId;

    @Builder
    public ReviewReportCommand(Long id, String title, String content, Long reviewId) {
        super(id, ReportType.REVIEW, title, content);
        this.reviewId = reviewId;
    }

    public void validate() {
        if (this.reviewId == null)
            throw new InvalidStatusException(ErrorCode.INVALID_INPUT_VALUE);

        if (super.getTitle() == null)
            throw new InvalidStatusException(ErrorCode.INVALID_INPUT_VALUE);
    }
}
