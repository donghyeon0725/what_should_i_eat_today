package today.what_should_i_eat_today.domain.world_cup.api;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import today.what_should_i_eat_today.domain.food.application.FoodService;
import today.what_should_i_eat_today.domain.food.dto.FoodResponseDto;
import today.what_should_i_eat_today.domain.food.entity.Food;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class WorldCupApi {

    private final FoodService foodService;

    @GetMapping("/world-cup")
    private ResponseEntity<?> getRandomFood(
            @RequestParam(name = "count", defaultValue = "16")
            @Valid @Pattern(regexp = "^16$|^32$|^64$|^128$", message = "해당 '${validatedValue}'강은 지원하지 않습니다")
                    Integer count) {

        List<Food> randomFoods = foodService.getRandomFood(count);

        return ResponseEntity.ok(FoodResponseDto.from(randomFoods));
    }
}
