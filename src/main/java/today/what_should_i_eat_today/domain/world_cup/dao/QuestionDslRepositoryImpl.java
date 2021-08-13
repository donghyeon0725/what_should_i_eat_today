package today.what_should_i_eat_today.domain.world_cup.dao;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import today.what_should_i_eat_today.domain.world_cup.entity.Question;

import static today.what_should_i_eat_today.domain.world_cup.entity.QQuestion.question;

/**
 * querydsl 기반 repository
 * 명명 규칙은 구현 인터페이스 이름 + Impl
 *
 * */
@RequiredArgsConstructor
public class QuestionDslRepositoryImpl implements QuestionDslRepository {

    private final JPAQueryFactory queryFactory;


    @Override
    public Page<Question> findByContent(String content, Pageable pageable) {
        QueryResults<Question> results = queryFactory
                .selectFrom(question)
                .where(contentContains(content))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl(results.getResults(), pageable, results.getTotal());
    }

    private BooleanExpression contentContains(String content) {
        return content != null ? question.content.contains(content) : null;
    }
}
