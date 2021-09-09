package today.what_should_i_eat_today.domain.food.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import today.what_should_i_eat_today.domain.country.entity.Country;

@Data
@NoArgsConstructor
public class FoodMemberCommand extends FoodCommand {
    @Builder
    public FoodMemberCommand(Long id, String name, Country country) {
        super(id, name, country);
    }
}
