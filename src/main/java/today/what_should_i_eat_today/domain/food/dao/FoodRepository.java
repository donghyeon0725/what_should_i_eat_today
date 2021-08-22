package today.what_should_i_eat_today.domain.food.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import today.what_should_i_eat_today.domain.food.entity.Food;

public interface FoodRepository extends JpaRepository<Food, Long> {

    @Query(value = "select f from FoodCategory fc join fc.food f join fc.category c where c.id = :categoryId"
        , countQuery = "select count(fc) from FoodCategory fc join fc.category c where c.id = :categoryId"
    )
    Page<Food> findFoodsByCategory(@Param("categoryId") Long categoryId, Pageable pageable);
}
