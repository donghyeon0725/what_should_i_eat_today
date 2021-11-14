package today.what_should_i_eat_today.domain.category.dto;

import lombok.*;
import today.what_should_i_eat_today.domain.admin.entity.Admin;
import today.what_should_i_eat_today.domain.category.entity.Category;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
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


    public static CategoryResponseDto from(Category category) {
        return CategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .admin(category.getAdmin())
                .description(category.getDescription())
                .visible(category.getVisible())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }

}
