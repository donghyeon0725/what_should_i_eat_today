package today.what_should_i_eat_today.domain.food.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.admin.entity.Admin;
import today.what_should_i_eat_today.domain.category.dao.FoodCategoryRepository;
import today.what_should_i_eat_today.domain.category.entity.Category;
import today.what_should_i_eat_today.domain.category.entity.FoodCategory;
import today.what_should_i_eat_today.domain.country.entity.Country;
import today.what_should_i_eat_today.domain.food.dao.FoodRepository;
import today.what_should_i_eat_today.domain.food.dto.FoodAdminCommand;
import today.what_should_i_eat_today.domain.food.dto.FoodCommand;
import today.what_should_i_eat_today.domain.food.dto.FoodMemberCommand;
import today.what_should_i_eat_today.domain.food.entity.Food;
import today.what_should_i_eat_today.domain.food.entity.FoodTag;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.member.entity.MemberStatus;
import today.what_should_i_eat_today.domain.member.mock.CustomMockUser;
import today.what_should_i_eat_today.domain.tag.entity.Tag;
import today.what_should_i_eat_today.domain.tag.entity.TagStatus;
import today.what_should_i_eat_today.global.error.exception.CannotExecuteException;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class FoodServiceTest {

    @Autowired
    private FoodService foodService;

    @Autowired
    private FoodCategoryRepository foodCategoryRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("음식 카테고리로 조회 테스트")
    void test1() {

        Category category = Category.builder().name("카테고리").build();


        for (int i=0; i<30; i++) {
            Food food = Food.builder().name("음식" + i).deleted(false).build();
            FoodCategory foodCategory = FoodCategory.builder().food(food).build();
            foodCategory.addCategoryMapping(category);
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

    @Test
    @DisplayName("Member Food 생성하기")
    @CustomMockUser(email="tester1234@test.com")
    void test3() {
        Member member = Member.builder().name("tester").email("tester1234@test.com").status(MemberStatus.SHOW).build();

        Tag tag1 = Tag.builder().name("배부른").status(TagStatus.USE).build();
        Tag tag2 = Tag.builder().name("담백한").status(TagStatus.USE).build();

        Category category1 = Category.builder().name("고기").visible(true).build();
        Category category2 = Category.builder().name("양념육").visible(true).build();

        em.persist(member);
        em.persist(tag1);
        em.persist(tag2);
        em.persist(category1);
        em.persist(category2);
        em.flush();
        em.clear();

        FoodMemberCommand commandWithNoCategory = FoodMemberCommand.builder().name("불고기").build();
        commandWithNoCategory.setTagIds(Arrays.asList(tag1.getId(), tag2.getId()));

        FoodMemberCommand commandWithNoTag = FoodMemberCommand.builder().name("불고기").build();
        commandWithNoTag.setCategoryIds(Arrays.asList(category1.getId(), category2.getId()));

        FoodMemberCommand command = FoodMemberCommand.builder().name("불고기").build();
        command.setCategoryIds(Arrays.asList(category1.getId(), category2.getId()));
        command.setTagIds(Arrays.asList(tag1.getId(), tag2.getId()));

        FoodMemberCommand commandDuplication = FoodMemberCommand.builder().name("불고기").build();
        command.setCategoryIds(Arrays.asList(category1.getId(), category2.getId()));
        command.setTagIds(Arrays.asList(tag1.getId(), tag2.getId()));

        final Long foodId = foodService.createFood(command);

        final Food findFood = em.find(Food.class, foodId);


        assertThrows(RuntimeException.class, () -> foodService.createFood(commandWithNoCategory), "카테고리가 없는 경우 예외가 발생한다.");
        assertThrows(RuntimeException.class, () -> foodService.createFood(commandWithNoTag), "태그가 없을 때 예외가 발생해야 한다.");
        assertThrows(CannotExecuteException.class, () -> foodService.createFood(commandDuplication), "음식명이 중복될 땐, 예외가 발생해야 한다.");
        assertNotNull(findFood);
    }


    @Test
    @DisplayName("Admin Food 생성하기")
    @CustomMockUser(email="tester1234@test.com")
    void test4() {
        Admin admin = Admin.builder().email("tester1234@test.com").build();

        Tag tag1 = Tag.builder().name("배부른").status(TagStatus.USE).build();
        Tag tag2 = Tag.builder().name("담백한").status(TagStatus.USE).build();

        Category category1 = Category.builder().name("고기").visible(true).build();
        Category category2 = Category.builder().name("양념육").visible(true).build();

        Country korea = Country.builder().name("한국").build();

        em.persist(admin);
        em.persist(korea);
        em.persist(tag1);
        em.persist(tag2);
        em.persist(category1);
        em.persist(category2);
        em.flush();
        em.clear();

        FoodAdminCommand commandWithNoCategory = FoodAdminCommand.builder().name("불고기").deleted(false).country(korea).build();
        commandWithNoCategory.setTagIds(Arrays.asList(tag1.getId(), tag2.getId()));

        FoodAdminCommand commandWithNoTag = FoodAdminCommand.builder().name("불고기").deleted(false).country(korea).build();
        commandWithNoTag.setCategoryIds(Arrays.asList(category1.getId(), category2.getId()));

        FoodAdminCommand command = FoodAdminCommand.builder().name("불고기").deleted(false).country(korea).build();
        command.setCategoryIds(Arrays.asList(category1.getId(), category2.getId()));
        command.setTagIds(Arrays.asList(tag1.getId(), tag2.getId()));

        FoodAdminCommand commandDuplication = FoodAdminCommand.builder().name("불고기").deleted(false).country(korea).build();
        command.setCategoryIds(Arrays.asList(category1.getId(), category2.getId()));
        command.setTagIds(Arrays.asList(tag1.getId(), tag2.getId()));

        FoodAdminCommand commandWithNoDeleted = FoodAdminCommand.builder().name("불고기").country(korea).build();
        command.setCategoryIds(Arrays.asList(category1.getId(), category2.getId()));
        command.setTagIds(Arrays.asList(tag1.getId(), tag2.getId()));

        FoodAdminCommand commandWithNoCountry = FoodAdminCommand.builder().name("불고기").deleted(false).build();
        command.setCategoryIds(Arrays.asList(category1.getId(), category2.getId()));
        command.setTagIds(Arrays.asList(tag1.getId(), tag2.getId()));

        final Long foodId = foodService.createFood(command);

        final Food findFood = em.find(Food.class, foodId);


        assertThrows(RuntimeException.class, () -> foodService.createFood(commandWithNoCategory), "카테고리가 없는 경우 예외가 발생한다.");
        assertThrows(RuntimeException.class, () -> foodService.createFood(commandWithNoTag), "태그가 없을 때 예외가 발생해야 한다.");
        assertThrows(RuntimeException.class, () -> foodService.createFood(commandWithNoDeleted), "승인 상태가 없을 경우 예외가 발생해야 한다.");
        assertThrows(RuntimeException.class, () -> foodService.createFood(commandWithNoCountry), "국가가 없을 경우 예외가 발생해야 한다.");
        assertThrows(CannotExecuteException.class, () -> foodService.createFood(commandDuplication), "음식명이 중복될 땐, 예외가 발생해야 한다.");
        assertNotNull(findFood);
    }

    @Test
    @DisplayName("Food update 테스트")
    @CustomMockUser(email="tester1234@test.com")
    void test5() {
        // TODO 기존 태그 & 카테고리와 매핑이 끊어지고 잘 붙는지 테스트
        Admin admin = Admin.builder().email("tester1234@test.com").build();

        Tag tag1 = Tag.builder().name("배부른").status(TagStatus.USE).build();
        Tag tag2 = Tag.builder().name("담백한").status(TagStatus.USE).build();

        Category category1 = Category.builder().name("고기").visible(true).build();
        Category category2 = Category.builder().name("양념육").visible(true).build();

        Country korea = Country.builder().name("한국").build();

        Food food = Food.builder().deleted(false).name("불고기").build();
        food.addFoodTags(
                FoodTag
                        .builder()
                        .tag(tag1)
                        .build()
        );
        FoodCategory foodCategory = FoodCategory.builder().food(food).build();
        category1.addFoodMapping(foodCategory);

        em.persist(foodCategory);
        em.persist(food);
        em.persist(admin);
        em.persist(korea);
        em.persist(tag1);
        em.persist(tag2);
        em.persist(category1);
        em.persist(category2);
        em.flush();
        em.clear();

        FoodAdminCommand command = FoodAdminCommand.builder().id(food.getId()).name("불고기").deleted(false).country(korea).build();
        command.setCategoryIds(Arrays.asList(category2.getId()));
        command.setTagIds(Arrays.asList(tag2.getId()));

        foodService.updateFood(command);

        final Food findFood = em.find(Food.class, food.getId());

        final List<FoodCategory> foodCategories = foodCategoryRepository.findByFood(findFood);


        assertThat(findFood.getFoodTags()).extracting("id").containsExactly(tag2.getId()).withFailMessage("태그를 변경했을 때, 오직 변경한 태그와 매핑이 있어야 한다.");
        assertThat(foodCategories).extracting("category").extracting("id").containsExactly(category2.getId()).withFailMessage("카테고리를 변경했을 때, 오직 변경한 카테고리와 매핑이 있어야 한다.");


    }
}
