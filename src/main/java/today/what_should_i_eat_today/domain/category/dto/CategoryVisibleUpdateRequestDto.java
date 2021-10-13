package today.what_should_i_eat_today.domain.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryVisibleUpdateRequestDto {

    @NotBlank
    private Long id;

    @NotNull
    private Boolean visible;

    public CategoryAdminCommand toCommand(Long adminId) {
        return CategoryAdminCommand.builder()
                .id(id)
                .adminId(adminId)
                .visible(visible)
                .build();
    }
}
