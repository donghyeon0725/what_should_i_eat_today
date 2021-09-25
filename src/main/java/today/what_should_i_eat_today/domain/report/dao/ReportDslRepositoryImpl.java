package today.what_should_i_eat_today.domain.report.dao;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import today.what_should_i_eat_today.domain.report.entity.QReport;
import today.what_should_i_eat_today.domain.report.entity.Report;
import today.what_should_i_eat_today.domain.report.entity.ReportStatus;

import static today.what_should_i_eat_today.domain.category.entity.QCategory.category;
import static today.what_should_i_eat_today.domain.report.entity.QReport.report;

@RequiredArgsConstructor
public class ReportDslRepositoryImpl implements ReportDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Report> findByTitleAndStatus(String title, ReportStatus status, Pageable pageable) {

        final QueryResults<Report> results = jpaQueryFactory
                .selectFrom(report)
                .join(report.member).fetchJoin()
                .where(titleContains(title), statusEq(status))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    private BooleanExpression statusEq(ReportStatus status) {
        return status != null ? report.status.eq(status) : null;
    }

    private BooleanExpression titleContains(String title) {
        return title != null ? report.title.contains(title) : null;
    }
}
