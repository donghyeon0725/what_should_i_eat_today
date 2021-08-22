package today.what_should_i_eat_today.domain.world_cup.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.world_cup.dao.PackageCourseRepository;
import today.what_should_i_eat_today.domain.world_cup.dao.QuestionPackageRepository;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.CannotExecuteException;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PackageValidator {
    private final PackageCourseRepository packageCourseRepository;

    public void validateForDelete(Package packages) {
        if (packageCourseRepository.existsCourseByPackages(packages))
            throw new CannotExecuteException(ErrorCode.INVALID_INPUT_VALUE);
    }
}
