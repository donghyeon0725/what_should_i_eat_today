package today.what_should_i_eat_today.domain.food.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import today.what_should_i_eat_today.domain.food.application.FoodCategoryService;
import today.what_should_i_eat_today.domain.food.application.FoodService;
import today.what_should_i_eat_today.domain.food.dto.FoodDto;
import today.what_should_i_eat_today.domain.food.dto.FoodResponseDto;
import today.what_should_i_eat_today.domain.food.entity.Food;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class FoodApi {

    private final FoodCategoryService foodCategoryService;

    private final FoodService foodService;

    @GetMapping("/foods/categories/{id}")
    public ResponseEntity<?> getFoodsInCategory(@PageableDefault Pageable pageable, @PathVariable("id") Long categoryId) {

        Page<Food> foods = foodCategoryService.getFoods(categoryId, pageable);

        return ResponseEntity.ok(foods);
    }

    @GetMapping("/foods/categories")
    public ResponseEntity getFoods(@PageableDefault Pageable pageable, @RequestBody FoodDto foodDto) {
        Page<Food> foods = foodService.getFoodList(foodDto, pageable);

        return ResponseEntity.ok(foods.map(s -> new FoodResponseDto(s)));
    }
}
