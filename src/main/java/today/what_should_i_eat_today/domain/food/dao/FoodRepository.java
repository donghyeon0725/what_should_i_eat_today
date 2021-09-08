package today.what_should_i_eat_today.domain.food.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import today.what_should_i_eat_today.domain.food.entity.Food;
import today.what_should_i_eat_today.domain.tag.entity.Tag;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long>, FoodDslRepository {

    @Query(value = "select f from FoodCategory fc join fc.food f join fc.category c where c.id = :categoryId"
            , countQuery = "select count(fc) from FoodCategory fc join fc.category c where c.id = :categoryId"
    )
    Page<Food> findFoodsByCategory(@Param("categoryId") Long categoryId, Pageable pageable);

    @Query(value = "SELECT * FROM food ORDER BY RAND() LIMIT :limit ", nativeQuery = true)
    List<Food> findFoodsRand(@Param("limit") Integer limit);

    Long countFoodByDeletedFalse();

    //f.rownum,
    @Query(value = "select ff.* from (SELECT row_number() over() as rnum, f.* FROM Food f where f.deleted = 'false') as ff where ff.rnum in :rows", nativeQuery = true)
    List<Food> findByRows(@Param("rows") List<Integer> rows);


    boolean existsByName(String name);

    @Query(value = "select case when count (f) > 0 then true else false end from Food f where f.name = :new and f.name <> :old")
    boolean existsByName(@Param("old") String oldName, @Param("new") String newName);

}
