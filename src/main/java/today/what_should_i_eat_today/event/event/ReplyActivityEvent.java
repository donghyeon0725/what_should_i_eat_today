package today.what_should_i_eat_today.event.event;

import today.what_should_i_eat_today.domain.review.entity.Review;

public class ReplyActivityEvent extends DomainEvent {
    public ReplyActivityEvent(Review review) {
        super(review);
    }

    public Review getReply() {
        return (Review)super.getDomain();
    }
}
