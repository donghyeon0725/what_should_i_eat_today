package today.what_should_i_eat_today.domain.food.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.food.entity.Food;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class FoodRepositoryTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private FoodRepository foodRepository;

    @Test
    @DisplayName("음식을 랜덤으로 가져온다")
    void test1() {

        for (int i = 0; i < 100; i++) {
            Food food = Food.builder().name("food" + i).build();
            em.persist(food);
        }

        em.clear();

        List<Food> foodsRand = foodRepository.findFoodsRand(10);

//        foodsRand.forEach(food -> System.out.println(food.getName()));

        assertThat(foodsRand).hasSize(10);

    }
}