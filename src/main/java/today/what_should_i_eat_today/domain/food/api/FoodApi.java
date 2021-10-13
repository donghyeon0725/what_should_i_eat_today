package today.what_should_i_eat_today.domain.food.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import today.what_should_i_eat_today.domain.food.application.FoodCategoryService;
import today.what_should_i_eat_today.domain.food.application.FoodService;
import today.what_should_i_eat_today.domain.food.dto.FoodDto;
import today.what_should_i_eat_today.domain.food.dto.FoodResponseDto;
import today.what_should_i_eat_today.domain.food.dto.FoodWithTagsAndCountryResponseDto;
import today.what_should_i_eat_today.domain.food.entity.Food;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class FoodApi {

    private final FoodCategoryService foodCategoryService;

    private final FoodService foodService;

    /**
     * 태그 국가가 포함된 음식 목록 가져오기
     */
    @GetMapping("/foods/tags-country")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> getFoodListWithTagsAndCountry(
            @PageableDefault @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable page,
            @RequestParam(value = "country", required = false, defaultValue = "") String country,
            @RequestParam(value = "tag", required = false, defaultValue = "") String tag,
            @RequestParam(value = "search", required = false, defaultValue = "") String search,
            @RequestParam(value = "categoryId", required = true) Long categoryId
    ) {

        Page<FoodWithTagsAndCountryResponseDto> foods = foodService.getFoodListWithTagsAndCountry(country, tag, search, page, categoryId);

        return ResponseEntity.ok(foods);
    }

    @GetMapping("/foods/categories/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> getFoodsByCategory(@PageableDefault Pageable pageable, @PathVariable("id") Long categoryId) {

        Page<FoodResponseDto> foodsByCategoryPage = foodCategoryService.getFoodsByCategory(categoryId, pageable);

        return ResponseEntity.ok(foodsByCategoryPage);
    }

    @GetMapping("/foods/categories")
    @Secured("ROLE_ADMIN")
    public ResponseEntity getFoods(@PageableDefault Pageable pageable, @RequestBody FoodDto foodDto) {
        Page<Food> foods = foodService.getFoodList(foodDto, pageable);

        return ResponseEntity.ok(foods.map(s -> new FoodResponseDto(s)));
    }
}
