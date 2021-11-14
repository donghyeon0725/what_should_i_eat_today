package today.what_should_i_eat_today.domain.report.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import today.what_should_i_eat_today.domain.report.entity.ReportType;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.InvalidStatusException;

@Data
@Getter
public class ProfileReportCommand extends ReportCommand{
    private Long reportedMemberId;

    @Builder
    public ProfileReportCommand(Long id, String title, String content, Long reportedMemberId) {
        super(id, ReportType.PROFILE, title, content);
        this.reportedMemberId = reportedMemberId;
    }

    public void validate() {
        if (this.reportedMemberId == null)
            throw new InvalidStatusException(ErrorCode.INVALID_INPUT_VALUE);

        if (super.getTitle() == null)
            throw new InvalidStatusException(ErrorCode.INVALID_INPUT_VALUE);
    }
}
