package today.what_should_i_eat_today.domain.qna.dao;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import today.what_should_i_eat_today.domain.qna.entity.QQna;
import today.what_should_i_eat_today.domain.qna.entity.Qna;
import today.what_should_i_eat_today.domain.qna.entity.QnaStatus;

import static today.what_should_i_eat_today.domain.qna.entity.QQna.qna;

@RequiredArgsConstructor
public class QnaDslRepositoryImpl implements QnaDslRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Qna> findByTitleAndStatus(String title, QnaStatus qnaStatus, Pageable pageable) {

        final QueryResults<Qna> results = jpaQueryFactory
                .selectFrom(qna)
                .where(titleContains(title), statusEq(qnaStatus))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    private BooleanExpression statusEq(QnaStatus qnaStatus) {
        return qnaStatus != null ? qna.status.eq(qnaStatus) : null;
    }

    private BooleanExpression titleContains(String title) {
        return title != null ? qna.title.contains(title) : null;
    }
}
