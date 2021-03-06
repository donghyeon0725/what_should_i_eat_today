package today.what_should_i_eat_today.domain.world_cup.entity;

import lombok.*;
import today.what_should_i_eat_today.global.common.entity.BaseEntity;

import javax.persistence.*;

@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionPackage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Package packages;

    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

    public void mappingToPackage(Package packages) {
        this.packages = packages;
    }
}
