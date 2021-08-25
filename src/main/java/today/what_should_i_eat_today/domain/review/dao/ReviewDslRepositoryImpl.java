package today.what_should_i_eat_today.domain.review.dao;

import com.querydsl.core.QueryResults;
import com.querydsl.core.util.ArrayUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import today.what_should_i_eat_today.domain.review.entity.QReview;
import today.what_should_i_eat_today.domain.review.entity.Review;
import today.what_should_i_eat_today.domain.review.entity.ReviewStatus;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static today.what_should_i_eat_today.domain.review.entity.QReview.review;

@RequiredArgsConstructor
public class ReviewDslRepositoryImpl implements ReviewDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Review> findAllReviewByPostIdAndParentNull(Long postId, Pageable pageable) {
//        QReview child = new QReview("child");

        final QueryResults<Review> results = jpaQueryFactory
                .selectFrom(review)
                .where(review.post.id.eq(postId), review.status.ne(ReviewStatus.BLIND), review.parent.isNull())
//                .join(review.child, child).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

}
