package today.what_should_i_eat_today.domain.world_cup.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.world_cup.dao.CourseRepository;
import today.what_should_i_eat_today.domain.world_cup.dao.PackageRepository;
import today.what_should_i_eat_today.domain.world_cup.dto.PackageRequest;
import today.what_should_i_eat_today.domain.world_cup.entity.Course;
import today.what_should_i_eat_today.domain.world_cup.entity.Package;
import today.what_should_i_eat_today.domain.world_cup.entity.Question;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {

    private final PackageRepository packageRepository;

    private final CourseRepository courseRepository;

    public Page<Course> getCourseListByPackage(PackageRequest packageRequest, Pageable pageable) {

        Package packages = packageRepository.findById(packageRequest.getPackageId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        return courseRepository.findByPackage(packages, pageable);
    }

    public Course findById(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));
    }


}
