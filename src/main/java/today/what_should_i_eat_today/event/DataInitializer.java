package today.what_should_i_eat_today.event;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import today.what_should_i_eat_today.domain.admin.entity.Admin;
import today.what_should_i_eat_today.domain.category.entity.Category;
import today.what_should_i_eat_today.domain.country.entity.Country;
import today.what_should_i_eat_today.domain.food.entity.Food;
import today.what_should_i_eat_today.domain.food.entity.FoodTag;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.post.entity.Post;
import today.what_should_i_eat_today.domain.report.entity.Report;
import today.what_should_i_eat_today.domain.report.entity.ReportStatus;
import today.what_should_i_eat_today.domain.report.entity.ReportType;
import today.what_should_i_eat_today.domain.review.entity.Review;
import today.what_should_i_eat_today.domain.review.entity.ReviewType;
import today.what_should_i_eat_today.domain.tag.entity.Tag;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

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
        categoriesInit();
    }

    /**
     * 카테고리 INSERT
     */
    private void categoriesInit() {
        List<Category> categories = Arrays.asList(
                Category.builder().name("밥").description("음식").admin(null).visible(true).build(),
                Category.builder().name("면").description("음식").admin(null).visible(true).build(),
                Category.builder().name("고기").description("음식").admin(null).visible(true).build(),
                Category.builder().name("해산물").description("음식").admin(null).visible(true).build(),
                Category.builder().name("국물").description("음식").admin(null).visible(true).build(),
                Category.builder().name("간식/디저트").description("음식").admin(null).visible(true).build(),
                Category.builder().name("중식").description("음식").admin(null).visible(true).build(),
                Category.builder().name("한식").description("음식").admin(null).visible(true).build(),
                Category.builder().name("일식").description("음식").admin(null).visible(true).build(),
                Category.builder().name("배달").description("음식").admin(null).visible(true).build(),
                Category.builder().name("분식").description("음식").admin(null).visible(true).build()
        );

        for (Category category : categories) {
            em.persist(category);
        }

        // 임시 데이터 250개
        for (int i = 0; i < 250; i++) {
            em.persist(Category.builder().name("테스트" + i).description("내용" + i).visible(false).admin(null).build());
        }
    }

    public void dataInit() {
        Member member1 = Member.builder().nickName("martin").email("testtesttesttest1217@naver.com").name("tester").build();
        Member member2 = Member.builder().nickName("john").email("testtesttest1234@naver.com").name("tester").build();

        Admin admin = Admin.builder().email("admin@naver.com").password("1234").build();

        Country korea = Country.builder().name("한국").build();

        Tag oilyTag = Tag.builder().name("기름진").build();
        Tag fullTag = Tag.builder().name("배부른").build();

        Food food = Food.builder().name("불고기").deleted(false).country(korea).build();
        food.addFoodTags(FoodTag.builder().tag(oilyTag).build());
        food.addFoodTags(FoodTag.builder().tag(fullTag).build());

//        Category meetCategory = Category.builder().name("고기").build();
//        Category koreaCategory = Category.builder().name("한국").build();
//        meetCategory.addFoodMapping(FoodCategory.builder().food(food).build());
//        koreaCategory.addFoodMapping(FoodCategory.builder().food(food).build());

        em.persist(member1);
        em.persist(member2);
        em.persist(admin);
        em.persist(korea);
//        em.persist(meetCategory);
//        em.persist(koreaCategory);
        em.persist(oilyTag);
        em.persist(fullTag);
        em.persist(food);
        em.flush();
        em.clear();

        insertReports(member1, member2);

    }

    public void insertReports(Member reporter, Member reported) {

        // 포스트 신고
        Post post = Post.builder().content("content").member(reported).build();
        Report postReport = Report.builder()
                .post(post).title("포스트 신고합니다").content("신고합니다").type(ReportType.POST)
                .status(ReportStatus.NOT_APPROVED).member(reporter).reportedMember(reported).build();

        // 프로필 신고
        Report profileReport = Report.builder()
                .title("프로필 신고합니다").content("신고합니다").type(ReportType.PROFILE)
                .status(ReportStatus.NOT_APPROVED).member(reporter).reportedMember(reported).build();


        // 리뷰 신고
        Review review = Review.builder().content("신고합니다").member(reported).reviewType(ReviewType.REVIEW).post(post).build();
        Report reviewReport = Report.builder()
                .title("리뷰 신고합니다").content("신고합니다").type(ReportType.REVIEW).review(review)
                .status(ReportStatus.NOT_APPROVED).member(reporter).reportedMember(reported).build();


        for (int i = 0; i < 250; i++) {
            em.persist(Report.builder()
                    .title("신고합니다 " + i).content("신고합니다 " + i).type(ReportType.PROFILE)
                    .status(ReportStatus.APPROVED).member(reporter).reportedMember(reported).build());
        }

        em.persist(post);
        em.persist(review);
        em.persist(postReport);
        em.persist(profileReport);
        em.persist(reviewReport);
        em.flush();
        em.clear();
    }

}
