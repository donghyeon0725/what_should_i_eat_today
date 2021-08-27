package today.what_should_i_eat_today.domain.favorite.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.favorite.entity.Favorite;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.post.entity.Post;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class FavoriteServiceTest {


    @Autowired
    private EntityManager em;

    @Autowired
    private FavoriteService favoriteService;

    @PersistenceUnit
    private EntityManagerFactory emf;


    @Test
    @DisplayName("내가 찜한 글들을 가져온다")
    void test() {
        Post post1 = Post.builder().title("글1").build();
        Post post2 = Post.builder().title("글2").build();
        Post post3 = Post.builder().title("글3").build();

        Member member = Member.builder().name("사람1").build();

        em.persist(member);
        em.persist(post1);
        em.persist(post2);
        em.persist(post3);

        Favorite build = Favorite.builder().member(member).post(post1).build();
        Favorite build1 = Favorite.builder().member(member).post(post2).build();

        em.persist(build);
        em.persist(build1);

        em.clear();


        List<Post> myFavorites = favoriteService.getMyFavoritePosts(member.getId());

        assertThat(myFavorites).extracting("title").containsExactly("글1", "글2");

//        myFavorites.stream().map(Post::getId).forEach(System.out::println);
        assertTrue(emf.getPersistenceUnitUtil().isLoaded(myFavorites.get(0)));

    }

}