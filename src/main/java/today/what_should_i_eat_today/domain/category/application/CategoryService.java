package today.what_should_i_eat_today.domain.category.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.category.dao.CategoryRepository;
import today.what_should_i_eat_today.domain.category.dto.CategoryAdminCommand;
import today.what_should_i_eat_today.domain.category.dto.CategoryUserCommand;
import today.what_should_i_eat_today.domain.category.entity.Category;
import today.what_should_i_eat_today.domain.category.entity.CategoryValidator;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.ResourceDuplicatedException;
import today.what_should_i_eat_today.global.error.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryValidator categoryValidator;

    @Transactional
    public Long createCategory(CategoryAdminCommand command) {
        command.createValidate();
        boolean isVisible = command.getVisible() == null ? true : command.getVisible();

        Category category = Category.builder().name(command.getName()).description(command.getDescription()).visible(isVisible).build();

        boolean isExisted = categoryRepository.existsByName(command.getName());

        // 이미 존재하는 카테고리명인 경우 예외발생
        if (isExisted)
            throw new ResourceDuplicatedException("category", "name", command.getName());

        categoryRepository.save(category);

        return category.getId();
    }


    public Page<Category> getCategoryList(CategoryAdminCommand command, Pageable pageable) {
        return categoryRepository.findCategoryByName(command.getName(), pageable);
    }


    public Page<Category> getCategoryList(CategoryUserCommand command, Pageable pageable) {
        return categoryRepository.findVisibleCategoryByName(command.getName(), pageable);
    }

    @Transactional
    public void updateCategory(CategoryAdminCommand command) {
        command.updateValidate();

        Category category = categoryRepository.findById(command.getId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        category.changeName(command.getName(), categoryValidator);
        category.changeDescription(command.getDescription());
        category.changeVisible(command.getVisible());
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    public Page<Category> getCategoryList(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }
}
