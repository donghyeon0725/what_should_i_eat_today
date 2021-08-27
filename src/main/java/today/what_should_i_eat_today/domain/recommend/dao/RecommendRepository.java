package today.what_should_i_eat_today.domain.recommend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.recommend.dto.RecommendCommand;
import today.what_should_i_eat_today.domain.recommend.entity.Recommend;
import today.what_should_i_eat_today.domain.review.entity.Review;

import java.util.Optional;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {
    Optional<Recommend> findByReviewAndMember(Review review, Member member);
}
