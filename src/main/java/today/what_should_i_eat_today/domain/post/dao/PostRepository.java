package today.what_should_i_eat_today.domain.post.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import today.what_should_i_eat_today.domain.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = "food")
    Page<Post> findAllByFoodId(Long foodId, Pageable pageable);
}