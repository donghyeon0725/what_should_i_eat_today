package today.what_should_i_eat_today.domain.favorite.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import today.what_should_i_eat_today.domain.favorite.entity.Favorite;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findAllByMemberId(Long memberId);
}
