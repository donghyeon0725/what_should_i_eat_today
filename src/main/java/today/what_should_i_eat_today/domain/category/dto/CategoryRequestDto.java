package today.what_should_i_eat_today.domain.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequestDto {

    @Length(min = 1, max = 255)
    private String name;

    @Length(min = 2, max = 255)
    private String description;

    @NotBlank
    private Boolean visible;

    public CategoryAdminCommand toCommand() {
        return CategoryAdminCommand.builder()
                .name(name)
                .description(description)
                .visible(visible)
                .build();
    }
}
