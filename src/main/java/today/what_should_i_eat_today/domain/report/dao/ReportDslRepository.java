package today.what_should_i_eat_today.domain.report.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import today.what_should_i_eat_today.domain.report.entity.Report;
import today.what_should_i_eat_today.domain.report.entity.ReportStatus;

public interface ReportDslRepository {
    Page<Report> findByTitleAndStatus(String title, ReportStatus status, Pageable pageable);
}
