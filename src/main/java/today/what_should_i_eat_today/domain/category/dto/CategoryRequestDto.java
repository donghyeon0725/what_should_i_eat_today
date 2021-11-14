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

    public CategoryAdminCommand toCommand(Long adminId) {
        return CategoryAdminCommand.builder()
                .name(name)
                .adminId(adminId)
                .description(description)
                .visible(visible)
                .build();
    }

    public CategoryAdminCommand toCommand(Long adminId, Long categoryId) {
        return CategoryAdminCommand.builder()
                .id(categoryId)
                .name(name)
                .adminId(adminId)
                .description(description)
                .visible(visible)
                .build();
    }
}
