package today.what_should_i_eat_today.domain.tag.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import today.what_should_i_eat_today.domain.admin.entity.Admin;
import today.what_should_i_eat_today.domain.tag.dao.TagRepository;
import today.what_should_i_eat_today.domain.tag.exception.InvalidTagException;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.invalid.ResourceConflictException;
import today.what_should_i_eat_today.global.error.exception.invalid.UnauthorizedUserException;

@Component
@RequiredArgsConstructor
public class TagValidator {
    private final TagRepository tagRepository;

    public void validateTagForChange(Tag tag) {
        if (tag == null)
            throw new InvalidTagException(ErrorCode.INVALID_INPUT_VALUE);

        if (!StringUtils.hasText(tag.getName()))
            throw new InvalidTagException(ErrorCode.INVALID_INPUT_VALUE);
    }

    public void validateForCreate(Tag tag) {
        if (tagRepository.existsByName(tag.getName()))
            throw new ResourceConflictException(ErrorCode.ALREADY_EXIST_RESOURCE);

        if (tag == null)
            throw new InvalidTagException(ErrorCode.INVALID_INPUT_VALUE);

        if (!StringUtils.hasText(tag.getName()))
            throw new InvalidTagException(ErrorCode.INVALID_INPUT_VALUE);
    }


    public void validateForDelete(Admin admin) {
        if (admin == null)
            throw new UnauthorizedUserException(ErrorCode.UNAUTHORIZED_USER);
    }
}
