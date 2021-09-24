package today.what_should_i_eat_today.domain.category.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import today.what_should_i_eat_today.domain.category.application.CategoryService;
import today.what_should_i_eat_today.domain.category.dto.CategoryRequestDto;

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
}
