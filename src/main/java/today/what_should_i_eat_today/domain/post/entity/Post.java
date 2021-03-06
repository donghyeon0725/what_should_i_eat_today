package today.what_should_i_eat_today.domain.post.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import today.what_should_i_eat_today.domain.favorite.entity.Favorite;
import today.what_should_i_eat_today.domain.food.entity.Food;
import today.what_should_i_eat_today.domain.likes.entity.Likes;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.model.Attachment;
import today.what_should_i_eat_today.global.common.entity.BaseEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@DynamicInsert
@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
//@Where(clause = "archived = false")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Food food;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Embedded
    private Attachment attachment;

    private String title;

    @Lob
    private String content;

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Likes> likesSet = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Favorite> favoriteSet = new HashSet<>();

    private boolean archived;

    @Transient
    private Boolean isLikedByMe;

    @Transient
    private Boolean isFavoriteByMe;

    @ColumnDefault(value = "0")
    private Long numberOfLikes;

    @ColumnDefault(value = "0")
    private Long numberOfFavorites;

    public void incrementLikes() {
        numberOfLikes++;
    }

    public void decrementLikes() {
        numberOfLikes--;
    }

    public void incrementFavorites() {
        numberOfFavorites++;
    }

    public void decrementFavorites() {
        numberOfFavorites--;
    }

    public void changeLikeStatus(boolean status) {
        this.isLikedByMe = status;
    }

    public void changeFavoriteStatus(boolean status) {
        this.isFavoriteByMe = status;
    }

    public void removeLike(Likes likes) {
        likesSet.remove(likes);
    }

    public void addLike(Likes likes) {
        likesSet.add(likes);
    }

    public void addFavorite(Favorite favorite) {
        favoriteSet.add(favorite);
    }

    public void removeFavorite(Favorite favorite) {
        favoriteSet.remove(favorite);
    }

    public boolean isPostCreator(Long memberId) {
        return this.member.getId().equals(memberId);
    }

    public Post update(Attachment attachment, String title, String content) {
        this.attachment = attachment;
        this.title = title;
        this.content = content;
        return this;
    }

    public void delete() {
        this.archived = true;
    }
}
