package today.what_should_i_eat_today.event.event;

import today.what_should_i_eat_today.domain.report.entity.Report;
import today.what_should_i_eat_today.domain.review.entity.Review;

public class ProfileReportEvent extends DomainEvent {
    public ProfileReportEvent(Report report) {
        super(report);
    }

    public Report getReport() {
        return (Report)super.getObject();
    }
}
