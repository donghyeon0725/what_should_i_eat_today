package today.what_should_i_eat_today.domain.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import today.what_should_i_eat_today.domain.admin.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}