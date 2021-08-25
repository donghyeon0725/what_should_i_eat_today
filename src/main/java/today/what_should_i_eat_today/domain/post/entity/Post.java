package today.what_should_i_eat_today.domain.post.entity;

import lombok.*;
import today.what_should_i_eat_today.domain.food.entity.Food;
import today.what_should_i_eat_today.domain.likes.entity.Likes;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.model.Attachment;
import today.what_should_i_eat_today.global.common.entity.BaseEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
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

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Likes> likesSet = new HashSet<>();

    private boolean archived;

    public void removeLike(Likes likes) {
        likesSet.remove(likes);
    }

    public void addLike(Likes likes) {
        likesSet.add(likes);
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
