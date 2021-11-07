package today.what_should_i_eat_today.domain.review.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.post.entity.Post;
import today.what_should_i_eat_today.domain.review.dto.ReviewCommand;
import today.what_should_i_eat_today.event.event.ReplyActivityEvent;
import today.what_should_i_eat_today.event.event.ReviewActivityEvent;
import today.what_should_i_eat_today.event.service.Events;
import today.what_should_i_eat_today.global.common.entity.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
//@Where(clause = "status != 'BLIND'")
public class Review extends BaseEntity {

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

    @Builder.Default
    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Review> child = new ArrayList<>();

    @Builder.Default
    @ColumnDefault(value = "0")
    private Long childCount = 0L;

    @Enumerated(EnumType.STRING)
    private ReviewType reviewType;

    private String content;

    @Enumerated(EnumType.STRING)
    private ReviewStatus status;

    public void show() {
        this.status = ReviewStatus.SHOW;
    }

    public void blind() {
        this.status = ReviewStatus.BLIND;
    }

    public void changeContent(ReviewCommand command, ReviewValidator validator) {
        validator.writerValidate(command.getContent());
        validator.writerValidate(this.getMember().getId(), command.getMemberId());
        this.content = command.getContent();
    }

    public void addParentReview(Review parent) {
        this.parent = parent;
    }

    public void addChildReview(Review review, ReviewValidator validator) {
        validator.childAddValidate(this);
        this.child.add(review);
        this.childCount++;
        review.addParentReview(this);
    }

    public void addChildReview(Review review) {
        this.child.add(review);
        this.childCount++;
        review.addParentReview(this);
    }

    public void removeChildReview(Review review) {
        this.childCount--;
    }

    public void placeReview() {
        placeType(ReviewType.REVIEW);
    }

    public void placeReply() {
        placeType(ReviewType.REPLY);
    }

    private void placeType(ReviewType type) {

        if (type == ReviewType.REVIEW) {
            this.reviewType = ReviewType.REVIEW;
            Events.raise(new ReviewActivityEvent(this));
        } else if (type == ReviewType.REPLY) {
            this.reviewType = ReviewType.REPLY;
            Events.raise(new ReplyActivityEvent(this));
        }

    }

    public void delete() {
        this.status = ReviewStatus.BLIND;

        if (this.parent != null) {
            this.parent.removeChildReview(this);
        }
    }
}
