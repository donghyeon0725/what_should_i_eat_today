package today.what_should_i_eat_today.domain.tag.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import today.what_should_i_eat_today.domain.tag.entity.Tag;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long>, TagDslRepository{
    boolean existsByName(String name);

    long countByIdIn(List<Long> tagIds);
}
