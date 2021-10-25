package today.what_should_i_eat_today.domain.food.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import today.what_should_i_eat_today.domain.food.entity.Food;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodResponseDtoV1 {
    private Long id;

    private String name;

    private String img;

    private String country;

    public FoodResponseDtoV1(Food food) {
        this.id = food.getId();
        this.name = food.getName();
        this.country = food.getCountry().getName();
    }
}
