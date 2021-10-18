package today.what_should_i_eat_today.event.event;

import today.what_should_i_eat_today.domain.likes.entity.Likes;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.post.entity.Post;

public class DislikeEvent extends DomainEvent {
    public DislikeEvent(Member member, Post post) {
        super(Likes.builder().member(member).post(post).build());
    }

    public Likes getDislike() {
        return (Likes) super.getObject();
    }
}
