package today.what_should_i_eat_today.domain.world_cup.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import today.what_should_i_eat_today.domain.world_cup.entity.Course;
import today.what_should_i_eat_today.domain.world_cup.entity.Package;
import today.what_should_i_eat_today.domain.world_cup.entity.PackageCourse;

import java.util.Optional;

public interface PackageCourseRepository extends JpaRepository<PackageCourse, Long> {

    boolean existsCourseByPackages(Package packages);

    Optional<PackageCourse> findByCourseAndPackages(Course course,Package packges);
}
