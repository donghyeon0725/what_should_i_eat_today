package today.what_should_i_eat_today.event.event;

import today.what_should_i_eat_today.domain.review.entity.Review;

public class ReviewActivityEvent extends DomainEvent {
    public ReviewActivityEvent(Review review) {
        super(review);
    }

    public Review getReview() {
        return (Review)super.getObject();
    }
}
