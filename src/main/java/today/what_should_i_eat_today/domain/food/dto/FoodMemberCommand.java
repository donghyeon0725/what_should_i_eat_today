package today.what_should_i_eat_today.domain.food.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import today.what_should_i_eat_today.domain.country.entity.Country;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class FoodMemberCommand extends FoodCommand {
    @Builder
    public FoodMemberCommand(Long id, String name, Country country) {
        super(id, name, country);
    }
}
