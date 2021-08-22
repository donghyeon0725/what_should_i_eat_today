package today.what_should_i_eat_today.domain.food.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.category.dao.CategoryRepository;
import today.what_should_i_eat_today.domain.category.entity.Category;
import today.what_should_i_eat_today.domain.food.dao.FoodRepository;
import today.what_should_i_eat_today.domain.food.entity.Food;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodService {

    private final FoodRepository foodRepository;

    public Page<Food> getFoodListByCategory(Long categoryId, Pageable pageable) {
        return foodRepository.findFoodsByCategory(categoryId, pageable);
    }
}
