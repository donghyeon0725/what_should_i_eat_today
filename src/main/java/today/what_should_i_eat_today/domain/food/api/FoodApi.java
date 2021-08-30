package today.what_should_i_eat_today.domain.food.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import today.what_should_i_eat_today.domain.food.application.FoodCategoryService;
import today.what_should_i_eat_today.domain.food.entity.Food;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class FoodApi {

    private final FoodCategoryService foodCategoryService;

    @GetMapping("/foods/categories/{id}")
    public ResponseEntity<?> getFoodsInCategory(@PageableDefault Pageable pageable, @PathVariable("id") Long categoryId) {

        Page<Food> foods = foodCategoryService.getFoods(categoryId, pageable);

        return ResponseEntity.ok(foods);
    }
}
