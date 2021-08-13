package today.what_should_i_eat_today.domain.tag.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import today.what_should_i_eat_today.domain.tag.entity.Tag;

public interface TagDslRepository {
    Page<Tag> findByNameContains(String name, Pageable pageable);
}
