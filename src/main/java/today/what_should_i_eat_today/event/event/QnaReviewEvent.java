package today.what_should_i_eat_today.event.event;

import today.what_should_i_eat_today.domain.activity.entity.Activity;
import today.what_should_i_eat_today.domain.qna.entity.Qna;
import today.what_should_i_eat_today.domain.report.entity.Report;

public class QnaReviewEvent extends DomainEvent {

    public QnaReviewEvent(Qna qna) {
        super(qna);
    }

    public Qna getQna() {
        return (Qna)super.getObject();
    }

}
