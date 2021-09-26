package today.what_should_i_eat_today.domain.category.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import today.what_should_i_eat_today.domain.category.application.CategoryService;
import today.what_should_i_eat_today.domain.category.dto.CategoryRequestDto;
import today.what_should_i_eat_today.domain.category.dto.CategoryResponseDto;
import today.what_should_i_eat_today.domain.category.entity.Category;
import today.what_should_i_eat_today.global.security.CurrentUser;
import today.what_should_i_eat_today.global.security.UserPrincipal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CategoryApi {

    private final CategoryService categoryService;

    @PostMapping("categories")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> create(@CurrentUser UserPrincipal principal, @RequestBody CategoryRequestDto requestDto) {

        // todo : 2021.09.24 admin id 를 가져와야 함 -> 2021.09.26 해결

        System.err.println(principal.getId());
        System.err.println(principal.getEmail());

        categoryService.createCategory(requestDto.toCommand());

        return ResponseEntity.ok("ok");
    }

    @GetMapping("categories")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> getCategories(@PageableDefault Pageable pageable, @RequestParam(required = false) String title) {

        // todo : 2021.09.24 domain 을 바로 dto 로 넘겨주는 것은 안좋아보임 (pageable 을 사용할 때 dto 처리는 어떻게하는 지) -> 2021.09.25 해결

        Page<Category> categoryList = categoryService.getCategoryList(pageable);

        Page<CategoryResponseDto> categoriesDto = categoryList.map(CategoryResponseDto::new);

        return ResponseEntity.ok(categoriesDto);
    }

}
