package today.what_should_i_eat_today.domain.world_cup.entity;

import lombok.*;
import today.what_should_i_eat_today.domain.tag.entity.TagValidator;
import today.what_should_i_eat_today.global.common.entity.BaseEntity;
import today.what_should_i_eat_today.domain.tag.entity.Tag;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private LocalDateTime createAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Tag tag;

    public void changeContent(String content) {
        this.content = content;
    }

    public void changeTag(Tag tag, TagValidator validator) {
        if (tag != null) {
            // validator 에게 유효성 검사 위임
            validator.validateTagForChange(tag);
            this.tag = tag;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(getId(), question.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
