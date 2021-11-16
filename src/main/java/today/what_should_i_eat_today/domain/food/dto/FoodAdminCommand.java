package today.what_should_i_eat_today.domain.food.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import today.what_should_i_eat_today.domain.country.entity.Country;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class FoodAdminCommand extends FoodCommand {

    private boolean deleted;

    @Builder
    public FoodAdminCommand(Long id, String name, Country country, boolean deleted) {
        super(id, name, country);
        this.deleted = deleted;
    }

    public void validateForCreateAndUpdate() {
        if (super.getCountry() == null)
            throw new IllegalArgumentException();
    }
}
