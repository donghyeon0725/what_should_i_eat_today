package today.what_should_i_eat_today.domain.review.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.CannotExecuteException;

@Component
@RequiredArgsConstructor
public class ReviewValidator {

    public void writerValidate(String content) {
        if (!StringUtils.hasText(content))
            throw new CannotExecuteException(ErrorCode.INVALID_INPUT_VALUE);
    }

    public void childAddValidate(Review root) {
        if (root.getParent() != null)
            throw new CannotExecuteException(ErrorCode.INVALID_INPUT_VALUE);
    }

    public void createReviewValidate(Review parent) {
        if (parent != null)
            throw new CannotExecuteException(ErrorCode.INVALID_INPUT_VALUE);
    }

    public void createReplyValidate(Review parent) {
        if (parent == null)
            throw new CannotExecuteException(ErrorCode.INVALID_INPUT_VALUE);
    }

    public void writerValidate(Long reviewWriteMemberId, Long authMemberId) {
        if (!reviewWriteMemberId.equals(authMemberId))
            throw new CannotExecuteException(ErrorCode.INVALID_INPUT_VALUE);
    }
}
