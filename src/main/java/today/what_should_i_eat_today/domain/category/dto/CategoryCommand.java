package today.what_should_i_eat_today.domain.category.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class CategoryCommand {

    private Long id;

    private String name;

    private String description;

    private Boolean visible;

    private Long adminId;
}
