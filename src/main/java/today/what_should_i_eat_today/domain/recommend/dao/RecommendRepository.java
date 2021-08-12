package today.what_should_i_eat_today.domain.recommend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import today.what_should_i_eat_today.domain.recommend.entity.Recommend;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {
}