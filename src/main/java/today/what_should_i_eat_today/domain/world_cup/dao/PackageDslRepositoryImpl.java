package today.what_should_i_eat_today.domain.world_cup.dao;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import today.what_should_i_eat_today.domain.world_cup.entity.Package;
import static today.what_should_i_eat_today.domain.world_cup.entity.QPackage.package$;

@RequiredArgsConstructor
public class PackageDslRepositoryImpl implements PackageDslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Package> findBySubject(String subject, Pageable pageable) {

        QueryResults<Package> results = queryFactory
                .selectFrom(package$)
                .where(subjectContains(subject))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl(results.getResults(), pageable, results.getTotal());
    }

    private BooleanExpression subjectContains(String subject) {
        return subject != null ? package$.subject.contains(subject) : null;
    }
}
