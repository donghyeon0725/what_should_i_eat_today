package today.what_should_i_eat_today.domain.recommend.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.member.mock.CustomMockUser;
import today.what_should_i_eat_today.domain.recommend.dto.RecommendCommand;
import today.what_should_i_eat_today.domain.recommend.entity.Recommend;
import today.what_should_i_eat_today.domain.recommend.entity.RecommendType;
import today.what_should_i_eat_today.domain.review.entity.Review;
import today.what_should_i_eat_today.domain.review.entity.ReviewType;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RecommendServiceTest {

    @Autowired
    private RecommendService recommendService;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("추천하기")
    @CustomMockUser(email = "test1234@test.com", roles = "USER")
    void test1() {
        Member member = Member.builder().email("test1234@test.com").build();
        Review review1 = Review.builder().reviewType(ReviewType.REVIEW).content("content1").build();
        Review review2 = Review.builder().reviewType(ReviewType.REVIEW).content("content2").build();
        Recommend recommend = Recommend.builder().review(review1).member(member).type(RecommendType.RECOMMEND).build();


        em.persist(member);
        em.persist(review1);
        em.persist(review2);
        em.persist(recommend);
        em.flush();
        em.clear();

        RecommendCommand command1 = RecommendCommand.builder().reviewId(review1.getId()).type(RecommendType.NOT_RECOMMEND).build();
        RecommendCommand command2 = RecommendCommand.builder().reviewId(review2.getId()).type(RecommendType.NOT_RECOMMEND).build();

        final Long recommend1 = recommendService.createRecommend(command1);
        final Long recommend2 = recommendService.createRecommend(command2);

        Recommend result1 = em.find(Recommend.class, recommend1);
        Recommend result2 = em.find(Recommend.class, recommend2);

        assertEquals(RecommendType.NOT_RECOMMEND, result1.getType(), "추천이 있었다면 해당 추천을 지우고 비추천으로 바뀐다.");
        assertEquals(RecommendType.NOT_RECOMMEND, result2.getType(), "추천 또는 비추천 할 수 있다.");
    }

    @Test
    @DisplayName("추천 취소하기")
    @CustomMockUser(email = "test1234@test.com", roles = "USER")
    void test2() {
        Member member = Member.builder().email("test1234@test.com").build();
        Review review = Review.builder().reviewType(ReviewType.REVIEW).content("content1").build();
        Recommend recommend = Recommend.builder().review(review).member(member).type(RecommendType.RECOMMEND).build();


        em.persist(member);
        em.persist(review);
        em.persist(recommend);
        em.flush();
        em.clear();

        RecommendCommand command = RecommendCommand.builder().recommendId(recommend.getId()).build();
        recommendService.cancelRecommend(command);

        final Recommend findRecommend = em.find(Recommend.class, recommend.getId());

        assertNull(findRecommend);
    }
}
