package today.what_should_i_eat_today.domain.category.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import today.what_should_i_eat_today.domain.category.entity.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryDslRepository {

    // 패치 조인 대신에 사용할 수 있는 방법
    @EntityGraph(attributePaths = {"admin"})
    @Override
    Optional<Category> findById(Long id);
}
