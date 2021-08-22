package today.what_should_i_eat_today.domain.category.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.CannotExecuteException;

@Component
@RequiredArgsConstructor
public class CategoryValidator {
    public void validateName(String name) {
        if (!StringUtils.hasText(name))
            throw new CannotExecuteException(ErrorCode.INVALID_INPUT_VALUE);
    }
}
