package today.what_should_i_eat_today.domain.report.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import today.what_should_i_eat_today.domain.report.entity.ReportType;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.InvalidStatusException;

@Data
@Getter
public class PostReportCommand extends ReportCommand {
    private Long postId;

    @Builder
    public PostReportCommand(Long id,  String title, String content, Long postId) {
        super(id, ReportType.POST, title, content);
        this.postId = postId;
    }

    public void validate() {
        if (this.postId == null)
            throw new InvalidStatusException(ErrorCode.INVALID_INPUT_VALUE);

        if (super.getTitle() == null)
            throw new InvalidStatusException(ErrorCode.INVALID_INPUT_VALUE);
    }
}
