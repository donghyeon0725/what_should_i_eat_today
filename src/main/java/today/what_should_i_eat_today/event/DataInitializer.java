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

        Admin root = Admin.builder().email("root@gmail.com").password("1234").build();
        em.persist(root);

        // 일식
        Country japan = Country.builder().name("일본").build();
        em.persist(japan);

        // 태그
        Tag oilyTag = Tag.builder().name("든든한").build();
        Tag fullTag = Tag.builder().name("쌀").build();
        em.persist(oilyTag);
        em.persist(fullTag);

        // 밥
        Category riceCategory = Category.builder().name("밥").description("음식").admin(root).visible(true).build();
        em.persist(riceCategory);

        // 국물
        Category soupCategory = Category.builder().name("국물").description("음식").admin(root).visible(true).build();
        em.persist(soupCategory);


        // 국밥 임식들
        List<Food> foods = Arrays.asList(
                Food.builder().name("설렁탕").country(japan).build(),
                Food.builder().name("순대국밥").country(japan).build(),
                Food.builder().name("돈까스").country(japan).build(),
                Food.builder().name("김치볶음밥").country(japan).build(),
                Food.builder().name("참치마요덮밥").country(japan).build(),
                Food.builder().name("카레라이스").country(japan).build(),
                Food.builder().name("오므라이스").country(japan).build(),
                Food.builder().name("참치김밥").country(japan).build(),
                Food.builder().name("비빔밥").country(japan).build(),
                Food.builder().name("제육덮밥").country(japan).build(),
                Food.builder().name("잡채볶음밥").country(japan).build(),
                Food.builder().name("주먹밥").country(japan).build(),
                Food.builder().name("삼각김밥").country(japan).build()
        );

        List<String> strings = Arrays.asList("돈까스", "비빔밥", "제육덮밥");

        for (Food food : foods) {
            em.persist(food);

            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 모든 음식들에 든든한, 쌀 이라는 태그를 넣어준다
            food.addFoodTags(FoodTag.builder().tag(oilyTag).build());
            food.addFoodTags(FoodTag.builder().tag(fullTag).build());

            // 모든 음식들에 밥 카테고리를 넣어준다
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
                Category.builder().name("면").description("음식").admin(root).visible(true).build(),
                Category.builder().name("고기").description("음식").admin(root).visible(true).build(),
                Category.builder().name("해산물").description("음식").admin(root).visible(true).build(),
                Category.builder().name("간식/디저트").description("음식").admin(root).visible(true).build(),
                Category.builder().name("중식").description("음식").admin(root).visible(true).build(),
                Category.builder().name("한식").description("음식").admin(root).visible(true).build(),
                Category.builder().name("일식").description("음식").admin(root).visible(true).build(),
                Category.builder().name("배달").description("음식").admin(root).visible(true).build(),
                Category.builder().name("분식").description("음식").admin(root).visible(true).build()
        );
        for (Category category : categories) {
            em.persist(category);
        }


        // 임시 데이터 250개
        for (int i = 0; i < 250; i++) {
            em.persist(Category.builder().name("테스트" + i).description("내용" + i).admin(root).visible(false).build());
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
        insertQnas();
        insertPostLikedAndFavoriteByMe();
    }

    private void insertPostLikedAndFavoriteByMe() {
        Member member = Member.builder().email("aboutTest@naver.com").build();
        Member other = Member.builder().email("otherother@naver.com").build();
        em.persist(member);
        em.persist(other);

        Food food = Food.builder().name("음식").build();
        Attachment attachment = new Attachment("https://upload.wikimedia.org/wikipedia/commons/thumb/9/99/Unofficial_JavaScript_logo_2.svg/1920px-Unofficial_JavaScript_logo_2.svg.png", "이름 없는 게시물");
        Post post1 = Post.builder().title("포스트1").attachment(attachment).member(other).food(food).content("컨텐츠").archived(false).build();
        Post post2 = Post.builder().title("포스트2").attachment(attachment).member(other).food(food).content("컨텐츠").archived(false).build();
        Post post3 = Post.builder().title("포스트3").attachment(attachment).member(other).food(food).content("컨텐츠").archived(false).build();
        Post post4 = Post.builder().title("포스트4").attachment(attachment).member(other).food(food).content("컨텐츠").archived(false).build();
        Post myPost = Post.builder().title("내포스트").attachment(attachment).member(member).food(food).content("컨텐츠").archived(false).build();

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
        Member member = Member.builder().name("이름").nickName("닉네임").email("member@test.com").build();
        em.persist(member);

        for (int i=0; i<25; i++) {
            Qna qna = Qna.builder().member(member).type(QnaType.SYSTEM_QUESTION).status(QnaStatus.NOT_PROCESSED).title("제목"+i).content("내용"+i).build();
            em.persist(qna);
        }

        em.flush();
        em.clear();
    }

    public void insertReports(Member reporter, Member reported) {

        Food food = Food.builder().name("가상의 음식").build();
        // 포스트 신고
        Post post = Post.builder().content("content").food(food).member(reported).build();
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


        for (int i = 0; i < 25; i++) {
            em.persist(Report.builder()
                    .title("신고합니다 " + i).content("신고합니다 " + i).type(ReportType.PROFILE)
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
