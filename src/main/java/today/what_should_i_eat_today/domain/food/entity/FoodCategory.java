package today.what_should_i_eat_today.domain.food.entity;


import lombok.*;
import today.what_should_i_eat_today.domain.category.entity.Category;
import today.what_should_i_eat_today.global.common.entity.BaseEntity;

import javax.persistence.*;

@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Food food;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
}
