package today.what_should_i_eat_today.domain.review.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import today.what_should_i_eat_today.domain.review.entity.Review;
import today.what_should_i_eat_today.domain.review.entity.ReviewStatus;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewDslRepository {
    @Override
    @EntityGraph(attributePaths = {"parent", "post", "member", "post.food"})
    Optional<Review> findById(Long id);

    @EntityGraph(attributePaths = {"member", "parent"})
    List<Review> findAllByPostIdAndParentIdAndStatusOrderByCreatedAtDesc(Long postId, Long parentId, ReviewStatus status);

    Long countAllByPostIdAndStatus(Long postId, ReviewStatus status);
}
