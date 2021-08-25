package today.what_should_i_eat_today.domain.review.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.member.entity.MemberStatus;
import today.what_should_i_eat_today.domain.post.entity.Post;
import today.what_should_i_eat_today.domain.review.entity.Review;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private EntityManager em;

    @PersistenceUnit
    private EntityManagerFactory emf;


    @Test
    @DisplayName("자식 리뷰를 EntityGraph 를 통해 페치 조인으로 가져오는지 검사")
    void test1() {
        // TODO 깊이가 1이상에 있는 review 를 가지고 와야 한다.
        Member member = Member.builder().name("member").nickName("test").build();
        Post post = Post.builder().content("test").title("test").build();

        Review root = Review.builder().content("root").parent(null).member(member).post(post).build();
        Review child1_1 = Review.builder().content("child1_1").member(member).post(post).parent(root).build();
        Review child1_2 = Review.builder().content("child1_2").member(member).post(post).parent(root).build();
        Review child2_1 = Review.builder().content("child2_1").member(member).post(post).parent(child1_1).build();

        em.persist(child1_1);
        em.persist(child1_2);
        em.persist(child2_1);
        em.persist(member);
        em.persist(root);
        em.persist(post);
        em.flush();
        em.clear();

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Review> findReview = reviewRepository.findAllReviewByPostIdAndParentNull(post.getId(), pageRequest);

        assertFalse(emf.getPersistenceUnitUtil().isLoaded(findReview.getContent().get(0).getChild()));
        assertEquals(1, findReview.getContent().size(), () -> "root 댓글만 가져와야 한다.");
    }
}
