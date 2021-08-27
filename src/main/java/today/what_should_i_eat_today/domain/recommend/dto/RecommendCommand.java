package today.what_should_i_eat_today.domain.recommend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import today.what_should_i_eat_today.domain.recommend.entity.RecommendType;
import today.what_should_i_eat_today.domain.review.entity.ReviewType;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.InvalidStatusException;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecommendCommand {
    private Long recommendId;

    private Long reviewId;

    private RecommendType type;

    public void createValidate() {
        if (this.type == null)
            throw new InvalidStatusException(ErrorCode.INVALID_INPUT_VALUE);

        if (this.reviewId == null)
            throw new InvalidStatusException(ErrorCode.INVALID_INPUT_VALUE);
    }

    public void cancelValidate() {
        if (this.recommendId == null)
            throw new InvalidStatusException(ErrorCode.INVALID_INPUT_VALUE);
    }
}
