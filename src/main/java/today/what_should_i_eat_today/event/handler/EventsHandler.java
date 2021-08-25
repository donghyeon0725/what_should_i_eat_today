package today.what_should_i_eat_today.event.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import today.what_should_i_eat_today.domain.activity.dao.ActivityRepository;
import today.what_should_i_eat_today.domain.activity.entity.Activity;
import today.what_should_i_eat_today.domain.activity.entity.ActivityType;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.post.entity.Post;
import today.what_should_i_eat_today.domain.review.entity.Review;
import today.what_should_i_eat_today.event.event.ReplyActivityEvent;
import today.what_should_i_eat_today.event.event.ReviewActivityEvent;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class EventsHandler {
    private final ActivityRepository activityRepository;

    @Async
    @Transactional
    @EventListener
    public void handle(ReviewActivityEvent event) {
        Review review = event.getReview();
        Post post = review.getPost();
        Member postOwner = post.getMember();

        activityRepository.save(Activity.builder().post(post).member(postOwner).review(review).type(ActivityType.REVIEW).build());
    }

    @Async
    @Transactional
    @EventListener
    public void handle(ReplyActivityEvent event) {
        Review review = event.getReply();
        Post post = review.getPost();
        Member reviewOwner = review.getParent().getMember();

        activityRepository.save(Activity.builder().post(post).member(reviewOwner).review(review).type(ActivityType.REPLY).build());
    }


}
