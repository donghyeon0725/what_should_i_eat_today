package today.what_should_i_eat_today.domain.review.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import today.what_should_i_eat_today.domain.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @EntityGraph(attributePaths = {"member"})
    // 루트인 것만 가지고 와야 한다.
//    @Query(value = "select r from Review r join fetch r.member where r.post.id = :postId"
//        ,countQuery = "select r from Review r where r.post.id = :postId"
//    )
    Page<Review> findAllReviewByPostIdAndParentNull(@Param("postId") Long postId, Pageable pageable);

}
