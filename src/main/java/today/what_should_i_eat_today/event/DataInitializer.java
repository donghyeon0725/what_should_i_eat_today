package today.what_should_i_eat_today.event;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import today.what_should_i_eat_today.domain.admin.entity.Admin;
import today.what_should_i_eat_today.domain.category.dao.CategoryRepository;
import today.what_should_i_eat_today.domain.category.entity.Category;
import today.what_should_i_eat_today.domain.category.entity.FoodCategory;
import today.what_should_i_eat_today.domain.country.entity.Country;
import today.what_should_i_eat_today.domain.food.dao.FoodRepository;
import today.what_should_i_eat_today.domain.food.entity.Food;
import today.what_should_i_eat_today.domain.food.entity.FoodTag;
import today.what_should_i_eat_today.domain.member.dao.MemberRepository;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.tag.entity.Tag;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
@Profile({"local", "test"})
@Transactional
public class DataInitializer implements ApplicationRunner {

    private final EntityManager em;

    @Override
    public void run(ApplicationArguments args) {
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        dataInit();
    }

    public void dataInit() {
        Member member1 = Member.builder().email("testtesttesttest1217@naver.com").name("tester").build();
        Member member2 = Member.builder().email("testtesttest1234@naver.com").name("tester").build();

        Admin admin = Admin.builder().email("admin@naver.com").password("1234").build();

        Country korea = Country.builder().name("한국").build();

        Tag oilyTag = Tag.builder().name("기름진").build();
        Tag fullTag = Tag.builder().name("배부른").build();

        Food food = Food.builder().name("불고기").deleted(false).country(korea).build();
        food.addFoodTags(FoodTag.builder().tag(oilyTag).build());
        food.addFoodTags(FoodTag.builder().tag(fullTag).build());

        Category meetCategory = Category.builder().name("고기").build();
        Category koreaCategory = Category.builder().name("한국").build();
        meetCategory.addFoodMapping(FoodCategory.builder().food(food).build());
        koreaCategory.addFoodMapping(FoodCategory.builder().food(food).build());

        em.persist(member1);
        em.persist(member2);
        em.persist(admin);
        em.persist(korea);
        em.persist(meetCategory);
        em.persist(koreaCategory);
        em.persist(oilyTag);
        em.persist(fullTag);
        em.persist(food);
        em.flush();
        em.clear();

    }

}
