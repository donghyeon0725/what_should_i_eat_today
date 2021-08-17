package today.what_should_i_eat_today.domain.world_cup.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.world_cup.dto.PackageRequest;
import today.what_should_i_eat_today.domain.world_cup.entity.Course;
import today.what_should_i_eat_today.domain.world_cup.entity.Package;
import today.what_should_i_eat_today.domain.world_cup.entity.PackageCourse;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CourseServiceTest {
    @Autowired
    private EntityManager em;

    @Autowired
    private CourseService courseService;


    @Test
    @DisplayName("묶음으로 과정 조회하기")
    void test1() {
        Course course1 = Course.builder().subject("과정1").build();
        Course course2 = Course.builder().subject("과정2").build();
        Course course3 = Course.builder().subject("과정3").build();
        Course course4 = Course.builder().subject("과정4").build();
        Course course5 = Course.builder().subject("과정5").build();

        // 패키지를 과정에 set 해주는 방식으로 하기 때문에, 과정에서 PackageCoruse 연관관계편의 메서드가 있어야 한다.
        Package packages = Package.builder().subject("패키지").build();

        // 패키지 코스는 매핑할 코스 1개당 1개를 생성해야 한다.
        PackageCourse packageCourse1 = PackageCourse.builder().packages(packages).build();
        PackageCourse packageCourse2 = PackageCourse.builder().packages(packages).build();
        PackageCourse packageCourse3 = PackageCourse.builder().packages(packages).build();

        course2.addPackageMapping(packageCourse1);
        course4.addPackageMapping(packageCourse2);
        course5.addPackageMapping(packageCourse3);

        em.persist(course1);
        em.persist(course2);
        em.persist(course3);
        em.persist(course4);
        em.persist(course5);
        em.persist(packages);

        em.flush();
        em.clear();

        PackageRequest packageRequest = new PackageRequest();
        packageRequest.setPackageId(packages.getId());
        PageRequest paging = PageRequest.of(0, 10);

        Page<Course> courseListByPackage = courseService.getCourseListByPackage(packageRequest, paging);

        assertThat(courseListByPackage).extracting("subject").containsExactly("과정2", "과정4", "과정5").withFailMessage("패키지를 통해서 과정을 찾을 수 있어야 한다.");

    }

}
