package today.what_should_i_eat_today.domain.world_cup.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import today.what_should_i_eat_today.domain.world_cup.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long>, QuestionDslRepository {
    Page<Question> findByContent(String content, Pageable pageable);
}
