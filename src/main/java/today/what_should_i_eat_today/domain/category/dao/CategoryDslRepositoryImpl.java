package today.what_should_i_eat_today.domain.category.dao;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import today.what_should_i_eat_today.domain.category.entity.Category;
import today.what_should_i_eat_today.domain.category.entity.QCategory;

import java.util.List;

import static today.what_should_i_eat_today.domain.category.entity.QCategory.category;

@RequiredArgsConstructor
public class CategoryDslRepositoryImpl implements CategoryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Page<Category> findVisibleCategoryByName(String name, Pageable pageable) {

        QueryResults<Category> results = jpaQueryFactory
                .selectFrom(category)
                .where(nameContains(name), category.visible.eq(true))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    @Override
    public Page<Category> findCategoryByName(String name, Pageable pageable) {

        List<Category> results = jpaQueryFactory
                .selectFrom(category)
                .join(category.admin).fetchJoin()
                .where(nameContains(name))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();


        long count = jpaQueryFactory
                .select(category.count())
                .from(category)
                .where(nameContains(name))
                .fetchCount();

        return new PageImpl<>(results, pageable, count);
    }

    private BooleanExpression nameContains(String name) {
        return name != null ? category.name.contains(name) : null;
    }
}
