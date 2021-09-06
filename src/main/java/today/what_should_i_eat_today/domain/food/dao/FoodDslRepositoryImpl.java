package today.what_should_i_eat_today.domain.food.dao;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import today.what_should_i_eat_today.domain.food.entity.Food;
import today.what_should_i_eat_today.domain.food.entity.QFoodTag;

import java.util.List;

import static today.what_should_i_eat_today.domain.food.entity.QFood.food;
import static today.what_should_i_eat_today.domain.food.entity.QFoodTag.foodTag;

@RequiredArgsConstructor
public class FoodDslRepositoryImpl implements FoodDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Food> findByTags(List<Long> tagIds) {
//
//        final List<FoodTag> fetch = jpaQueryFactory
//                .selectFrom(foodTag)
//                .join(foodTag.tag)
//                .where(foodTag.tag.id.in(tagIds))
//                .fetch();

//        final List<Food> fetch = jpaQueryFactory
//                .selectFrom(food)
//                .join(foodTag).on(foodTag.food.id.eq(food.id))
//                .join(tag).on(foodTag.tag.id.eq(tag.id))
//                .where(foodTag.tag.id.in(tagIds))
//                .fetch();

        return jpaQueryFactory
                .selectFrom(food)
                .where(
                        food.id.in(
                                JPAExpressions
                                        .select(foodTag.food.id)
                                        .from(foodTag)
                                        .where(foodTag.tag.id.in(tagIds))
                                        .groupBy(foodTag.food.id)
                                        .having(foodTag.food.count().goe(tagIds.size()))
                        )
                )
                .fetch();

        // foodTag 와 tag 를 join 하고, food id 로 grouping 했을 때, count 가 일정 이상 나오는 food

//        List<Food> foods = new ArrayList<>();
//        fetch.forEach(s-> foods.add(s.getFood()));
//        return fetch;
    }
}
