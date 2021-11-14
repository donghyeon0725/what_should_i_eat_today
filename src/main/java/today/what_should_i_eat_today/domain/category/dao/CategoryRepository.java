package today.what_should_i_eat_today.domain.category.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import today.what_should_i_eat_today.domain.category.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryDslRepository {

    // 패치 조인 대신에 사용할 수 있는 방법
    @EntityGraph(attributePaths = {"admin"})
    @Override
    Optional<Category> findById(Long id);

    List<Category> findByIdIn(List<Long> ids);

    boolean existsByName(String name);

    @EntityGraph(attributePaths = {"admin", "foodCategories"})
    @Override
    Page<Category> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"admin", "foodCategories"})
    Page<Category> findAllByNameContains(String categoryName, Pageable pageable);

    @Modifying
    @Query("DELETE FROM Category c WHERE c.id in :ids")
    void deleteAllByIdInQuery(@Param("ids") Iterable<Long> categoryIds);

}
