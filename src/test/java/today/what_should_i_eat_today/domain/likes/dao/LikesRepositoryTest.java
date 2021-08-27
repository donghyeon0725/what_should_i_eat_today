package today.what_should_i_eat_today.domain.likes.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import today.what_should_i_eat_today.domain.likes.entity.Likes;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.post.entity.Post;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureDataJpa
class LikesRepositoryTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private LikesRepository likesRepository;

    @TestConfiguration
    static class TestConfig {
        @PersistenceContext
        private EntityManager entityManager;

        @Bean
        public JPAQueryFactory jpaQueryFactory() {
            return new JPAQueryFactory(entityManager);
        }
    }

    @Test
    @DisplayName("내가 좋아요 한 글을 가져온다")
    void test() {

        Member martinMember = Member.builder().name("martin").build();

        em.persist(martinMember);

        for (int i = 0; i < 50; i++) {
            Post post = Post.builder().title("테스트 글" + i).build();
            em.persist(post);

            // martin 은 2번째 글 마다 찜한다
            if (i % 2 == 0)
                em.persist(Likes.builder().member(martinMember).post(post).build());
        }
        em.clear();

        List<Likes> likesOfMartin = likesRepository.findAllByMemberId(martinMember.getId());

        List<Post> likesPostsFromMartin = likesOfMartin.stream().map(Likes::getPost).collect(Collectors.toList());

        String[] martinPostsTitles = new String[25];

        for (int i = 0; i < martinPostsTitles.length; i++) {
            martinPostsTitles[i] = "테스트 글" + i * 2;
        }

        assertThat(likesPostsFromMartin)
                .extracting("title")
                .containsExactly(martinPostsTitles)
        ;
    }

}