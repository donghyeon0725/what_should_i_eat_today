package today.what_should_i_eat_today.domain.activity.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import today.what_should_i_eat_today.domain.activity.entity.Activity;
import today.what_should_i_eat_today.domain.member.entity.Member;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    Page<Activity> findByMember(Member member, Pageable pageable);
}
