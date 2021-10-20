package today.what_should_i_eat_today.domain.likes.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import today.what_should_i_eat_today.domain.likes.entity.Likes;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.post.entity.Post;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    boolean existsByPostAndMember(Post post, Member member);

    Optional<Likes> findByPostAndMember(Post post, Member member);

    List<Likes> findAllByMemberId(Long memberId, Pageable pageable);

    @EntityGraph(attributePaths = {"post"})
    Page<Likes> findByMemberId(Long memberId, Pageable pageable);


    int countByMember(Member member);
}
