package today.what_should_i_eat_today.domain.food.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import today.what_should_i_eat_today.domain.food.entity.Food;

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

    public static List<FoodResponseDto> from(List<Food> randomFoods) {
        List<FoodResponseDto> foodResponseDtos = new ArrayList<>();

        for (Food food : randomFoods) {
            FoodResponseDto dto = FoodResponseDto.builder()
                    .id(food.getId())
                    .name(food.getName())
                    .img("") // todo 2021.08.31 기본 이미지 설정해야 함
                    .build();
            foodResponseDtos.add(dto);
        }

        return foodResponseDtos;
    }
}
