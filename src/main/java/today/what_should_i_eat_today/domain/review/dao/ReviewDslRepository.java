package today.what_should_i_eat_today.domain.review.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import today.what_should_i_eat_today.domain.review.entity.Review;

import java.util.List;

public interface ReviewDslRepository {
    Page<Review> findAllReviewByPostIdAndParentNull(Long postId, Pageable pageable);
}
