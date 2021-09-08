package today.what_should_i_eat_today.domain.food.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import today.what_should_i_eat_today.domain.country.entity.Country;

@Data
@NoArgsConstructor
public class FoodDto extends FoodCommand {

    private Boolean deleted;

    @Builder
    public FoodDto(Long id, String name, Country country, boolean deleted) {
        super(id, name, country);
        this.deleted = deleted;
    }
}
