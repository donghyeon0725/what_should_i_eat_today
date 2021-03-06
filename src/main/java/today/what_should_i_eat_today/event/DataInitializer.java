package today.what_should_i_eat_today.event;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.admin.entity.Admin;
import today.what_should_i_eat_today.domain.category.entity.Category;
import today.what_should_i_eat_today.domain.category.entity.FoodCategory;
import today.what_should_i_eat_today.domain.country.entity.Country;
import today.what_should_i_eat_today.domain.favorite.entity.Favorite;
import today.what_should_i_eat_today.domain.food.entity.Food;
import today.what_should_i_eat_today.domain.food.entity.FoodTag;
import today.what_should_i_eat_today.domain.likes.entity.Likes;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.model.Attachment;
import today.what_should_i_eat_today.domain.post.entity.Post;
import today.what_should_i_eat_today.domain.qna.entity.Qna;
import today.what_should_i_eat_today.domain.qna.entity.QnaStatus;
import today.what_should_i_eat_today.domain.qna.entity.QnaType;
import today.what_should_i_eat_today.domain.report.entity.Report;
import today.what_should_i_eat_today.domain.report.entity.ReportStatus;
import today.what_should_i_eat_today.domain.report.entity.ReportType;
import today.what_should_i_eat_today.domain.review.entity.Review;
import today.what_should_i_eat_today.domain.review.entity.ReviewType;
import today.what_should_i_eat_today.domain.tag.entity.Tag;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Profile({"local2", "test"})
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
     * ???????????? INSERT
     */
    private void categoriesInit() {

        Admin root = Admin.builder().email("root@gmail.com").password("1234").build();
        em.persist(root);

        // ??????
        Country japan = Country.builder().name("??????").build();
        em.persist(japan);

        // ??????
        Tag oilyTag = Tag.builder().name("?????????").build();
        Tag fullTag = Tag.builder().name("???").build();
        em.persist(oilyTag);
        em.persist(fullTag);

        // ???
        Category riceCategory = Category.builder().name("???").description("??????").admin(root).visible(true).build();
        em.persist(riceCategory);

        // ??????
        Category soupCategory = Category.builder().name("??????").description("??????").admin(root).visible(true).build();
        em.persist(soupCategory);


        // ?????? ?????????
        List<Food> foods = Arrays.asList(
                Food.builder().name("?????????").country(japan).deleted(false).build(),
                Food.builder().name("????????????").country(japan).deleted(false).build(),
                Food.builder().name("?????????").country(japan).deleted(false).build(),
                Food.builder().name("???????????????").country(japan).deleted(false).build(),
                Food.builder().name("??????????????????").country(japan).deleted(false).build(),
                Food.builder().name("???????????????").country(japan).deleted(false).build(),
                Food.builder().name("???????????????").country(japan).deleted(false).build(),
                Food.builder().name("????????????").country(japan).deleted(false).build(),
                Food.builder().name("?????????").country(japan).deleted(false).build(),
                Food.builder().name("????????????").country(japan).deleted(false).build(),
                Food.builder().name("???????????????").country(japan).deleted(false).build(),
                Food.builder().name("?????????").country(japan).deleted(false).build(),
                Food.builder().name("????????????").country(japan).deleted(false).build()
        );

        List<String> strings = Arrays.asList("?????????", "?????????", "????????????");

        for (Food food : foods) {
            em.persist(food);

            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // ?????? ???????????? ?????????, ??? ????????? ????????? ????????????
            food.addFoodTags(FoodTag.builder().tag(oilyTag).build());
            food.addFoodTags(FoodTag.builder().tag(fullTag).build());

            // ?????? ???????????? ??? ??????????????? ????????????
            FoodCategory foodCategory1 = FoodCategory.builder().food(food).build();
            foodCategory1.addCategoryMapping(riceCategory);
            em.persist(foodCategory1);


            if (strings.contains(food.getName())) {
                FoodCategory foodCategory2 = FoodCategory.builder().food(food).build();
                foodCategory2.addCategoryMapping(soupCategory);
                em.persist(foodCategory2);
            }

        }


        List<Category> categories = Arrays.asList(
                Category.builder().name("???").description("??????").admin(root).visible(true).build(),
                Category.builder().name("??????").description("??????").admin(root).visible(true).build(),
                Category.builder().name("?????????").description("??????").admin(root).visible(true).build(),
                Category.builder().name("??????/?????????").description("??????").admin(root).visible(true).build(),
                Category.builder().name("??????").description("??????").admin(root).visible(true).build(),
                Category.builder().name("??????").description("??????").admin(root).visible(true).build(),
                Category.builder().name("??????").description("??????").admin(root).visible(true).build(),
                Category.builder().name("??????").description("??????").admin(root).visible(true).build(),
                Category.builder().name("??????").description("??????").admin(root).visible(true).build()
        );
        for (Category category : categories) {
            em.persist(category);
        }


        // ?????? ????????? 250???
        for (int i = 0; i < 250; i++) {
            em.persist(Category.builder().name("?????????" + i).description("??????" + i).admin(root).visible(false).build());
        }
    }

    public void dataInit() {
        Member member1 = Member.builder().nickName("martin").email("testtesttesttest1217@naver.com").name("tester").build();
        Member member2 = Member.builder().nickName("john").email("testtesttest1234@naver.com").name("tester").build();

        Admin admin = Admin.builder().email("admin@naver.com").password("1234").build();

        Country korea = Country.builder().name("??????").build();

        Tag oilyTag = Tag.builder().name("?????????").build();
        Tag fullTag = Tag.builder().name("?????????").build();

        Food food = Food.builder().name("?????????").deleted(false).country(korea).build();
        food.addFoodTags(FoodTag.builder().tag(oilyTag).build());
        food.addFoodTags(FoodTag.builder().tag(fullTag).build());


//        Category meetCategory = Category.builder().name("??????").build();
//        Category koreaCategory = Category.builder().name("??????").build();
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
        insertQnas();
        insertPostLikedAndFavoriteByMe();
    }

    private void insertPostLikedAndFavoriteByMe() {
        Member member = Member.builder().email("aboutTest@naver.com").build();
        Member other = Member.builder().email("otherother@naver.com").build();
        em.persist(member);
        em.persist(other);

        Food food = Food.builder().name("??????").build();
        Attachment attachment = new Attachment("https://upload.wikimedia.org/wikipedia/commons/thumb/9/99/Unofficial_JavaScript_logo_2.svg/1920px-Unofficial_JavaScript_logo_2.svg.png", "?????? ?????? ?????????");
        Post post1 = Post.builder().title("?????????1").attachment(attachment).member(other).food(food).content("?????????").archived(false).build();
        Post post2 = Post.builder().title("?????????2").attachment(attachment).member(other).food(food).content("?????????").archived(false).build();
        Post post3 = Post.builder().title("?????????3").attachment(attachment).member(other).food(food).content("?????????").archived(false).build();
        Post post4 = Post.builder().title("?????????4").attachment(attachment).member(other).food(food).content("?????????").archived(false).build();
        Post myPost = Post.builder().title("????????????").attachment(attachment).member(member).food(food).content("?????????").archived(false).build();

        Likes likes1 = Likes.builder().post(post1).member(member).build();
        Likes likes2 = Likes.builder().post(post4).member(member).build();

        Favorite favorite1 = Favorite.builder().post(post2).member(member).build();
        Favorite favorite2 = Favorite.builder().post(post3).member(member).build();


        em.persist(food);
        em.persist(myPost);
        em.persist(post1);
        em.persist(post2);
        em.persist(post3);
        em.persist(post4);
        em.persist(likes1);
        em.persist(likes2);
        em.persist(favorite1);
        em.persist(favorite2);
        em.flush();
        em.clear();

    }

    public void insertQnas() {
        Member member = Member.builder().name("??????").nickName("?????????").email("member@test.com").build();
        em.persist(member);

        for (int i=0; i<25; i++) {
            Qna qna = Qna.builder().member(member).type(QnaType.SYSTEM_QUESTION).status(QnaStatus.NOT_PROCESSED).title("??????"+i).content("??????"+i).build();
            em.persist(qna);
        }

        em.flush();
        em.clear();
    }

    public void insertReports(Member reporter, Member reported) {

        Food food = Food.builder().name("????????? ??????").build();
        // ????????? ??????
        Attachment attachment = new Attachment("https://upload.wikimedia.org/wikipedia/commons/thumb/9/99/Unofficial_JavaScript_logo_2.svg/1920px-Unofficial_JavaScript_logo_2.svg.png", "?????? ?????? ?????????");
        Post post = Post.builder().title("content").attachment(attachment).content("content").food(food).member(reported).build();

        Report postReport = Report.builder()
                .post(post).title("????????? ???????????????").content("???????????????").type(ReportType.POST)
                .status(ReportStatus.NOT_APPROVED).member(reporter).reportedMember(reported).build();

        // ????????? ??????
        Report profileReport = Report.builder()
                .title("????????? ???????????????").content("???????????????").type(ReportType.PROFILE)
                .status(ReportStatus.NOT_APPROVED).member(reporter).reportedMember(reported).build();


        // ?????? ??????
        Review review = Review.builder().content("???????????????").member(reported).reviewType(ReviewType.REVIEW).post(post).build();
        Report reviewReport = Report.builder()
                .title("?????? ???????????????").content("???????????????").type(ReportType.REVIEW).review(review)
                .status(ReportStatus.NOT_APPROVED).member(reporter).reportedMember(reported).build();


        for (int i = 0; i < 25; i++) {
            em.persist(Report.builder()
                    .title("??????????????? " + i).content("??????????????? " + i).type(ReportType.PROFILE)
                    .status(ReportStatus.APPROVED).member(reporter).reportedMember(reported).build());
        }

        em.persist(food);
        em.persist(post);
        em.persist(review);
        em.persist(postReport);
        em.persist(profileReport);
        em.persist(reviewReport);
        em.flush();
        em.clear();
    }

}
