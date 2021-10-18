package today.what_should_i_eat_today.domain.member.entity;

import lombok.*;
import org.springframework.util.StringUtils;
import today.what_should_i_eat_today.domain.model.AuthProvider;
import today.what_should_i_eat_today.domain.post.entity.Post;
import today.what_should_i_eat_today.domain.post.entity.PostValidator;
import today.what_should_i_eat_today.event.event.DislikeEvent;
import today.what_should_i_eat_today.event.event.FavoriteEvent;
import today.what_should_i_eat_today.event.event.LikesEvent;
import today.what_should_i_eat_today.event.event.UnFavoriteEvent;
import today.what_should_i_eat_today.event.service.Events;
import today.what_should_i_eat_today.global.common.entity.BaseEntity;

import javax.persistence.*;
import java.awt.*;
import java.util.Objects;

@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String profileImg;

    private String email;

    private String name;

    private String nickName;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    public void changeToDefaultProfile() {
        this.profileImg = Profile.DEFAULT.getPath();
    }

    public void updateNameAndImage(String name, String imageUrl) {
        this.name = name;
        this.profileImg = imageUrl;
    }

    public void updateNickNameAndImage(String nickName, String imageUrl) {
        this.nickName = StringUtils.hasText(nickName) ? nickName : name;
        this.profileImg = StringUtils.hasText(imageUrl) ? imageUrl : profileImg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Member)) return false;
        Member member = (Member) o;
        return Objects.equals(getId(), member.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public void likePost(Post post, PostValidator postValidator) {
        postValidator.validateForLike(this, post);
        Events.raise(new LikesEvent(this, post));
    }

    public void dislikedPost(Post post, PostValidator postValidator) {
        postValidator.validateForDislike(this, post);
        Events.raise(new DislikeEvent(this, post));
    }

    public void favoritePost(Post post, PostValidator postValidator) {
        postValidator.validateForFavorite(this, post);
        Events.raise(new FavoriteEvent(this, post));
    }

    public void unFavoritePost(Post post, PostValidator postValidator) {
        postValidator.validateForUnFavorite(this, post);
        Events.raise(new UnFavoriteEvent(this, post));
    }
}
