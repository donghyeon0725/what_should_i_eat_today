package today.what_should_i_eat_today.domain.world_cup.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import today.what_should_i_eat_today.domain.world_cup.entity.Course;
import today.what_should_i_eat_today.domain.world_cup.entity.Package;

public interface CourseDslRepository {
    Page<Course> findByPackage(Package packages, Pageable pageable);
}
