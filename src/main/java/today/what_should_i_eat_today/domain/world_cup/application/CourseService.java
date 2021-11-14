package today.what_should_i_eat_today.domain.world_cup.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.model.Status;
import today.what_should_i_eat_today.domain.world_cup.dao.CourseRepository;
import today.what_should_i_eat_today.domain.world_cup.dao.PackageCourseRepository;
import today.what_should_i_eat_today.domain.world_cup.dao.PackageRepository;
import today.what_should_i_eat_today.domain.world_cup.dto.PackageRequest;
import today.what_should_i_eat_today.domain.world_cup.entity.Course;
import today.what_should_i_eat_today.domain.world_cup.dto.CourseRequest;
import today.what_should_i_eat_today.domain.world_cup.entity.CourseValidator;
import today.what_should_i_eat_today.domain.world_cup.entity.Package;
import today.what_should_i_eat_today.domain.world_cup.entity.PackageCourse;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {

    private final PackageRepository packageRepository;

    private final CourseRepository courseRepository;

    private final CourseValidator courseValidator;

    private final PackageCourseRepository packageCourseRepository;

    public Page<Course> getCourseListByPackage(PackageRequest packageRequest, Pageable pageable) {

        Package packages = packageRepository.findById(packageRequest.getPackageId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        return courseRepository.findByPackage(packages, pageable);
    }

    public Course findById(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));
    }

    public Page<Course> getCourseList(CourseRequest courseRequest, Pageable pageable) {
        return courseRepository.findBySubject(courseRequest.getSubject(), pageable);
    }

    @Transactional
    public Long createCourse(CourseRequest courseRequest) {
        Course course = Course.builder().subject(courseRequest.getSubject()).status(Status.HIDE).build();

        courseRepository.save(course);

        return course.getId();
    }

    @Transactional
    public Long updateCourse(CourseRequest courseRequest) {
        Course findCourse = courseRepository.findById(courseRequest.getCourseId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        findCourse.changeStatus(courseRequest.getStatus(), courseValidator);
        findCourse.changeSubject(courseRequest.getSubject());

        return courseRequest.getCourseId();
    }

    @Transactional
    public void addPackageToCourse(CourseRequest courseRequest) {
        Course findCourse = courseRepository.findById(courseRequest.getCourseId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        Package findPackage = packageRepository.findById(courseRequest.getPackageId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        findCourse.addPackageMapping(PackageCourse.builder().packages(findPackage).build());
    }

    @Transactional
    public void removePackageFromCourse(CourseRequest courseRequest) {
        Course findCourse = courseRepository.findById(courseRequest.getCourseId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        Package findPackage = packageRepository.findById(courseRequest.getPackageId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        PackageCourse findPackageCourse = packageCourseRepository.findByCourseAndPackages(findCourse, findPackage).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        findCourse.getPackageCourses().remove(findPackageCourse);
    }





}
