package today.what_should_i_eat_today.domain.tag.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import today.what_should_i_eat_today.domain.tag.dao.TagRepository;
import today.what_should_i_eat_today.domain.tag.exception.InvalidTagException;
import today.what_should_i_eat_today.global.error.ErrorCode;

@Component
@RequiredArgsConstructor
public class TagValidator {
    private final TagRepository tagRepository;

    public void validateTag(Tag tag) {
        if (tag == null)
            throw new InvalidTagException(ErrorCode.INVALID_INPUT_VALUE);

        if (!StringUtils.hasText(tag.getName()))
            throw new InvalidTagException(ErrorCode.INVALID_INPUT_VALUE);
    }
}
