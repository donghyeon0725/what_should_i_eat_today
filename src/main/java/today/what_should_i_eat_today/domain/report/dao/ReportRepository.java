package today.what_should_i_eat_today.domain.report.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import today.what_should_i_eat_today.domain.report.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Long>, ReportDslRepository {
}
