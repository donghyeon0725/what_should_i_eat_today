package today.what_should_i_eat_today.domain.category.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import today.what_should_i_eat_today.domain.category.entity.Category;

public interface CategoryDslRepository {
    Page<Category> findVisibleCategoryByName(String name, Pageable pageable);
    Page<Category> findCategoryByName(String name, Pageable pageable);
}
