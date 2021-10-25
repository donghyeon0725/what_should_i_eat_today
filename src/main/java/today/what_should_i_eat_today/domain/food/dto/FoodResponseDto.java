package today.what_should_i_eat_today.domain.food.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import today.what_should_i_eat_today.domain.category.dto.CategoryResponseDto;
import today.what_should_i_eat_today.domain.food.entity.Food;
import today.what_should_i_eat_today.domain.food.entity.FoodTag;
import today.what_should_i_eat_today.domain.tag.dto.TagResponseDto;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodResponseDto {

    private Long id;

    private String name;

    private String img;

    private String country;

    @JsonProperty("foodCategories")
    private List<CategoryResponseDto> categoryResponseDtos = new ArrayList<>();

    @Builder.Default
    @JsonProperty("foodTags")
    private List<TagResponseDto> tagResponseDtos = new ArrayList<>();

    public FoodResponseDto(Food food) {
        this.id = food.getId();
        this.name = food.getName();
        food.getFoodTags().forEach(s -> tagResponseDtos.add(new TagResponseDto(s.getTag().getId(), s.getTag().getName())));
        food.getFoodCategories().forEach(s -> categoryResponseDtos.add(new CategoryResponseDto(s.getCategory().getId(), s.getCategory().getName())));
    }


    public void addAllFoodTags(List<FoodTag> foodTags) {
        for (FoodTag foodTag : foodTags) {
            TagResponseDto tagResponseDto = new TagResponseDto(foodTag);
            this.tagResponseDtos.add(tagResponseDto);
        }
    }

    public static List<FoodResponseDto> from(List<Food> randomFoods) {
        List<FoodResponseDto> foodResponseDtos = new ArrayList<>();

        for (Food food : randomFoods) {
            FoodResponseDto dto = from(food);
            foodResponseDtos.add(dto);
        }

        return foodResponseDtos;
    }

    public static FoodResponseDto from(Food food) {
        return FoodResponseDto.builder()
                .id(food.getId())
                .name(food.getName())
                .country(food.getCountry().getName())
                .img("") // todo 2021.08.31 기본 이미지 설정해야 함
                .build();
    }

    public static FoodResponseDto from(Food food, List<CategoryResponseDto> categoryResponseDtos) {
        return FoodResponseDto.builder()
                .id(food.getId())
                .name(food.getName())
                .country(food.getCountry().getName())
                .img("") // todo 2021.08.31 기본 이미지 설정해야 함
                .categoryResponseDtos(categoryResponseDtos)
                .build();
    }

}
