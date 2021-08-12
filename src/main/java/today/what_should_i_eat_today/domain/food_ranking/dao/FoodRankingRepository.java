package today.what_should_i_eat_today.domain.food_ranking.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import today.what_should_i_eat_today.domain.food_ranking.entity.FoodRanking;

public interface FoodRankingRepository extends JpaRepository<FoodRanking, Long> {
}