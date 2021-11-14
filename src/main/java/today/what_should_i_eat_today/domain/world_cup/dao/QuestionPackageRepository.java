package today.what_should_i_eat_today.domain.world_cup.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import today.what_should_i_eat_today.domain.world_cup.entity.Package;
import today.what_should_i_eat_today.domain.world_cup.entity.Question;
import today.what_should_i_eat_today.domain.world_cup.entity.QuestionPackage;

import java.util.Optional;

public interface QuestionPackageRepository extends JpaRepository<QuestionPackage, Long> {
    boolean existsByQuestion(Question question);

    Optional<QuestionPackage> findByPackagesAndQuestion(Package packages, Question question);
}
