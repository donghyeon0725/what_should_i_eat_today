package today.what_should_i_eat_today.domain.world_cup.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.model.Status;
import today.what_should_i_eat_today.domain.world_cup.dto.CourseRequest;
import today.what_should_i_eat_today.domain.world_cup.dto.PackageRequest;
import today.what_should_i_eat_today.domain.world_cup.entity.Course;
import today.what_should_i_eat_today.domain.world_cup.entity.Package;
import today.what_should_i_eat_today.domain.world_cup.entity.PackageCourse;
import today.what_should_i_eat_today.global.error.exception.ResourceNotFoundException;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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


    @Test
    @DisplayName("과정 생성하기")
    void test2() {
        // given
        CourseRequest courseRequest = CourseRequest.builder().subject("과정").build();
        Long courseId = courseService.createCourse(courseRequest);

        // when
        Course course = em.find(Course.class, courseId);

        // then
        assertEquals(courseId, course.getId(), () -> "과정을 정상 생성할 수 있어야 한다.");
    }


    @Test
    @DisplayName("과정 리스트 조회하기")
    void test3() {

        // given
        for (int i=0; i<20; i++) {
            Course course = Course.builder().subject("과정" + i).status(Status.USE).build();
            em.persist(course);
        }

        CourseRequest courseRequest1 = CourseRequest.builder().subject("과정").build();
        CourseRequest courseRequest2 = CourseRequest.builder().subject("과정19").build();
        CourseRequest courseRequest3 = CourseRequest.builder().subject(null).build();
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<Course> courseList1 = courseService.getCourseList(courseRequest1, pageable);
        Page<Course> courseList2 = courseService.getCourseList(courseRequest2, pageable);
        Page<Course> courseList3 = courseService.getCourseList(courseRequest3, pageable);

        // then
        assertEquals(10, courseList1.getContent().size(), () -> "10건이 정확하게 검색 되어야 한다.");
        assertEquals(1, courseList2.getContent().size(), () -> "1건이 정확하게 검색 되어야 한다.");
        assertEquals("과정19", courseList2.getContent().get(0).getSubject(), () -> "1건이 정확하게 검색 되어야 한다.");
        assertEquals(10, courseList3.getContent().size(), () -> "10건이 정확하게 검색되어야 한다");
    }

    @Test
    @DisplayName("과정 수정")
    void test4() {
        // given
        Course course1 = Course.builder().subject("과정").status(Status.USE).build();
        Course course2 = Course.builder().subject("과정").status(Status.USE).build();

        em.persist(course1);
        em.persist(course2);
        em.flush();
        em.clear();

        CourseRequest courseRequest1 = CourseRequest.builder().courseId(course1.getId()).subject("수정합니다.").build();
        CourseRequest courseRequest2 = CourseRequest.builder().courseId(course2.getId()).subject("수정합니다.").status(Status.HIDE).build();

        // when
        Long request1 = courseService.updateCourse(courseRequest1);
        Long request2 = courseService.updateCourse(courseRequest2);

        Course findCourse1 = em.find(Course.class, request1);
        Course findCourse2 = em.find(Course.class, request2);

        assertEquals("수정합니다.", findCourse1.getSubject(), () -> "주제가 정확하게 수정 되어야 합니다.");
        assertEquals(Status.HIDE, findCourse2.getStatus(), () -> "상태가 정확하게 수정 되어야 합니다.");
    }


    @Test
    @DisplayName("과정 구성하기 : 추가")
    void test5() {

        // given
        Course course = Course.builder().subject("과정1").status(Status.USE).build();
//        Course course2 = Course.builder().subject("과정2").status(Status.USE).build();

        Package packages1 = Package.builder().subject("패키지1").build();
        Package packages2 = Package.builder().subject("패키지2").build();
        Package packages3 = Package.builder().subject("패키지3").build();
        Package packages4 = Package.builder().subject("패키지4").build();

        course.addPackageMapping(PackageCourse.builder().packages(packages1).build());
        course.addPackageMapping(PackageCourse.builder().packages(packages2).build());

        em.persist(packages1);
        em.persist(packages2);
        em.persist(packages3);
        em.persist(packages4);
        em.persist(course);

        em.flush();
        em.clear();

        CourseRequest courseRequest1 = CourseRequest.builder().courseId(course.getId()).packageId(packages3.getId()).build();
        CourseRequest courseRequest2 = CourseRequest.builder().courseId(course.getId()).packageId(packages4.getId()).build();

        // when
        courseService.addPackageToCourse(courseRequest1);
        courseService.addPackageToCourse(courseRequest2);

        Course findCourse = em.find(Course.class, course.getId());

        assertEquals(4, findCourse.getPackageCourses().size(), "과정에 패키지를 추가할 수 있어야 한다.");
        assertThat(findCourse.getPackageCourses())
                .extracting("packages")
                .extracting("id")
                .containsExactly(packages1.getId(), packages2.getId(), packages3.getId(), packages4.getId())
                .withFailMessage("과정에 패키지를 추가할 수 있어야 한다.");
    }


    @Test
    @DisplayName("과정 구성하기 : 삭제")
    void test6() {

        // given
        Course course = Course.builder().subject("과정1").status(Status.USE).build();

        Package packages1 = Package.builder().subject("패키지1").build();
        Package packages2 = Package.builder().subject("패키지2").build();
        Package packages3 = Package.builder().subject("패키지3").build();
        Package packages4 = Package.builder().subject("패키지4").build();
        Package packages5 = Package.builder().subject("패키지5").build();

        course.addPackageMapping(PackageCourse.builder().packages(packages1).build());
        course.addPackageMapping(PackageCourse.builder().packages(packages2).build());
        course.addPackageMapping(PackageCourse.builder().packages(packages3).build());
        course.addPackageMapping(PackageCourse.builder().packages(packages4).build());

        em.persist(packages1);
        em.persist(packages2);
        em.persist(packages3);
        em.persist(packages4);
        em.persist(packages5);
        em.persist(course);

        em.flush();
        em.clear();

        CourseRequest courseRequest1 = CourseRequest.builder().courseId(course.getId()).packageId(packages3.getId()).build();
        CourseRequest courseRequest2 = CourseRequest.builder().courseId(course.getId()).packageId(packages4.getId()).build();
        CourseRequest errorRequest = CourseRequest.builder().courseId(course.getId()).packageId(packages5.getId()).build();

        // when
        courseService.removePackageFromCourse(courseRequest1);
        courseService.removePackageFromCourse(courseRequest2);

        Course findCourse = em.find(Course.class, course.getId());

        assertEquals(2, findCourse.getPackageCourses().size(), "과정에 패키지를 제거할 수 있어야 한다.");
        assertThat(findCourse.getPackageCourses())
                .extracting("packages")
                .extracting("id")
                .containsExactly(packages1.getId(), packages2.getId())
                .withFailMessage("과정에 패키지를 제거할 수 있어야 한다.");
        assertThrows(ResourceNotFoundException.class, () -> courseService.removePackageFromCourse(errorRequest), () -> "매핑이 없는 패키지는 제거할 수 없다.");
    }
}
