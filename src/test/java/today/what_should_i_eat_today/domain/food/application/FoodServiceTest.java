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
import today.what_should_i_eat_today.domain.food.entity.Food;
import today.what_should_i_eat_today.domain.food.entity.FoodStatus;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class FoodServiceTest {

    @Autowired
    private FoodService foodService;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("음식 카테고리로 조회 테스트")
    void test1() {

        Category category = Category.builder().name("카테고리").build();


        for (int i=0; i<30; i++) {
            Food food = Food.builder().name("음식" + i).status(FoodStatus.USE).build();
            FoodCategory foodCategory = FoodCategory.builder().food(food).build();
            category.addFoodMapping(foodCategory);
            em.persist(food);
        }

        em.persist(category);
        em.flush();
        em.clear();

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<Food> foodListByCategory = foodService.getFoodListByCategory(category.getId(), pageRequest);

        System.out.println(foodListByCategory.getContent().size());
        for (Food food : foodListByCategory.getContent())
            System.out.println("food = " + food.getName());
    }

    @Test
    @DisplayName("월드컵 조회")
    void test2() {
        for (int i = 0; i < 50; i++) {
            Food food = Food.builder().name("food" + i).build();
            em.persist(food);
        }
        em.clear();

        List<Food> randomFoods = foodService.getRandomFood(16);

        assertThat(randomFoods).hasSize(16);
    }
}
