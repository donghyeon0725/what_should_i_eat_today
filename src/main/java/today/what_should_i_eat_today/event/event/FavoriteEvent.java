package today.what_should_i_eat_today.event.event;

import today.what_should_i_eat_today.domain.favorite.entity.Favorite;
import today.what_should_i_eat_today.domain.likes.entity.Likes;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.post.entity.Post;

public class FavoriteEvent extends DomainEvent {
    public FavoriteEvent(Member member, Post post) {
        super(member, post);
    }

    public Member getMember() {
        return (Member) super.getDomain();
    }

    public Post getPost() {
        return (Post) super.getParam();
    }

}
