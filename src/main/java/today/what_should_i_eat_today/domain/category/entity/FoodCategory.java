package today.what_should_i_eat_today.domain.category.entity;


import lombok.*;
import today.what_should_i_eat_today.domain.category.entity.Category;
import today.what_should_i_eat_today.domain.food.entity.Food;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.global.common.entity.BaseEntity;

import javax.persistence.*;
import java.util.Objects;

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

    public void addCategoryMapping(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof FoodCategory)) return false;
        FoodCategory that = (FoodCategory) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
