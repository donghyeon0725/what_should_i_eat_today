package today.what_should_i_eat_today.domain.tag.dao;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import today.what_should_i_eat_today.domain.tag.entity.QTag;
import today.what_should_i_eat_today.domain.tag.entity.Tag;

import static today.what_should_i_eat_today.domain.tag.entity.QTag.tag;


@RequiredArgsConstructor
public class TagDslRepositoryImpl implements TagDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Tag> findByNameContains(String name, Pageable pageable) {

        QueryResults<Tag> results = jpaQueryFactory
                .selectFrom(tag)
                .where(nameContains(name))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    private BooleanExpression nameContains(String name) {
        return name != null ? tag.name.contains(name) : null;
    }
}
