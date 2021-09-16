package today.what_should_i_eat_today.domain.food.dao;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import today.what_should_i_eat_today.domain.country.entity.Country;
import today.what_should_i_eat_today.domain.food.dto.FoodDto;
import today.what_should_i_eat_today.domain.food.entity.Food;

import java.util.List;

import static today.what_should_i_eat_today.domain.category.entity.QFoodCategory.foodCategory;
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
                                // 태그 조건을 모두 가진 음식 하나만 선택
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

    @Override
    public Page<Food> findBySearch(FoodDto foodDto, Pageable pageable) {
        // 카테고리 검색
        // 국가 검색
        // 음식명 검색
        // 음식 승인 여부 검색

        final QueryResults<Food> results = jpaQueryFactory
                .selectFrom(food)
                .where(
                        containsName(foodDto.getName()),
                        eqCountry(foodDto.getCountry()),
                        eqDeleted(foodDto.getDeleted()),
                        intoAllCategory(foodDto.getCategoryIds())

                )
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchResults();


        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    private BooleanExpression intoAllCategory(List<Long> categoryIds) {
        return categoryIds == null || categoryIds.size() == 0 ? null : food.id.in(
                JPAExpressions
                        .select(foodCategory.food.id)
                        .from(foodCategory)
                        .where(foodCategory.category.id.in(categoryIds))
                        .groupBy(foodCategory.food.id)
                        .having(foodCategory.food.count().goe(categoryIds.size()))
        );
    }

    private BooleanExpression eqDeleted(Boolean deleted) {
        return deleted != null ? food.deleted.eq(deleted) : null;
    }

    private BooleanExpression eqCountry(Country country) {
        return country != null ? food.country.eq(country) : null;
    }

    private BooleanExpression containsName(String name) {
        return name != null ? food.name.contains(name) : null;
    }
}
