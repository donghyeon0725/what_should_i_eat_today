package today.what_should_i_eat_today.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.InvalidStatusException;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewCommand {

    private Long id;

    private Long memberId;

    private Long postId;

    private Long parentId;

    private String content;

    public void validate() {
        if (this.content == null)
            throw new InvalidStatusException(ErrorCode.INVALID_INPUT_VALUE);

        if (this.content.length() > 3000)
            throw new InvalidStatusException(ErrorCode.INVALID_INPUT_VALUE);

        if (memberId == null)
            throw new InvalidStatusException(ErrorCode.INVALID_INPUT_VALUE);

        if (postId == null)
            throw new InvalidStatusException(ErrorCode.INVALID_INPUT_VALUE);
    }

    public void createValidate() {
        validate();
    }

    public void replyValidate() {
        validate();

        if (parentId == null)
            throw new InvalidStatusException(ErrorCode.INVALID_INPUT_VALUE);
    }

    public void updateValidate() {
        if (this.content == null)
            throw new InvalidStatusException(ErrorCode.INVALID_INPUT_VALUE);

        if (this.content.length() > 3000)
            throw new InvalidStatusException(ErrorCode.INVALID_INPUT_VALUE);
    }
}
