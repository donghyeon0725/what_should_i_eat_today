package today.what_should_i_eat_today.domain.world_cup.entity;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.model.Status;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.InvalidStatusException;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseValidator {

    public void validateForStatusChange(Status status) {
        if (!status.equals(Status.USE) && !status.equals(Status.HIDE))
            throw new InvalidStatusException(ErrorCode.INVALID_INPUT_VALUE);
    }
}
