package today.what_should_i_eat_today.domain.food.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.category.dao.FoodCategoryRepository;
import today.what_should_i_eat_today.domain.category.dto.CategoryResponseDto;
import today.what_should_i_eat_today.domain.category.entity.FoodCategory;
import today.what_should_i_eat_today.domain.food.dao.FoodTagRepository;
import today.what_should_i_eat_today.domain.food.dto.FoodResponseDto;
import today.what_should_i_eat_today.domain.food.entity.Food;
import today.what_should_i_eat_today.domain.food.entity.FoodTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodCategoryService {

    private final FoodCategoryRepository foodCategoryRepository;

    private final FoodTagRepository foodTagRepository;

    public Page<FoodResponseDto> getFoodsByCategory(Long categoryId, Pageable pageable) {

        // todo : 2021.08.30 코드 개선이 필요해보임
        // 카테고리에 해당하는 Food 을 조회하되, pageable 을 적용해야한다
        Page<FoodCategory> foodCategories = foodCategoryRepository.findAllByCategoryId(categoryId, pageable);

        List<Food> foods = foodCategories.getContent().stream().map(FoodCategory::getFood).collect(Collectors.toList());

        // todo : 2021.09.29 코드 개선 필요... 음식에 해당하는 카테고리들 가져오기
        List<CategoryResponseDto> categoryResponseDtos = new ArrayList<>();
        // 음식의 카테고리들
//        for (Food food : foods) {
//            List<FoodCategory> foodCategoriesByFood = food.getFoodCategories();
//            for (FoodCategory foodCategory : foodCategoriesByFood) {
////                System.err.println("food name ->" + foodCategory.getFood().getName() +  "category name" + foodCategory.getCategory().getName());
//                System.out.println("admin" + foodCategory.getCategory().getAdmin().getEmail());
//                categoryResponseDtos.add(CategoryResponseDto.from(foodCategory.getCategory()));
//
//            }
//        }

        List<Long> foodIds = foods.stream().map(Food::getId).collect(Collectors.toList());


        // todo : 2021.09.29 음식에 태그가 설정이 안된 경우에는 null 이 되어서 grouping 을 할 수 없게된다 (exception 발생)
        Map<Long, List<FoodTag>> collect = foodTagRepository.findByFoodIdIn(foodIds).stream().collect(Collectors.groupingBy(foodTag -> {
            return foodTag.getFood().getId();
        }));

        List<FoodResponseDto> foodResponseDtos = foods.stream().map(food -> {
            List<FoodTag> foodTags = collect.get(food.getId());
            FoodResponseDto dto = FoodResponseDto.from(food);
            dto.addAllFoodTags(foodTags);
            dto.setCategoryResponseDtos(categoryResponseDtos);
            return dto;
        }).collect(Collectors.toList());


        return new PageImpl<>(foodResponseDtos, pageable, foodCategories.getTotalPages());
    }
}
