package today.what_should_i_eat_today.domain.world_cup.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import today.what_should_i_eat_today.domain.world_cup.entity.PackageCourse;

public interface PackageCourseRepository extends JpaRepository<PackageCourse, Long> {
}
