package today.what_should_i_eat_today.domain.favorite.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import today.what_should_i_eat_today.domain.favorite.entity.Favorite;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.post.entity.Post;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureDataJpa
class FavoriteRepositoryTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private FavoriteRepository favoriteRepository;

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
    @DisplayName("내가 찜한 글들을 가져온다")
    void test() {


        Member martinMember = Member.builder().name("martin").build();
        Member bennyMember = Member.builder().name("benny").build();

        em.persist(martinMember);
        em.persist(bennyMember);


        for (int i = 0; i < 50; i++) {
            Post post = Post.builder().title("테스트 글" + i).build();
            em.persist(post);

            // martin 은 2번째 글 마다 찜한다
            if (i % 2 == 0)
                em.persist(Favorite.builder().member(martinMember).post(post).build());

            // benny 는 10번째 글 마다 찜한다
            if (i % 10 == 0)
                em.persist(Favorite.builder().member(bennyMember).post(post).build());
        }
        em.clear();

        System.err.println("==============================================================");

        List<Favorite> favorites = favoriteRepository.findAllByMemberId(martinMember.getId());

        List<Post> martinFavoritePosts = favorites.stream().map(Favorite::getPost).collect(Collectors.toList());


        String[] str = new String[25];

        for (int i = 0; i < str.length; i++) {
            str[i] = "테스트 글" + i * 2;
        }

        assertThat(martinFavoritePosts)
                .extracting("title")
                .containsExactly(str)
        ;
    }
}
