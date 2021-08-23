package today.what_should_i_eat_today.domain.review.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.member.mock.CustomMockUser;
import today.what_should_i_eat_today.domain.post.entity.Post;
import today.what_should_i_eat_today.domain.review.dto.ReviewCommand;
import today.what_should_i_eat_today.domain.review.entity.Review;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private EntityManager em;

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Test
    @DisplayName("댓글 조회하기 테스트. N + 1 쿼리 성능 최적화 확인.")
    void test1() {
        Member member = Member.builder().name("member").nickName("test").build();
        Post post = Post.builder().content("test").title("test").build();

        Review root1 = Review.builder().content("root").parent(null).member(member).post(post).build();
        Review child1 = Review.builder().content("child1").member(member).post(post).parent(root1).build();
        Review child2 = Review.builder().content("child2").member(member).post(post).parent(root1).build();

        Review root2 = Review.builder().content("root").parent(null).member(member).post(post).build();
        Review child3 = Review.builder().content("child1").member(member).post(post).parent(root2).build();
        Review child4 = Review.builder().content("child2").member(member).post(post).parent(root2).build();

        Review root3 = Review.builder().content("root").parent(null).member(member).post(post).build();
        Review child5 = Review.builder().content("child1").member(member).post(post).parent(root3).build();
        Review child6 = Review.builder().content("child2").member(member).post(post).parent(root3).build();

        em.persist(child1);
        em.persist(child2);
        em.persist(child3);
        em.persist(child4);
        em.persist(child5);
        em.persist(child6);
        em.persist(root1);
        em.persist(root2);
        em.persist(root3);
        em.persist(post);
        em.persist(member);
        em.flush();
        em.clear();

        ReviewCommand command = ReviewCommand.builder().postId(post.getId()).build();
        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<Review> reviewList = reviewService.getReviewList(command, pageRequest);
        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(reviewList.getContent().get(0).getChild());

        assertTrue(loaded);
    }

    @Test
    @DisplayName("리뷰 생성 테스트")
    @CustomMockUser(email = "test1234@test.com", roles = "USER")
    void test2() {
        Post post = Post.builder().title("타이틀").content("콘텐츠").build();
        Member member = Member.builder().email("test1234@test.com").nickName("test").build();

        em.persist(post);
        em.persist(member);
        em.flush();
        em.clear();

        ReviewCommand command = ReviewCommand.builder().content("댓글").postId(post.getId()).memberId(member.getId()).build();
        Long reviewId = reviewService.createReview(command);

        Review review = em.find(Review.class, reviewId);

        assertEquals(reviewId, review.getId(), "리뷰를 생성할 수 있어야 한다.");
        assertEquals("댓글", review.getContent(), "리뷰를 생성할 수 있어야 한다.");
    }


    @Test
    @DisplayName("답급 작성하기 테스트")
    @CustomMockUser(email = "test1234@test.com", roles = "USER")
    void test3() {
        Post post = Post.builder().title("타이틀").content("콘텐츠").build();
        Member member = Member.builder().email("test1234@test.com").nickName("test").build();
        Review review = Review.builder().post(post).member(member).content("테스트").build();

        em.persist(post);
        em.persist(member);
        em.persist(review);
        em.flush();
        em.clear();

        ReviewCommand command = ReviewCommand.builder().content("답글").postId(post.getId()).parentId(review.getId()).memberId(member.getId()).build();

        Long reviewId = reviewService.replyReview(command);
        Review findReview = em.find(Review.class, reviewId);

        assertEquals(command.getContent(), findReview.getContent(), "답글을 정상 생성할 수 있어야 한다.");
        assertEquals(review.getId(), findReview.getParent().getId(), "댓글에 답글을 달 수 있어야 한다.");
    }


    @Test
    @DisplayName("댓글 조회하기 테스트")
    void test4() {
        Member member = Member.builder().name("member").nickName("test").build();
        Post post = Post.builder().content("test").title("test").build();

        Review root = Review.builder().content("root").parent(null).member(member).post(post).build();
        Review child1_1 = Review.builder().content("child1_1").member(member).post(post).parent(root).build();
        Review child1_2 = Review.builder().content("child1_2").member(member).post(post).parent(root).build();
        Review child2_1 = Review.builder().content("child2_1").member(member).post(post).parent(child1_1).build();
        
        em.persist(member);
        em.persist(post);
        em.persist(root);
        em.persist(child1_1);
        em.persist(child1_2);
        em.persist(child2_1);
        em.flush();
        em.clear();
        
        
        ReviewCommand command = ReviewCommand.builder().postId(post.getId()).build();
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Review> reviewList = reviewService.getReviewList(command, pageRequest);


        assertEquals(1, reviewList.getContent().size(), () -> "댓글이 정확하게 조회 되어야 한다.");
        assertEquals(2, reviewList.getContent().get(0).getChild().size(), () -> "답글이 정확하게 조회 되어야 한다.");
    }

    @Test
    @DisplayName("댓글 수정하기 테스트")
    void test5() {
        Post post = Post.builder().title("타이틀").content("콘텐츠").build();
        Member member = Member.builder().email("test1234@test.com").nickName("test").build();
        Review review = Review.builder().post(post).member(member).content("테스트").build();

        em.persist(post);
        em.persist(member);
        em.persist(review);
        em.flush();
        em.clear();

        ReviewCommand command = ReviewCommand.builder().id(review.getId()).content("수정합니다").build();

        reviewService.updateReview(command);

        Review findReview = em.find(Review.class, review.getId());

        assertEquals("수정합니다", findReview.getContent());
    }


    @Test
    @DisplayName("댓글 삭제하기 테스트")
    void test6() {
        Post post = Post.builder().title("타이틀").content("콘텐츠").build();
        Member member = Member.builder().email("test1234@test.com").nickName("test").build();
        Review review = Review.builder().post(post).member(member).content("테스트").build();
        Review reply1 = Review.builder().post(post).member(member).parent(review).content("답글").build();
        Review reply2 = Review.builder().post(post).member(member).parent(review).content("답글").build();

        em.persist(post);
        em.persist(member);
        em.persist(review);
        em.persist(reply1);
        em.persist(reply2);
        em.flush();
        em.clear();

        reviewService.deleteReview(review.getId());

        Review review1 = em.find(Review.class, reply1.getId());
        Review review2 = em.find(Review.class, reply2.getId());

        assertNull(review1);
        assertNull(review2);
    }
}
