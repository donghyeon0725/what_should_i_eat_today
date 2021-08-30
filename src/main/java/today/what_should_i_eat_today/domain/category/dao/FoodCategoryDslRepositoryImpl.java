package today.what_should_i_eat_today.domain.category.dao;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import today.what_should_i_eat_today.domain.food.entity.Food;

import static today.what_should_i_eat_today.domain.category.entity.QFoodCategory.foodCategory;

@RequiredArgsConstructor
public class FoodCategoryDslRepositoryImpl implements FoodCategoryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Food> findFoodsByCategory(Long categoryId, Pageable pageable) {

        QueryResults<Food> foodQueryResults = jpaQueryFactory
                .select(foodCategory.food)
                .from(foodCategory)
                .join(foodCategory.food)
                .where(foodCategory.category.id.eq(categoryId))
                .fetchResults();

        return new PageImpl<>(foodQueryResults.getResults(), pageable, foodQueryResults.getTotal());
    }
}
