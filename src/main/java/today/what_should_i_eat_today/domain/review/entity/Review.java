package today.what_should_i_eat_today.domain.review.entity;

import lombok.*;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.post.entity.Post;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Review parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Review> child = new ArrayList<>();

    private String content;

    public void changeContent(String content, ReviewValidator validator) {
        validator.contentValidate(content);
        this.content = content;
    }

    public void addParentReview(Review parent) {
        this.parent = parent;
    }

    public void addChildReview(Review review, ReviewValidator validator) {
        validator.childAddValidate(this);

        this.child.add(review);
        review.addParentReview(this);
    }

}
