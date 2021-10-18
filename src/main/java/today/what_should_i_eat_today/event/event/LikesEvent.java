package today.what_should_i_eat_today.event.event;

import today.what_should_i_eat_today.domain.likes.entity.Likes;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.post.entity.Post;
import today.what_should_i_eat_today.domain.qna.entity.Qna;

public class LikesEvent extends DomainEvent {
    public LikesEvent(Member member, Post post) {
        super(Likes.builder().member(member).post(post).build());
    }

    public Likes getLikes() {
        return (Likes) super.getObject();
    }
}
