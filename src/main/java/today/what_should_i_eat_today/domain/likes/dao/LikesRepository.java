package today.what_should_i_eat_today.domain.likes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import today.what_should_i_eat_today.domain.likes.entity.Likes;

public interface LikesRepository extends JpaRepository<Likes, Long> {
}