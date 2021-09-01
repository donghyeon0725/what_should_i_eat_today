package today.what_should_i_eat_today.domain.category.dao;

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

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class FoodCategoryDslRepositoryImplTest {

    @Autowired
    private FoodCategoryRepository foodCategoryRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("페이징 테스트")
    void test1() {

        Category category1 = Category.builder().name("카테고리1").build();
        Category category2 = Category.builder().name("카테고리2").build();

        Food food1 = Food.builder().name("김치 볶음밥").build();
        Food food2 = Food.builder().name("김치 볶음밥").build();
        Food food3 = Food.builder().name("김치 볶음밥2").build();

        FoodCategory fc1 = FoodCategory.builder().food(food1).build();
        FoodCategory fc3 = FoodCategory.builder().food(food3).build();
        category1.addFoodMapping(fc1);
        category1.addFoodMapping(fc3);

        FoodCategory fc2 = FoodCategory.builder().food(food2).build();
        category2.addFoodMapping(fc2);

        em.persist(food1);
        em.persist(food2);
        em.persist(food3);

        em.persist(category1);
        em.persist(category2);

        em.clear();

        Page<Food> foods = foodCategoryRepository.findFoodsByCategory(category1.getId(), PageRequest.of(0, 10));

        foods.getContent().forEach(System.err::println);

        assertThat(foods.getContent()).hasSize(2);

    }
}