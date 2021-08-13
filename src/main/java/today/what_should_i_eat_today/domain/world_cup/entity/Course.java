package today.what_should_i_eat_today.domain.world_cup.entity;

import lombok.*;
import today.what_should_i_eat_today.global.common.entity.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * PackageCourse 에 대한 생명주기는 Course 에서 관리
 * */
@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Course extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;

    @Builder.Default
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PackageCourse> packageCourses = new ArrayList<>();


    public void addPackageMapping(PackageCourse packageCourse) {
        this.packageCourses.add(packageCourse);
        packageCourse.mappingToCourse(this);
    }

}
