package today.what_should_i_eat_today.domain.qna.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import today.what_should_i_eat_today.domain.qna.entity.QnaReview;

public interface QnaReviewRepository extends JpaRepository<QnaReview, Long> {
}