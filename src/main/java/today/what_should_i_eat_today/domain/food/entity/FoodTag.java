package today.what_should_i_eat_today.domain.food.entity;

import lombok.*;
import today.what_should_i_eat_today.domain.tag.entity.Tag;

import javax.persistence.*;

@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Food food;

    @ManyToOne(fetch = FetchType.LAZY)
    private Tag tag;

    public void mappingToFood(Food food) {
        this.food = food;
    }
}
