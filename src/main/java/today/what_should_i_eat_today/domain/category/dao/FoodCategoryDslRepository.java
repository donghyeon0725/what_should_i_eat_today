package today.what_should_i_eat_today.domain.category.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import today.what_should_i_eat_today.domain.food.entity.Food;

public interface FoodCategoryDslRepository {

    Page<Food> findFoodsByCategory(Long categoryId, Pageable pageable);
}
