package today.what_should_i_eat_today.domain.food.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.category.dao.FoodCategoryRepository;
import today.what_should_i_eat_today.domain.category.entity.FoodCategory;
import today.what_should_i_eat_today.domain.food.entity.Food;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodCategoryService {

    private final FoodCategoryRepository foodCategoryRepository;

    public Page<Food> getFoods(Long categoryId, Pageable pageable) {

        // todo : 2021.08.30 코드 개선이 필요해보임
        // 카테고리에 해당하는 Food 을 조회하되, pageable 을 적용해야한다
        Page<FoodCategory> foodCategories = foodCategoryRepository.findAllByCategoryId(categoryId, pageable);

        List<Food> foods = foodCategories.getContent().stream().map(FoodCategory::getFood).collect(Collectors.toList());

        for (Food food : foods) {
            food.getName();
        }

        return new PageImpl<>(foods, pageable, foodCategories.getTotalPages());
    }
}
