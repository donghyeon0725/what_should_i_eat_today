package today.what_should_i_eat_today.domain.world_cup.entity;

import lombok.*;
import today.what_should_i_eat_today.global.common.entity.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * PackageCourse 생명주기는 Package 가 관리
 *
 * */
@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"subject"})
public class Package extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;

    private LocalDateTime createAt;

    @Builder.Default
    @OneToMany(mappedBy = "packages", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionPackage> questionPackages = new ArrayList<>();

    public void addQuestionMapping(QuestionPackage questionPackage) {
        this.questionPackages.add(questionPackage);
        questionPackage.mappingToPackage(this);
    }

    public void changeSubject(String subject) {
        this.subject = subject;
    }

}
