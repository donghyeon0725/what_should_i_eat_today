package today.what_should_i_eat_today.domain.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryUserCommand extends CategoryCommand {

    @Builder
    public CategoryUserCommand(Long id, String name, String description, Boolean visible) {
        super(id, name, description, visible);
    }
}
