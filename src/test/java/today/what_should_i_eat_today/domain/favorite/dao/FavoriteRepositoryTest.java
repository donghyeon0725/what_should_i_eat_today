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
import java.util.List;
import java.util.stream.Collectors;

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


        List<Favorite> martinFavorites = favoriteRepository.findAllByMemberId(martinMember.getId());
        List<Favorite> bennyFavorites = favoriteRepository.findAllByMemberId(bennyMember.getId());

        List<Member> martinMemberOfFavoritePosts = martinFavorites.stream().map(Favorite::getMember).collect(Collectors.toList());
        List<Post> martinFavoritePosts = martinFavorites.stream().map(Favorite::getPost).collect(Collectors.toList());
        List<Member> bennyMemberOfFavoritePosts = bennyFavorites.stream().map(Favorite::getMember).collect(Collectors.toList());
        List<Post> bennyFavoritePosts = bennyFavorites.stream().map(Favorite::getPost).collect(Collectors.toList());


        String[] martinPostsTitles = new String[25];
        String[] martins = new String[25];
        String[] bennyPostsTitles = new String[5];
        String[] bennys = new String[5];

        for (int i = 0; i < martinPostsTitles.length; i++) {
            martinPostsTitles[i] = "테스트 글" + i * 2;
            martins[i] = "martin";
        }
        for (int i = 0; i < bennyPostsTitles.length; i++) {
            bennyPostsTitles[i] = "테스트 글" + i * 10;
            bennys[i] = "benny";
        }

        assertThat(martinFavoritePosts)
                .extracting("title")
                .containsExactly(martinPostsTitles)
        ;

        assertThat(martinMemberOfFavoritePosts)
                .extracting("name")
                .containsExactly(martins)
        ;

        assertThat(bennyFavoritePosts)
                .extracting("title")
                .containsExactly(bennyPostsTitles)
        ;

        assertThat(bennyMemberOfFavoritePosts)
                .extracting("name")
                .containsExactly(bennys)
        ;
    }
}
