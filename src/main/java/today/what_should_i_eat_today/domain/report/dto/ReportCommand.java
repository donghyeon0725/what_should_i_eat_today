package today.what_should_i_eat_today.domain.report.dto;

import lombok.*;
import today.what_should_i_eat_today.domain.report.entity.ReportStatus;
import today.what_should_i_eat_today.domain.report.entity.ReportType;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.InvalidStatusException;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportCommand {
    private Long id;

    private ReportType type;

    private String title;

    private String content;

    private ReportStatus status;

    public ReportCommand(Long id, ReportType type, String title, String content) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.content = content;
    }

    public ReportCommand(Long id, ReportStatus status) {
        this.id = id;
        this.status = status;
    }

    public void statusValidate() {
        if (this.status == null)
            throw new InvalidStatusException(ErrorCode.INVALID_INPUT_VALUE);
    }
}
