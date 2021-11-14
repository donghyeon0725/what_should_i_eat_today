package today.what_should_i_eat_today.domain.food.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import today.what_should_i_eat_today.domain.category.dto.CategoryResponseDto;
import today.what_should_i_eat_today.domain.category.entity.FoodCategory;
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

    // new 로 객체를 생성하는 경우 init value 가 null 이 되어버리는 버그가 있음 (롬복에서 고쳐줘야 하는데 고쳐주지 않음)
    @Builder.Default
    @JsonProperty("foodCategories")
    private List<CategoryResponseDto> categoryResponseDtos = new ArrayList<>();

    @Builder.Default
    @JsonProperty("foodTags")
    private List<TagResponseDto> tagResponseDtos = new ArrayList<>();

    public FoodResponseDto(Food food) {
        categoryResponseDtos = new ArrayList<>();
        tagResponseDtos = new ArrayList<>();
        this.id = food.getId();
        this.name = food.getName();

        for (int i=0; i<food.getFoodTags().size(); i++)
            tagResponseDtos.add(new TagResponseDto(food.getFoodTags().get(i).getTag().getId(), food.getFoodTags().get(i).getTag().getName()));

        for (int i=0; i<food.getFoodCategories().size(); i++)
            categoryResponseDtos.add(new CategoryResponseDto(food.getFoodCategories().get(i).getCategory().getId(), food.getFoodCategories().get(i).getCategory().getName()));
    }


    public void addAllFoodTags(List<FoodTag> foodTags) {
        for (FoodTag foodTag : foodTags) {
            TagResponseDto tagResponseDto = new TagResponseDto(foodTag);
            this.tagResponseDtos.add(tagResponseDto);
        }
    }

    public void addAllFoodCategories(List<FoodCategory> foodCategories) {
        for (FoodCategory foodCategory : foodCategories) {
            CategoryResponseDto tagResponseDto = new CategoryResponseDto(foodCategory.getCategory().getId(), foodCategory.getCategory().getName());
            this.categoryResponseDtos.add(tagResponseDto);
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
