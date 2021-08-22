package today.what_should_i_eat_today.domain.category.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import today.what_should_i_eat_today.domain.category.entity.FoodCategory;

public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long> {
}
