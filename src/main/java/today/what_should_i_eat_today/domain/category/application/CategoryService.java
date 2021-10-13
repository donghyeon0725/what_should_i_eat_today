package today.what_should_i_eat_today.domain.category.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.admin.dao.AdminRepository;
import today.what_should_i_eat_today.domain.admin.entity.Admin;
import today.what_should_i_eat_today.domain.category.dao.CategoryRepository;
import today.what_should_i_eat_today.domain.category.dto.CategoryAdminCommand;
import today.what_should_i_eat_today.domain.category.dto.CategoryUserCommand;
import today.what_should_i_eat_today.domain.category.entity.Category;
import today.what_should_i_eat_today.domain.category.entity.CategoryValidator;
import today.what_should_i_eat_today.domain.category.entity.FoodCategory;
import today.what_should_i_eat_today.domain.food.entity.Food;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.ResourceDuplicatedException;
import today.what_should_i_eat_today.global.error.exception.ResourceNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final AdminRepository adminRepository;

    private final CategoryRepository categoryRepository;

    private final CategoryValidator categoryValidator;

    @Transactional
    public Long createCategory(CategoryAdminCommand command) {
        command.createValidate();
        boolean isVisible = command.getVisible() == null ? true : command.getVisible();

        Admin admin = adminRepository.findById(command.getAdminId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        Category category = Category.builder().name(command.getName()).description(command.getDescription()).visible(isVisible).admin(admin).build();

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

    public Page<Category> getCategoryList(String categoryName, Pageable pageable) {
        return categoryRepository.findAllByNameContains(categoryName, pageable);
    }

    @Transactional
    public void deleteAllById(List<Long> categoryIds) {
        categoryRepository.deleteAllById(categoryIds);

        // todo : 2021.10.01 - food 이랑 연관되어있으면 오류발생 ( FoodCategory 부터 제거해주어야 함, 직접 쿼리문을 주어서 JPA의 CASCADE.ALL 을 적용하지 못함 )
//        categoryRepository.deleteAllByIdInQuery(categoryIds);
    }

    @Transactional
    public void updateVisibleOfCategory(CategoryAdminCommand command) {

        Category category = categoryRepository.findById(command.getId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        category.changeVisible(command.getVisible());
    }

    @Transactional
    public void updateFoodCategoryMapping(Long categoryId, Long foodId, Boolean check) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        // 포함
        if (check) {
            category.addFoodMapping(FoodCategory.builder().category(Category.builder().id(categoryId).build()).food(Food.builder().id(foodId).build()).build());
            // 제거
        } else {
            category.removeFoodMapping(categoryId, foodId);
        }

    }
}
