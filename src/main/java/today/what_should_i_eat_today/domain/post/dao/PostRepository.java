package today.what_should_i_eat_today.domain.post.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import today.what_should_i_eat_today.domain.food.entity.Food;
import today.what_should_i_eat_today.domain.post.entity.Post;

import javax.persistence.Entity;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Override
    @EntityGraph(attributePaths = {"food", "member"})
    Optional<Post> findById(Long id);

    @EntityGraph(attributePaths = "food")
    Page<Post> findAllByFoodId(Long foodId, Pageable pageable);


    @Query(value =
            "select * from post where id in (" +
            "select id from ( " +
                "select id, rank() over (partition by p.food_id order by p.id desc) as rn " +
                "from post p " +
                "where p.food_id in :foods" +
            ")" +
            "as ranking where ranking.rn <= 1)", nativeQuery = true)
    List<Post> findPostByFoodTop1(@Param("foods") List<Long> foodsId);

    @Query(value =
            "select * from post where id in (" +
                    "select id from ( " +
                    "select id, rank() over (partition by p.food_id order by p.id desc) as rn " +
                    "from post p " +
                    "where p.food_id = :foodId" +
                    ")" +
                    "as ranking where ranking.rn <= 1)", nativeQuery = true)
    Post findPostByFood1(@Param("foodId") Long foodId);


    @EntityGraph(attributePaths = {"food", "member"})
    Page<Post> findByMember_Id(Long memberId, Pageable pageable);

    @EntityGraph(attributePaths = {"food", "member"})
    Page<Post> findByMember_IdAndArchivedIsFalse(Long memberId, Pageable pageable);
}
