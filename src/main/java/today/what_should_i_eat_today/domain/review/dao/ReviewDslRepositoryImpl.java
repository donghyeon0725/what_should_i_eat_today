package today.what_should_i_eat_today.domain.review.dao;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.recommend.entity.QRecommend;
import today.what_should_i_eat_today.domain.recommend.entity.RecommendType;
import today.what_should_i_eat_today.domain.review.dto.ReviewDto;
import today.what_should_i_eat_today.domain.review.entity.Review;
import today.what_should_i_eat_today.domain.review.entity.ReviewStatus;

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
                .orderBy(review.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    // recommend total, 과 좋아요를 눌렀는지 여부
    @Override
    public Page<ReviewDto> findAllDtoByPostIdAndParentNull(Long postId, Member member, Pageable pageable) {
        QRecommend sub = new QRecommend("qRecommend");


        final QueryResults<ReviewDto> results = jpaQueryFactory
                .select(
                        Projections.fields(
                                ReviewDto.class,
                                review.id.as("id"),
                                review.member.nickName.as("nickname"),
                                review.content.as("content"),
                                review.status.as("status"),

                                ExpressionUtils.as(
                                    JPAExpressions
                                        .select(sub.count())
                                        .from(sub)
                                        .where(sub.review.id.eq(review.id), sub.type.eq(RecommendType.RECOMMEND)),
                            "recommendCount"
                                ),

                                ExpressionUtils.as(
                                    JPAExpressions
                                        .select(sub.count())
                                        .from(sub)
                                        .where(sub.review.id.eq(review.id), sub.type.eq(RecommendType.NOT_RECOMMEND)),
                                        "notRecommendCount"
                                ),

                                ExpressionUtils.as(
                                    JPAExpressions
                                        .select(sub.type)
                                        .from(sub)
                                        .where(sub.review.id.eq(review.id), sub.member.eq(member)),
                                "recommendType"
                                )

                        )

                )
                .from(review)
                .where(review.post.id.eq(postId), review.status.ne(ReviewStatus.BLIND), review.parent.isNull())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    @Override
    public Page<ReviewDto> findAllDtoByReview(Long reviewId, Member member, Pageable pageable) {
        QRecommend sub = new QRecommend("qRecommend");


        final QueryResults<ReviewDto> results = jpaQueryFactory
                .select(
                        Projections.fields(
                                ReviewDto.class,
                                review.id.as("id"),
                                review.member.nickName.as("nickname"),
                                review.content.as("content"),
                                review.status.as("status"),

                                ExpressionUtils.as(
                                        JPAExpressions
                                                .select(sub.count())
                                                .from(sub)
                                                .where(sub.review.id.eq(review.id), sub.type.eq(RecommendType.RECOMMEND)),
                                        "recommendCount"
                                ),

                                ExpressionUtils.as(
                                        JPAExpressions
                                                .select(sub.count())
                                                .from(sub)
                                                .where(sub.review.id.eq(review.id), sub.type.eq(RecommendType.NOT_RECOMMEND)),
                                        "notRecommendCount"
                                ),

                                ExpressionUtils.as(
                                        JPAExpressions
                                                .select(sub.type)
                                                .from(sub)
                                                .where(sub.review.id.eq(review.id), sub.member.eq(member)),
                                        "recommendType"
                                )

                        )

                )
                .from(review)
                .where(review.parent.id.eq(reviewId), review.status.ne(ReviewStatus.BLIND))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

}
