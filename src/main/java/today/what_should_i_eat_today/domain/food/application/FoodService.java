package today.what_should_i_eat_today.domain.food.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.food.dao.FoodRepository;
import today.what_should_i_eat_today.domain.food.entity.Food;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodService {

    private final FoodRepository foodRepository;

    public Page<Food> getFoodListByCategory(Long categoryId, Pageable pageable) {
        return foodRepository.findFoodsByCategory(categoryId, pageable);
    }

    public List<Food> getRandomFood(Integer count) {
        // todo : 2021.08.31 나중에 문제 있음 (음식 사진, tag, country)
        return foodRepository.findFoodsRand(count);
    }
}
