package today.what_should_i_eat_today.domain.activity.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import today.what_should_i_eat_today.domain.activity.entity.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
