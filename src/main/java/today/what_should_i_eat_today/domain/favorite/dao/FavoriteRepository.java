package today.what_should_i_eat_today.domain.favorite.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import today.what_should_i_eat_today.domain.favorite.entity.Favorite;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.post.entity.Post;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findAllByMemberId(Long memberId, Pageable pageable);

    Optional<Favorite> findByPostAndMember(Post post, Member member);

    @EntityGraph(attributePaths = {"post"})
    Page<Favorite> findByMemberId(Long memberId, Pageable pageable);
}
