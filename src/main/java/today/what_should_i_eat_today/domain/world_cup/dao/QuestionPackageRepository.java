package today.what_should_i_eat_today.domain.world_cup.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import today.what_should_i_eat_today.domain.world_cup.entity.Question;
import today.what_should_i_eat_today.domain.world_cup.entity.QuestionPackage;

public interface QuestionPackageRepository extends JpaRepository<QuestionPackage, Long> {
    boolean existsByQuestion(Question question);
}
