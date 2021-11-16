package today.what_should_i_eat_today.domain.category.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.InvalidStatusException;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CategoryAdminCommand extends CategoryCommand {

    @Builder
    public CategoryAdminCommand(Long id, String name, String description, Boolean visible, Long adminId) {
        super(id, name, description, visible, adminId);
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
