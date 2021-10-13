package today.what_should_i_eat_today.domain.category.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import today.what_should_i_eat_today.domain.category.application.CategoryService;
import today.what_should_i_eat_today.domain.category.dto.CategoryRequestDto;
import today.what_should_i_eat_today.domain.category.dto.CategoryResponseDto;
import today.what_should_i_eat_today.domain.category.dto.CategoryVisibleUpdateRequestDto;
import today.what_should_i_eat_today.domain.category.entity.Category;
import today.what_should_i_eat_today.global.security.CurrentUser;
import today.what_should_i_eat_today.global.security.UserPrincipal;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CategoryApi {

    private final CategoryService categoryService;

    @PostMapping("categories")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> create(@CurrentUser UserPrincipal principal, @RequestBody CategoryRequestDto requestDto) {

        // todo : 2021.09.24 admin id 를 가져와야 함 -> 2021.09.26 해결

        categoryService.createCategory(requestDto.toCommand(principal.getId()));

        // todo : 2021.09.28 ResponseEntity.create() 로 변경필요
        return ResponseEntity.ok("ok");
    }

    @GetMapping("categories/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> getCategory(@PathVariable("id") Long categoryId) {

        Category category = categoryService.findById(categoryId);

        CategoryResponseDto responseDto = CategoryResponseDto.from(category);

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("categories")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> getCategories(
            @PageableDefault @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(name = "title", required = false, defaultValue = "") String categoryName
    ) {

        // todo : 2021.09.24 domain 을 바로 dto 로 넘겨주는 것은 안좋아보임 (pageable 을 사용할 때 dto 처리는 어떻게하는 지) -> 2021.09.25 해결

        Page<Category> categoryList = categoryService.getCategoryList(categoryName, pageable);

        Page<CategoryResponseDto> categoriesDto = categoryList.map(CategoryResponseDto::new);

        return ResponseEntity.ok(categoriesDto);
    }

    @PutMapping("categories/visible")
    @Secured("ROLE_ADMIN")
    public void updateVisibleOfCategory(@CurrentUser UserPrincipal principal, @RequestBody CategoryVisibleUpdateRequestDto dto) {
        categoryService.updateVisibleOfCategory(dto.toCommand(principal.getId()));
    }

    @PutMapping("categories/{id}")
    @Secured("ROLE_ADMIN")
    public void updateCategory(
            @CurrentUser UserPrincipal principal,
            @PathVariable("id") Long categoryId,
            @RequestBody CategoryRequestDto requestDto) {

        categoryService.updateCategory(requestDto.toCommand(principal.getId(), categoryId));
    }

    @PutMapping("categories/foodCategory-mapping/{id}")
    public void updateFoodCategoryMapping(
            @PathVariable("id") Long categoryId,
            @RequestParam(name = "foodId") Long foodId,
            @RequestParam(name = "check") Boolean check
    ) {
        categoryService.updateFoodCategoryMapping(categoryId, foodId, check);

    }

    @DeleteMapping("categories")
    @Secured("ROLE_ADMIN")
    public void deleteAllById(@RequestBody List<Long> categoryIds) {
        categoryService.deleteAllById(categoryIds);
    }
}
