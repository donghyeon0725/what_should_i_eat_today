package today.what_should_i_eat_today.domain.post.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.likes.entity.Likes;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.post.entity.Post;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private PostService postService;

    @Test
    @DisplayName("좋아요 하지않은 글에 좋아요 하기")
    void test() {
        Member member = Member.builder()
                .name("root")
                .build();

        Post post = Post.builder()
                .content("글1")
                .build();

        em.persist(member);
        em.persist(post);
        em.clear();

        boolean isLike = postService.updateLike(post.getId(), member.getId());
        Post findPost = em.find(Post.class, post.getId());
        List<Likes> findLikesList = em.createQuery("SELECT l from Likes l", Likes.class).getResultList();

        assertThat(isLike).isTrue();
        assertThat(findPost.getLikesSet()).hasSize(1);
        assertThat(findLikesList).hasSize(1);

    }

    @Test
    @DisplayName("이미 좋아요 한 글에는 좋아요를 삭제한다")
    void test2() {
        Member member = Member.builder()
                .name("root")
                .build();

        Post post = Post.builder()
                .content("글1")
                .build();

        Likes likes = Likes.builder()
                .post(post)
                .member(member)
                .build();

        em.persist(member);
        em.persist(post);
        em.persist(likes);
        em.clear();

        boolean isLike = postService.updateLike(post.getId(), member.getId());
        Post findPost = em.find(Post.class, post.getId());
        List<Likes> findLikesList = em.createQuery("SELECT l from Likes l", Likes.class).getResultList();

        assertThat(isLike).isFalse();
        assertThat(findPost.getLikesSet()).isEmpty();
        assertThat(findLikesList).isEmpty();

    }

}