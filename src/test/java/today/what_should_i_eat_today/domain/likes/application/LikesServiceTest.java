package today.what_should_i_eat_today.domain.likes.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.likes.entity.Likes;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.post.entity.Post;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LikesServiceTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private LikesService likesService;

    @PersistenceUnit
    private EntityManagerFactory emf;


    @Test
    @DisplayName("내가 좋아요 한 글들을 가져온다")
    void test() {
        Post post1 = Post.builder().title("글1").build();
        Post post2 = Post.builder().title("글2").build();
        Post post3 = Post.builder().title("글3").build();

        Member member = Member.builder().name("사람1").build();

        em.persist(member);
        em.persist(post1);
        em.persist(post2);
        em.persist(post3);

        Likes likes1 = Likes.builder().member(member).post(post1).build();
        Likes likes2 = Likes.builder().member(member).post(post2).build();

        em.persist(likes1);
        em.persist(likes2);

        em.clear();


        PageRequest page = PageRequest.of(0, 10);

        List<Post> myLikes = likesService.getMyLikesPosts(member.getId(), page);

        // isLoaded 테스트 보기...
        assertTrue(emf.getPersistenceUnitUtil().isLoaded(myLikes.get(0)));

        assertThat(myLikes).extracting("title").containsExactly("글1", "글2");

    }
}