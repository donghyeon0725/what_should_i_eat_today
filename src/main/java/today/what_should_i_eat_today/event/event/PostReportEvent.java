package today.what_should_i_eat_today.event.event;

import today.what_should_i_eat_today.domain.report.entity.Report;

public class PostReportEvent extends DomainEvent {
    public PostReportEvent(Report report) {
        super(report);
    }

    public Report getReport() {
        return (Report)super.getDomain();
    }
}
