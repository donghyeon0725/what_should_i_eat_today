package today.what_should_i_eat_today.domain.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import today.what_should_i_eat_today.domain.admin.entity.Admin;
import today.what_should_i_eat_today.domain.category.entity.Category;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryResponseDto {

    private Long id;

    private String name;

    private Admin admin;

    private String description;

    private Boolean visible;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public CategoryResponseDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.admin = category.getAdmin();
        this.description = category.getDescription();
        this.visible = category.getVisible();
        this.createdAt = category.getCreatedAt();
        this.updatedAt = category.getUpdatedAt();
    }

    public CategoryResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
