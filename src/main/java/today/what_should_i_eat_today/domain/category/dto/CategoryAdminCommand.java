package today.what_should_i_eat_today.domain.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.InvalidStatusException;

@Data
@NoArgsConstructor
public class CategoryAdminCommand extends CategoryCommand {

    @Builder
    public CategoryAdminCommand(Long id, String name, String description, Boolean visible) {
        super(id, name, description, visible);
    }

    public void createValidate() {
        if (!StringUtils.hasText(getName()))
            throw new InvalidStatusException(ErrorCode.INVALID_INPUT_VALUE);
    }

    public void updateValidate() {
        if (!StringUtils.hasText(getName()))
            throw new InvalidStatusException(ErrorCode.INVALID_INPUT_VALUE);
    }
}
