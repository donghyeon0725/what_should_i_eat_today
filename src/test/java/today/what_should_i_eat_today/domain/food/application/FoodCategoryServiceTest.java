package today.what_should_i_eat_today.domain.food.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.category.entity.Category;
import today.what_should_i_eat_today.domain.category.entity.FoodCategory;
import today.what_should_i_eat_today.domain.food.dto.FoodResponseDto;
import today.what_should_i_eat_today.domain.food.entity.Food;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class FoodCategoryServiceTest {

    @Autowired
    private FoodCategoryService foodCategoryService;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("카테고리에 해당하는 음식들 조회")
    void test1() {
        // todo : 2021.08.30 왠지 나중에 문제 생길 것 같은 코드로 보임

        Category category1 = Category.builder()
                .name("밥")
                .build();
        em.persist(category1);

        Category category2 = Category.builder()
                .name("돈까스")
                .build();
        em.persist(category2);

        for (int i = 0; i < 50; i++) {
            Food food = Food.builder()
                    .name("food" + i)
                    .build();

            em.persist(food);

            if (i % 2 == 0) {
                FoodCategory foodCategory = FoodCategory.builder().food(food).build();
                foodCategory.addCategoryMapping(category1);
                em.persist(foodCategory);
            }

            if (i % 3 == 0) {
                FoodCategory foodCategory = FoodCategory.builder().food(food).build();
                foodCategory.addCategoryMapping(category2);
                em.persist(foodCategory);
            }
        }

        em.clear();

        PageRequest page = PageRequest.of(0, 10);
        Page<FoodResponseDto> foods = foodCategoryService.getFoodsByCategory(category1.getId(), page);

        assertThat(foods.getContent()).hasSize(10);
        assertThat(foods.getContent())
                .extracting("name")
                .containsExactly(
                        "food0", "food2", "food4", "food6", "food8",
                        "food10", "food12", "food14", "food16", "food18"
                );
    }

}
