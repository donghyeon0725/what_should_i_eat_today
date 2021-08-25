package today.what_should_i_eat_today.domain.review.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import today.what_should_i_eat_today.domain.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewDslRepository {
}
