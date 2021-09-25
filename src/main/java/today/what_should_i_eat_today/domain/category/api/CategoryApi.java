package today.what_should_i_eat_today.domain.category.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import today.what_should_i_eat_today.domain.category.application.CategoryService;
import today.what_should_i_eat_today.domain.category.dto.CategoryRequestDto;
import today.what_should_i_eat_today.domain.category.entity.Category;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CategoryApi {

    private final CategoryService categoryService;

    @PostMapping("categories")
    public ResponseEntity<?> create(@RequestBody CategoryRequestDto requestDto) {

        // todo : 2021.09.24 admin id 를 가져와야 함

        categoryService.createCategory(requestDto.toCommand());

        return ResponseEntity.ok("ok");
    }

    @GetMapping("categories")
    public ResponseEntity<?> getCategories(@PageableDefault Pageable pageable) {

        System.err.println(pageable.getPageNumber());
        System.err.println(pageable.getPageSize());

        // todo : 2021.09.24 domain 을 바로 dto 로 넘겨주는 것은 안좋아보임 (pageable 을 사용할 때 dto 처리는 어떻게하는 지)

        Page<Category> categoryList = categoryService.getCategoryList(pageable);

        return ResponseEntity.ok(categoryList);
    }

}
