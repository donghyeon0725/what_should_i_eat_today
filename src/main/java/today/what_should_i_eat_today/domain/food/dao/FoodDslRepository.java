package today.what_should_i_eat_today.domain.food.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import today.what_should_i_eat_today.domain.food.dto.FoodDto;
import today.what_should_i_eat_today.domain.food.entity.Food;
import today.what_should_i_eat_today.domain.tag.entity.Tag;

import java.util.List;

public interface FoodDslRepository {
    List<Food> findByTags(List<Long> tagIds);

    Page<Food> findBySearch(FoodDto foodDto, Pageable pageable);
}
