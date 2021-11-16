package today.what_should_i_eat_today.domain.category.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CategoryUserCommand extends CategoryCommand {

    @Builder
    public CategoryUserCommand(Long id, String name, String description, Boolean visible, Long adminId) {
        super(id, name, description, visible, adminId);
    }
}
