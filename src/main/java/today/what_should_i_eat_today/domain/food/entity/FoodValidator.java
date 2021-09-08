package today.what_should_i_eat_today.domain.food.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import today.what_should_i_eat_today.domain.category.dao.CategoryRepository;
import today.what_should_i_eat_today.domain.category.dao.FoodCategoryRepository;
import today.what_should_i_eat_today.domain.food.dao.FoodRepository;
import today.what_should_i_eat_today.domain.tag.dao.TagRepository;
import today.what_should_i_eat_today.domain.tag.dto.TagRequest;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.CannotExecuteException;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FoodValidator {

    private final TagRepository tagRepository;

    private final FoodRepository foodRepository;

    private final FoodCategoryRepository foodCategoryRepository;

    public void validateTags(List<Long> tagIds) {
        final long tags = tagRepository.countByIdIn(tagIds);

        if (tags != tagIds.size())
            throw new IllegalArgumentException();
    }


    public void validateFoodForCreate(Food food) {
        if (!foodCategoryRepository.existsByFood(food))
            throw new CannotExecuteException(ErrorCode.INVALID_INPUT_VALUE);

        if (food.getFoodTags().size() < 1)
            throw new CannotExecuteException(ErrorCode.INVALID_INPUT_VALUE);
    }

    public void validateName(Food food, String name) {
        if (foodRepository.existsByName(food.getName(), name))
            throw new CannotExecuteException(ErrorCode.RESOURCE_CONFLICT);
    }

    public void validateName(String name) {
        if (foodRepository.existsByName(name))
            throw new CannotExecuteException(ErrorCode.RESOURCE_CONFLICT);
    }
}
