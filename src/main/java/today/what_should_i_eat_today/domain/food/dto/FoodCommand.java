package today.what_should_i_eat_today.domain.food.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import today.what_should_i_eat_today.domain.country.entity.Country;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodCommand {
    private Long id;

    private String name;

    private Country country;

    public FoodCommand(Long id, String name, Country country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    private List<Long> categoryIds;

    private List<Long> tagIds;
}
