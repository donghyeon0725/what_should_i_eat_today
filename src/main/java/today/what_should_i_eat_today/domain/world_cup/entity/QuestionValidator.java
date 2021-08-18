package today.what_should_i_eat_today.domain.world_cup.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.world_cup.dao.QuestionPackageRepository;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.CannotExecuteException;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionValidator {
    private final QuestionPackageRepository questionPackageRepository;

    public void validateForDelete(Question question) {
        if (questionPackageRepository.existsByQuestion(question))
            throw new CannotExecuteException(ErrorCode.INVALID_INPUT_VALUE);
    }
}
