package today.what_should_i_eat_today.domain.food.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import today.what_should_i_eat_today.domain.category.entity.Category;
import today.what_should_i_eat_today.domain.category.entity.FoodCategory;
import today.what_should_i_eat_today.domain.food.entity.Food;
import today.what_should_i_eat_today.domain.tag.dto.TagResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FoodWithTagsAndCountryResponseDto {

    private Long id;
    private String name;
    private String country;
    private List<TagResponseDto> tags;

    private boolean check = false;


    public FoodWithTagsAndCountryResponseDto(Food food, Long categoryId) {
        this.id = food.getId();
        this.name = food.getName();
        this.country = food.getCountry().getName();
        this.tags = TagResponseDto.from(food.getFoodTags());
        this.check = food.getFoodCategories().stream().map(FoodCategory::getCategory).map(Category::getId).collect(Collectors.toList()).contains(categoryId);
    }
}
