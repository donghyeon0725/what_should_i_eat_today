package today.what_should_i_eat_today.domain.food.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import today.what_should_i_eat_today.domain.food.entity.FoodTag;

import java.util.List;

public interface FoodTagRepository extends JpaRepository<FoodTag, Long> {

    @EntityGraph(attributePaths = "food.country")
    List<FoodTag> findByFoodIdIn(List<Long> foodIds);
}
