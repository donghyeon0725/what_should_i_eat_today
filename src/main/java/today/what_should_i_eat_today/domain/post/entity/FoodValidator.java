package today.what_should_i_eat_today.domain.post.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import today.what_should_i_eat_today.domain.tag.dao.TagRepository;
import today.what_should_i_eat_today.domain.tag.dto.TagRequest;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FoodValidator {

    private final TagRepository tagRepository;

    public void validateTags(List<Long> tagIds) {
        final long tags = tagRepository.countByIdIn(tagIds);

        if (tags != tagIds.size())
            throw new IllegalArgumentException();
    }
}
