package today.what_should_i_eat_today.event;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.admin.entity.Admin;
import today.what_should_i_eat_today.domain.category.entity.Category;
import today.what_should_i_eat_today.domain.category.entity.FoodCategory;
import today.what_should_i_eat_today.domain.country.entity.Country;
import today.what_should_i_eat_today.domain.food.entity.Food;
import today.what_should_i_eat_today.domain.food.entity.FoodTag;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.model.Attachment;
import today.what_should_i_eat_today.domain.post.entity.Post;
import today.what_should_i_eat_today.domain.tag.entity.Tag;
import today.what_should_i_eat_today.domain.tag.entity.TagStatus;

import javax.persistence.EntityManager;

@Component
@Transactional
@Profile(value = "local")
@RequiredArgsConstructor
public class DataInitializer2 implements ApplicationRunner {

    private final EntityManager em;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Admin admin = Admin.builder().email("admin@gmail.com").password("1234").build();
        Admin root = Admin.builder().email("root@gmail.com").password("1234").build();
        em.persist(admin);
        em.persist(root);

        Member NIBH_FELIS_MEMBER = Member.builder().email("test1@gmail.com").name("Nibh felis").nickName("Nibh felis").profileImg("//blog.kakaocdn.net/dn/smtrT/btriGVNsK0x/Ksvc9s8wc1kkDEk3DxwfN1/img.png").build();
        Member NEQUE_ANTE_MEMBER = Member.builder().email("test2@gmail.com").name("Neque ante").nickName("Neque ante").profileImg("//blog.kakaocdn.net/dn/bLRr9X/btriGkUpvtS/KE8FocWNkyHkEQoMWYfd4K/img.png").build();
        Member TELLUS_MAURIS_MEMBER = Member.builder().email("test3@gmail.com").name("Tellus mauris").nickName("Tellus mauris").profileImg("//blog.kakaocdn.net/dn/c0PcCL/btriGkNEvm5/whUpqO8Ps34EsNMh1tXm81/img.png").build();
        em.persist(NIBH_FELIS_MEMBER);
        em.persist(NEQUE_ANTE_MEMBER);
        em.persist(TELLUS_MAURIS_MEMBER);

        Category rice = Category.builder().name("밥").description("음식").visible(true).admin(admin).build();
        Category soup = Category.builder().name("국물").description("음식").visible(true).admin(admin).build();
        Category donkatsAndSushi = Category.builder().name("돈까스/회").description("음식").visible(true).admin(admin).build();
        Category noodle = Category.builder().name("면").description("음식").visible(true).admin(admin).build();
        Category meat = Category.builder().name("고기").description("음식").visible(true).admin(admin).build();
        Category seafood = Category.builder().name("해산물").description("음식").visible(true).admin(admin).build();
        Category dessert = Category.builder().name("디저트").description("음식").visible(true).admin(admin).build();
        Category snackBar = Category.builder().name("분식").description("음식").visible(true).admin(admin).build();
        Category fastfood = Category.builder().name("패스트 푸드").description("음식").visible(true).admin(admin).build();
        em.persist(rice);
        em.persist(soup);
        em.persist(donkatsAndSushi);
        em.persist(noodle);
        em.persist(meat);
        em.persist(seafood);
        em.persist(dessert);
        em.persist(snackBar);
        em.persist(fastfood);

        Country korean = Country.builder().name("한식").build();
        Country japanese = Country.builder().name("일식").build();
        Country chinese = Country.builder().name("중식").build();
        Country western = Country.builder().name("양식").build();
        Country vietnamese = Country.builder().name("베트남식").build();
        Country taiwanese = Country.builder().name("대만식").build();
        Country italy = Country.builder().name("이탈리아").build();
        Country russia = Country.builder().name("러시아").build();
        Country france = Country.builder().name("프랑스").build();
        em.persist(korean);
        em.persist(japanese);
        em.persist(chinese);
        em.persist(western);
        em.persist(vietnamese);
        em.persist(taiwanese);
        em.persist(italy);
        em.persist(russia);
        em.persist(france);


        Food food_순대국밥 = Food.builder().name("순대국밥").deleted(false).admin(admin).country(korean).build();
        Food food_뼈해장국 = Food.builder().name("뼈해장국").deleted(false).admin(admin).country(korean).build();
        Food food_비빔밥 = Food.builder().name("비빔밥").deleted(false).admin(admin).country(korean).build();
        Food food_김치볶음밥 = Food.builder().name("김치볶음밥").deleted(false).admin(admin).country(korean).build();
        Food food_칼국수 = Food.builder().name("칼국수").deleted(false).admin(admin).country(korean).build();
        Food food_라면 = Food.builder().name("라면").deleted(false).admin(admin).country(korean).build();
        Food food_콩나물_라면 = Food.builder().name("콩나물 라면").deleted(false).admin(admin).country(korean).build();
        Food food_소고기 = Food.builder().name("소고기").deleted(false).admin(admin).country(korean).build();
        Food food_김치전 = Food.builder().name("김치전").deleted(false).admin(admin).country(korean).build();
        Food food_순두부_찌개 = Food.builder().name("순두부 찌개").deleted(false).admin(admin).country(korean).build();
        Food food_제육덮밥 = Food.builder().name("제육덮밥").deleted(false).admin(admin).country(korean).build();
        Food food_돼지갈비 = Food.builder().name("돼지갈비").deleted(false).admin(admin).country(korean).build();
        Food food_쌈밥 = Food.builder().name("쌈밥").deleted(false).admin(admin).country(korean).build();
        Food food_잔치국수 = Food.builder().name("잔치국수").deleted(false).admin(admin).country(korean).build();
        Food food_돈까스 = Food.builder().name("돈까스").deleted(false).admin(admin).country(japanese).build();
        Food food_치즈_돈까스 = Food.builder().name("치즈 돈까스").deleted(false).admin(admin).country(japanese).build();
        Food food_떡볶이 = Food.builder().name("떡볶이").deleted(false).admin(admin).country(korean).build();
        Food food_카레라이스 = Food.builder().name("카레라이스").deleted(false).admin(admin).country(korean).build();
        Food food_오므라이스 = Food.builder().name("오므라이스").deleted(false).admin(admin).country(korean).build();
        Food food_참치김밥 = Food.builder().name("참치김밥").deleted(false).admin(admin).country(korean).build();
        Food food_잡채볶음밥 = Food.builder().name("잡채 볶음밥").deleted(false).admin(admin).country(korean).build();
        Food food_삼각김밥 = Food.builder().name("삼각 김밥").deleted(false).admin(admin).country(korean).build();
        Food food_설렁탕 = Food.builder().name("설렁탕").deleted(false).admin(admin).country(korean).build();
        Food food_봉골레_파스타 = Food.builder().name("봉골레 파스타").deleted(false).admin(admin).country(italy).build();
        Food food_알리오_올리오 = Food.builder().name("알리오 올리오").deleted(false).admin(admin).country(italy).build();
        Food food_스테이크_크림_파스타 = Food.builder().name("스테이크 크림 파스타").deleted(false).admin(admin).country(italy).build();
        Food food_토마토_파스타 = Food.builder().name("토마토 파스타").deleted(false).admin(admin).country(italy).build();
        Food food_스파게티 = Food.builder().name("스파게티").deleted(false).admin(admin).country(italy).build();
        Food food_갈비찜 = Food.builder().name("갈비찜").deleted(false).admin(admin).country(korean).build();
        Food food_비빔국수 = Food.builder().name("비빔국수").deleted(false).admin(admin).country(korean).build();
        Food food_부대찌개 = Food.builder().name("부대찌개").deleted(false).admin(admin).country(korean).build();
        Food food_햄버거 = Food.builder().name("햄버거").deleted(false).admin(admin).country(western).build();
        em.persist(food_순대국밥);
        em.persist(food_뼈해장국);
        em.persist(food_비빔밥);
        em.persist(food_김치볶음밥);
        em.persist(food_칼국수);
        em.persist(food_라면);
        em.persist(food_콩나물_라면);
        em.persist(food_소고기);
        em.persist(food_김치전);
        em.persist(food_순두부_찌개);
        em.persist(food_제육덮밥);
        em.persist(food_돼지갈비);
        em.persist(food_쌈밥);
        em.persist(food_잔치국수);
        em.persist(food_돈까스);
        em.persist(food_치즈_돈까스);
        em.persist(food_떡볶이);
        em.persist(food_카레라이스);
        em.persist(food_오므라이스);
        em.persist(food_참치김밥);
        em.persist(food_잡채볶음밥);
        em.persist(food_삼각김밥);
        em.persist(food_설렁탕);
        em.persist(food_봉골레_파스타);
        em.persist(food_알리오_올리오);
        em.persist(food_스테이크_크림_파스타);
        em.persist(food_토마토_파스타);
        em.persist(food_스파게티);
        em.persist(food_갈비찜);
        em.persist(food_비빔국수);
        em.persist(food_부대찌개);
        em.persist(food_햄버거);


//        FoodCategory foodCategory_김치전 = FoodCategory.builder().food(food_김치전).build();
//        em.persist(foodCategory_김치전);

        // mapping
        rice.addFoodMappings(

                FoodCategory.builder().food(food_순대국밥).build(),
                FoodCategory.builder().food(food_뼈해장국).build(),
                FoodCategory.builder().food(food_비빔밥).build(),
                FoodCategory.builder().food(food_김치볶음밥).build(),
                FoodCategory.builder().food(food_제육덮밥).build(),
                FoodCategory.builder().food(food_순두부_찌개).build(),
                FoodCategory.builder().food(food_김치볶음밥).build(),
                FoodCategory.builder().food(food_쌈밥).build(),
                FoodCategory.builder().food(food_카레라이스).build(),
                FoodCategory.builder().food(food_오므라이스).build(),
                FoodCategory.builder().food(food_참치김밥).build(),
                FoodCategory.builder().food(food_잡채볶음밥).build(),
                FoodCategory.builder().food(food_삼각김밥).build(),
                FoodCategory.builder().food(food_설렁탕).build(),
                FoodCategory.builder().food(food_부대찌개).build()
        );

        soup.addFoodMappings(
                FoodCategory.builder().food(food_순대국밥).build(),
                FoodCategory.builder().food(food_뼈해장국).build(),
                FoodCategory.builder().food(food_순두부_찌개).build(),
                FoodCategory.builder().food(food_부대찌개).build()
        );

        donkatsAndSushi.addFoodMappings(
                FoodCategory.builder().food(food_돈까스).build(),
                FoodCategory.builder().food(food_치즈_돈까스).build()
        );


        noodle.addFoodMappings(
                FoodCategory.builder().food(food_칼국수).build(),
                FoodCategory.builder().food(food_라면).build(),
                FoodCategory.builder().food(food_콩나물_라면).build(),
                FoodCategory.builder().food(food_잔치국수).build(),
                FoodCategory.builder().food(food_칼국수).build(),
                FoodCategory.builder().food(food_봉골레_파스타).build(),
                FoodCategory.builder().food(food_알리오_올리오).build(),
                FoodCategory.builder().food(food_스테이크_크림_파스타).build(),
                FoodCategory.builder().food(food_토마토_파스타).build(),
                FoodCategory.builder().food(food_봉골레_파스타).build(),
                FoodCategory.builder().food(food_스파게티).build(),
                FoodCategory.builder().food(food_비빔국수).build()
        );

        meat.addFoodMappings(
                FoodCategory.builder().food(food_뼈해장국).build(),
                FoodCategory.builder().food(food_소고기).build(),
                FoodCategory.builder().food(food_제육덮밥).build(),
                FoodCategory.builder().food(food_돼지갈비).build(),
                FoodCategory.builder().food(food_돈까스).build(),
                FoodCategory.builder().food(food_치즈_돈까스).build(),
                FoodCategory.builder().food(food_스테이크_크림_파스타).build(),
                FoodCategory.builder().food(food_갈비찜).build()
        );

        // seafood

        // dessert

        snackBar.addFoodMapping(FoodCategory.builder().food(food_떡볶이).build());
        fastfood.addFoodMapping(FoodCategory.builder().food(food_햄버거).build());


        Tag 매운 = Tag.builder().name("매운").status(TagStatus.USE).admin(admin).build();
        Tag 달콤한 = Tag.builder().name("달콤한").status(TagStatus.USE).admin(admin).build();
        Tag 쓴 = Tag.builder().name("쓴").status(TagStatus.USE).admin(admin).build();
        Tag 싱거운 = Tag.builder().name("싱거운").status(TagStatus.USE).admin(admin).build();
        Tag 달달한 = Tag.builder().name("달달한").status(TagStatus.USE).admin(admin).build();
        Tag 신선한 = Tag.builder().name("신선한").status(TagStatus.USE).admin(admin).build();
        Tag 기름진 = Tag.builder().name("기름진").status(TagStatus.USE).admin(admin).build();
        Tag 순한 = Tag.builder().name("순한").status(TagStatus.USE).admin(admin).build();
        Tag 짠 = Tag.builder().name("짠").status(TagStatus.USE).admin(admin).build();
        Tag 매콤한 = Tag.builder().name("매콤한").status(TagStatus.USE).admin(admin).build();
        Tag 단 = Tag.builder().name("단").status(TagStatus.USE).admin(admin).build();
        Tag 바삭한 = Tag.builder().name("바삭한").status(TagStatus.USE).admin(admin).build();
        Tag 눅눅한 = Tag.builder().name("눅눅한").status(TagStatus.USE).admin(admin).build();
        Tag 부드러운 = Tag.builder().name("부드러운").status(TagStatus.USE).admin(admin).build();
        Tag 쫄깃한 = Tag.builder().name("쫄깃한").status(TagStatus.USE).admin(admin).build();
        Tag 질긴 = Tag.builder().name("질긴").status(TagStatus.USE).admin(admin).build();
        Tag 느끼한 = Tag.builder().name("느끼한").status(TagStatus.USE).admin(admin).build();
        Tag 담백한 = Tag.builder().name("담백한").status(TagStatus.USE).admin(admin).build();
        Tag 얼큰한 = Tag.builder().name("얼큰한").status(TagStatus.USE).admin(admin).build();
        Tag 뜨거운 = Tag.builder().name("뜨거운").status(TagStatus.USE).admin(admin).build();
        Tag 차가운 = Tag.builder().name("차가운").status(TagStatus.USE).admin(admin).build();
        Tag 따듯한 = Tag.builder().name("따듯한").status(TagStatus.USE).admin(admin).build();
        Tag 미지근한 = Tag.builder().name("미지근한").status(TagStatus.USE).admin(admin).build();
        Tag 고칼로리 = Tag.builder().name("고칼로리").status(TagStatus.USE).admin(admin).build();
        Tag 저칼로리 = Tag.builder().name("저칼로리").status(TagStatus.USE).admin(admin).build();
        Tag 든든한 = Tag.builder().name("든든한").status(TagStatus.USE).admin(admin).build();
        Tag 고소한 = Tag.builder().name("고소한").status(TagStatus.USE).admin(admin).build();
        Tag 구수한 = Tag.builder().name("구수한").status(TagStatus.USE).admin(admin).build();
        Tag 밤에_먹기_좋은 = Tag.builder().name("밤에 먹기 좋은").status(TagStatus.USE).admin(admin).member(NIBH_FELIS_MEMBER).build();
        Tag 퇴근_후_먹기_좋은 = Tag.builder().name("퇴근 후 먹기 좋은").status(TagStatus.USE).admin(admin).member(NIBH_FELIS_MEMBER).build();
        Tag 옛날_음식 = Tag.builder().name("옛날 음식").status(TagStatus.USE).admin(admin).member(NIBH_FELIS_MEMBER).build();
        Tag 해장 = Tag.builder().name("해장").status(TagStatus.USE).admin(admin).member(NIBH_FELIS_MEMBER).build();
        Tag 아플_때_먹기좋은 = Tag.builder().name("아플 때 먹기좋은").status(TagStatus.USE).admin(admin).member(NIBH_FELIS_MEMBER).build();
        Tag 건강식 = Tag.builder().name("건강식").status(TagStatus.USE).admin(admin).member(NIBH_FELIS_MEMBER).build();
        em.persist(매운);
        em.persist(달콤한);
        em.persist(쓴);
        em.persist(싱거운);
        em.persist(달달한);
        em.persist(신선한);
        em.persist(기름진);
        em.persist(순한);
        em.persist(짠);
        em.persist(매콤한);
        em.persist(단);
        em.persist(바삭한);
        em.persist(눅눅한);
        em.persist(부드러운);
        em.persist(쫄깃한);
        em.persist(질긴);
        em.persist(느끼한);
        em.persist(담백한);
        em.persist(얼큰한);
        em.persist(뜨거운);
        em.persist(차가운);
        em.persist(따듯한);
        em.persist(미지근한);
        em.persist(고칼로리);
        em.persist(저칼로리);
        em.persist(든든한);
        em.persist(고소한);
        em.persist(구수한);
        em.persist(밤에_먹기_좋은);
        em.persist(퇴근_후_먹기_좋은);
        em.persist(옛날_음식);
        em.persist(해장);
        em.persist(아플_때_먹기좋은);
        em.persist(건강식);

//        FoodTag foodTag_쓴 = FoodTag.builder().tag(쓴).build();
//        FoodTag foodTag_싱거운 = FoodTag.builder().tag(싱거운).build();
//        FoodTag foodTag_달달한 = FoodTag.builder().tag(달달한).build();
//        FoodTag foodTag_순한 = FoodTag.builder().tag(순한).build();
//        FoodTag foodTag_짠 = FoodTag.builder().tag(짠).build();
//        FoodTag foodTag_단 = FoodTag.builder().tag(단).build();
//        FoodTag foodTag_바삭한 = FoodTag.builder().tag(바삭한).build();
//        FoodTag foodTag_눅눅한 = FoodTag.builder().tag(눅눅한).build();
//        FoodTag foodTag_쫄깃한 = FoodTag.builder().tag(쫄깃한).build();
//        FoodTag foodTag_질긴 = FoodTag.builder().tag(질긴).build();
//        FoodTag foodTag_차가운 = FoodTag.builder().tag(차가운).build();
//        FoodTag foodTag_미지근한 = FoodTag.builder().tag(미지근한).build();
//        FoodTag foodTag_저칼로리 = FoodTag.builder().tag(저칼로리).build();
//        FoodTag foodTag_밤에_먹기_좋은 = FoodTag.builder().tag(밤에_먹기_좋은).build();
//        FoodTag foodTag_퇴근_후_먹기_좋은 = FoodTag.builder().tag(퇴근_후_먹기_좋은).build();
//        FoodTag foodTag_옛날_음식 = FoodTag.builder().tag(옛날_음식).build();
//        FoodTag foodTag_아플_때_먹기좋은 = FoodTag.builder().tag(아플_때_먹기좋은).build();
//        em.persist(foodTag_쓴);
//        em.persist(foodTag_싱거운);
//        em.persist(foodTag_달달한);
//        em.persist(foodTag_순한);
//        em.persist(foodTag_짠);
//        em.persist(foodTag_단);
//        em.persist(foodTag_바삭한);
//        em.persist(foodTag_눅눅한);
//        em.persist(foodTag_쫄깃한);
//        em.persist(foodTag_질긴);
//        em.persist(foodTag_차가운);
//        em.persist(foodTag_미지근한);
//        em.persist(foodTag_저칼로리);
//        em.persist(foodTag_밤에_먹기_좋은);
//        em.persist(foodTag_퇴근_후_먹기_좋은);
//        em.persist(foodTag_옛날_음식);
//        em.persist(foodTag_아플_때_먹기좋은);


        food_순대국밥.addFoodTags(
                FoodTag.builder().tag(매운).build(),
                FoodTag.builder().tag(든든한).build(),
                FoodTag.builder().tag(해장).build()
        );
        food_뼈해장국.addFoodTags(
                FoodTag.builder().tag(든든한).build(),
                FoodTag.builder().tag(매운).build(),
                FoodTag.builder().tag(얼큰한).build()
        );

        food_비빔밥.addFoodTags(
                FoodTag.builder().tag(건강식).build(),
                FoodTag.builder().tag(신선한).build()
        );
        food_김치볶음밥.addFoodTags(
                FoodTag.builder().tag(매콤한).build()
        );
        food_칼국수.addFoodTags(
                FoodTag.builder().tag(뜨거운).build(),
                FoodTag.builder().tag(담백한).build()
        );
        food_라면.addFoodTags(
                FoodTag.builder().tag(뜨거운).build(),
                FoodTag.builder().tag(얼큰한).build()
        );
        food_콩나물_라면.addFoodTags(
                FoodTag.builder().tag(뜨거운).build(),
                FoodTag.builder().tag(얼큰한).build()
        );
        food_소고기.addFoodTags(
                FoodTag.builder().tag(부드러운).build(),
                FoodTag.builder().tag(기름진).build()
        );
        food_김치전.addFoodTags(
                FoodTag.builder().tag(기름진).build()
        );
        food_순두부_찌개.addFoodTags(
                FoodTag.builder().tag(뜨거운).build(),
                FoodTag.builder().tag(얼큰한).build()
        );
        food_제육덮밥.addFoodTags(
                FoodTag.builder().tag(매콤한).build(),
                FoodTag.builder().tag(고칼로리).build(),
                FoodTag.builder().tag(기름진).build()
        );
        food_돼지갈비.addFoodTags(
                FoodTag.builder().tag(고칼로리).build(),
                FoodTag.builder().tag(기름진).build()
        );
        food_쌈밥.addFoodTags(
                FoodTag.builder().tag(건강식).build()
        );
        food_잔치국수.addFoodTags(
                FoodTag.builder().tag(따듯한).build(),
                FoodTag.builder().tag(구수한).build()
        );
        food_돈까스.addFoodTags(
                FoodTag.builder().tag(고칼로리).build(),
                FoodTag.builder().tag(느끼한).build(),
                FoodTag.builder().tag(달콤한).build(),
                FoodTag.builder().tag(기름진).build()
        );
        food_치즈_돈까스.addFoodTags(
                FoodTag.builder().tag(고칼로리).build(),
                FoodTag.builder().tag(느끼한).build(),
                FoodTag.builder().tag(달콤한).build(),
                FoodTag.builder().tag(기름진).build()
        );
        food_떡볶이.addFoodTags(
                FoodTag.builder().tag(매콤한).build()
        );
        food_카레라이스.addFoodTags(
                FoodTag.builder().tag(매콤한).build()
        );
        food_오므라이스.addFoodTags(
                FoodTag.builder().tag(달콤한).build()
        );
        food_참치김밥.addFoodTags(
                FoodTag.builder().tag(기름진).build(),
                FoodTag.builder().tag(고소한).build()
        );
        food_잡채볶음밥.addFoodTags(
                FoodTag.builder().tag(고소한).build(),
                FoodTag.builder().tag(기름진).build()
        );
        food_삼각김밥.addFoodTags(
                FoodTag.builder().tag(고소한).build()
        );
        food_설렁탕.addFoodTags(
                FoodTag.builder().tag(든든한).build(),
                FoodTag.builder().tag(뜨거운).build(),
                FoodTag.builder().tag(건강식).build()
        );
        food_봉골레_파스타.addFoodTags(
                FoodTag.builder().tag(느끼한).build(),
                FoodTag.builder().tag(기름진).build()
        );
        food_알리오_올리오.addFoodTags(
                FoodTag.builder().tag(느끼한).build(),
                FoodTag.builder().tag(기름진).build()
        );
        food_스테이크_크림_파스타.addFoodTags(
                FoodTag.builder().tag(느끼한).build(),
                FoodTag.builder().tag(기름진).build()
        );
        food_토마토_파스타.addFoodTags(
                FoodTag.builder().tag(느끼한).build(),
                FoodTag.builder().tag(기름진).build()
        );
        food_스파게티.addFoodTags(
                FoodTag.builder().tag(느끼한).build(),
                FoodTag.builder().tag(기름진).build()
        );
        food_갈비찜.addFoodTags(
                FoodTag.builder().tag(느끼한).build(),
                FoodTag.builder().tag(기름진).build(),
                FoodTag.builder().tag(부드러운).build()
        );
        food_비빔국수.addFoodTags(
                FoodTag.builder().tag(매콤한).build()
        );
        food_부대찌개.addFoodTags(
                FoodTag.builder().tag(얼큰한).build(),
                FoodTag.builder().tag(뜨거운).build()
        );
        food_햄버거.addFoodTags(
                FoodTag.builder().tag(기름진).build(),
                FoodTag.builder().tag(느끼한).build()
        );


        Post post_소사골 = Post.builder().title("소사골 우순대국").content("담소 순대국").archived(false).member(NEQUE_ANTE_MEMBER).food(food_순대국밥).attachment(Attachment.builder().name("test.png").path("//pds.joongang.co.kr/news/component/htmlphoto_mmdata/201411/18/htm_2014111820423954005011.jpg").build()).build();
        Post post_우거지 = Post.builder().title("우거지 뼈해장국").content("강강술래 뼈해장국").archived(false).member(NEQUE_ANTE_MEMBER).food(food_뼈해장국).attachment(Attachment.builder().name("test.png").path("//image.skstoa.com/goods/085/21983085_c.jpg").build()).build();
        Post post_비빔밥 = Post.builder().title("비빔밥").content("본돌솥 비빔밥").archived(false).member(NEQUE_ANTE_MEMBER).food(food_비빔밥).attachment(Attachment.builder().name("test.png").path("//health.chosun.com/site/data/img_dir/2021/01/27/2021012702508_0.jpg").build()).build();
        Post post_김치볶음밥 = Post.builder().title("김치볶음밥").content("김밥천국 김치볶음밥").archived(false).member(NEQUE_ANTE_MEMBER).food(food_김치볶음밥).attachment(Attachment.builder().name("test.png").path("//storage.googleapis.com/cbmpress/uploads/sites/3/2018/10/CBM-17.jpg").build()).build();
        Post post_바지락 = Post.builder().title("바지락 칼국수").content("칼국수").archived(false).member(NEQUE_ANTE_MEMBER).food(food_칼국수).attachment(Attachment.builder().name("test.png").path("//t1.daumcdn.net/cfile/tistory/260690505262AD571D").build()).build();
        Post post_라면 = Post.builder().title("라면").content("진 라면").archived(false).member(NEQUE_ANTE_MEMBER).food(food_라면).attachment(Attachment.builder().name("test.png").path("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRsh1tKt3Dfvuhys1sMv085NmTd5BOd4Oi4ocioLBJhclQZRe2WDd_10ceJEiFoi1wgwt0&usqp=CAU").build()).build();
        Post post_콩나물 = Post.builder().title("콩나물 라면").content("김밥천국 콩나물 라면").archived(false).member(NEQUE_ANTE_MEMBER).food(food_콩나물_라면).attachment(Attachment.builder().name("test.png").path("https://t1.daumcdn.net/cfile/tistory/2058923D4E0E682002").build()).build();
        Post post_백선생 = Post.builder().title("백선생 스테이크").content("아메리칸 미트스토리").archived(false).member(NEQUE_ANTE_MEMBER).food(food_소고기).attachment(Attachment.builder().name("test.png").path("//post-phinf.pstatic.net/MjAxODAzMjFfMTI5/MDAxNTIxNjIxNTE0MjU4.K6A7tUiwden3UQMwelM35_4GIFB-NlhoPPIVD6IWokEg.KiHRHoreNOKSDQtEsW2de4ikvCRXlemsHah2xEjUGjQg.PNG/CA180320A_01.png?type=w1200").build()).build();
        Post post_김치전 = Post.builder().title("김치전").content("백종원의 김치전").archived(false).member(NEQUE_ANTE_MEMBER).food(food_김치전).attachment(Attachment.builder().name("test.png").path("https://mblogthumb-phinf.pstatic.net/MjAyMTA5MDlfMjUg/MDAxNjMxMTUwMTUwMzAy.wYA-Vy5z0Hses5cLZm_NSvCs1KesK00MbngrY6cqRq4g.P_jWFJteuY4dMDRp5iGqex8uLGZSdDnfyKVm-cr0un8g.JPEG.wy5493/output_362068409.jpg?type=w800").build()).build();
        Post post_순두부 = Post.builder().title("순두부 찌개").content("담소 순두부찌개").archived(false).member(NEQUE_ANTE_MEMBER).food(food_순두부_찌개).attachment(Attachment.builder().name("test.png").path("//mblogthumb-phinf.pstatic.net/MjAyMDA5MjFfODcg/MDAxNjAwNjY3MzE1NjA2.Upndrth268zQQiuntIAXUBp9sXIi0QlUMwM2THMfflMg.i8jEArCsP9xGthG_NWChQnOk21JnI4QIfp-78cg-pegg.JPEG.hwapori/1600667313986.JPG?type=w800").build()).build();
        Post post_제육덮밥 = Post.builder().title("제육덮밥").content("백종원 제육덮밥").archived(false).member(NEQUE_ANTE_MEMBER).food(food_제육덮밥).attachment(Attachment.builder().name("test.png").path("//mblogthumb-phinf.pstatic.net/MjAxODA2MjlfMTg3/MDAxNTMwMjI3MTM2MTYw.YAnGJ54iSwne65j2SPsv2jtvvbjVKrpGeXkiNySXTyYg.EBatLj2Hs8R_DT_zUb33VRjh19lSDzIeMw3pW6lDpAsg.JPEG.dew36/image_8966900271530227101909.jpg?type=w800").build()).build();
        Post post_돼지갈비 = Post.builder().title("돼지갈비").content("갈비다움").archived(false).member(NEQUE_ANTE_MEMBER).food(food_돼지갈비).attachment(Attachment.builder().name("test.png").path("http://shop-phinf.pstatic.net/20190123_126/100986059_1548254589607WQ3Fv_JPEG/%BB%F3%BC%BC%C6%E4%C0%CC%C1%F6%B0%ED%B1%E2%BB%E7%C1%F8.jpg?type=w860").build()).build();
        Post post_쌈밥 = Post.builder().title("쌈밥").content("백종원의 원조 쌈밥집").archived(false).member(NEQUE_ANTE_MEMBER).food(food_쌈밥).attachment(Attachment.builder().name("test.png").path("//ssambap.co.kr/wp-content/uploads/2017/06/m_vs_01.jpg").build()).build();
        Post post_잔치국수 = Post.builder().title("잔치국수").content("백종원의 잔치국수").archived(false).member(NEQUE_ANTE_MEMBER).food(food_잔치국수).attachment(Attachment.builder().name("test.png").path("//image.board.sbs.co.kr/2021/03/26/iRU1616720284909.jpg").build()).build();
        Post post_돈까스 = Post.builder().title("돈까스").content("왕 돈까스").archived(false).member(NEQUE_ANTE_MEMBER).food(food_돈까스).attachment(Attachment.builder().name("test.png").path("//t1.daumcdn.net/cfile/tistory/9951473F5D4633FD2C").build()).build();
        Post post_치즈 = Post.builder().title("치즈 돈까스").content("홍익 돈까스").archived(false).member(NEQUE_ANTE_MEMBER).food(food_치즈_돈까스).attachment(Attachment.builder().name("test.png").path("//contents.sixshop.com/thumbnails/uploadedFiles/39154/default/image_1594709054909_1000.jpg").build()).build();
        Post post_떡볶이 = Post.builder().title("떡볶이").content("죠스 떡볶이").archived(false).member(NEQUE_ANTE_MEMBER).food(food_떡볶이).attachment(Attachment.builder().name("test.png").path("http://www.seenews365.com/news/photo/201810/31333_31848_1721.jpg").build()).build();
        Post post_카레라이스 = Post.builder().title("카레라이스").content("매콤 쉬림프 카레라이스").archived(false).member(NEQUE_ANTE_MEMBER).food(food_카레라이스).attachment(Attachment.builder().name("test.png").path("http://ottogi.okitchen.co.kr/pds/upfile/2020-08-23_214059453[11].jpg").build()).build();
        Post post_오므라이스 = Post.builder().title("오므라이스").content("김밥천국 오므라이스").archived(false).member(NEQUE_ANTE_MEMBER).food(food_오므라이스).attachment(Attachment.builder().name("test.png").path("//ww.namu.la/s/fbd00013bf6c3e4230e9dc13a253cc82474e41e72a66ccbb0e86f6976035d6d4ae24507500630f93c3b32720308398238be15629620d1833288934190a59b3f6139bbe58b20b007f69f9f96c95f2dec1").build()).build();
        Post post_참치김밥 = Post.builder().title("참치김밥").content("김밥천국").archived(false).member(NEQUE_ANTE_MEMBER).food(food_참치김밥).attachment(Attachment.builder().name("test.png").path("//m.cookstopmall.co.kr/web/product/big/201904/d8ce567b8d4936d6ee546be6aa61f5df.jpg").build()).build();
        Post post_잡채 = Post.builder().title("잡채 볶음밥").content("프레시지 잡채 볶음밥").archived(false).member(NEQUE_ANTE_MEMBER).food(food_잡채볶음밥).attachment(Attachment.builder().name("test.png").path("//www.heodak.com/shopimages/heodak/006009000107.jpg?1623305996").build()).build();
        Post post_삼각 = Post.builder().title("삼각 김밥").content("치킨마요 삼각김밥").archived(false).member(NEQUE_ANTE_MEMBER).food(food_삼각김밥).attachment(Attachment.builder().name("test.png").path("//t1.daumcdn.net/liveboard/babshim/337d4693f99947a39d901dbacc0184a5.JPG").build()).build();
        Post post_설렁탕 = Post.builder().title("설렁탕").content("이병우 착한 설렁탕").archived(false).member(NEQUE_ANTE_MEMBER).food(food_설렁탕).attachment(Attachment.builder().name("test.png").path("//th2.tmon.kr/thumbs/image/d06/2d3/4a6/1165706d0_700x700_95_FIT.jpg").build()).build();
        Post post_봉골레 = Post.builder().title("봉골레 파스타").content("롤링 파스타").archived(false).member(NEQUE_ANTE_MEMBER).food(food_봉골레_파스타).attachment(Attachment.builder().name("test.png").path("//t1.daumcdn.net/cfile/tistory/9938A0435FF71A9022").build()).build();
        Post post_알리오 = Post.builder().title("알리오 올리오").content("롤링 파스타").archived(false).member(NEQUE_ANTE_MEMBER).food(food_알리오_올리오).attachment(Attachment.builder().name("test.png").path("//img.danawa.com/images/descFiles/5/68/4067903_1573739178901.jpeg").build()).build();
        Post post_스테이크 = Post.builder().title("스테이크 크림 파스타").content("롤링 파스타").archived(false).member(NEQUE_ANTE_MEMBER).food(food_스테이크_크림_파스타).attachment(Attachment.builder().name("test.png").path("//blog.kakaocdn.net/dn/nM5dN/btq0bw6NOTG/46vCl3py0mkiX5OSfPwR8k/img.jpg").build()).build();
        Post post_토마토 = Post.builder().title("토마토 파스타").content("롤링 파스타").archived(false).member(NEQUE_ANTE_MEMBER).food(food_토마토_파스타).attachment(Attachment.builder().name("test.png").path("//static.tasteem.io/uploads/3993/post/32791/content_6f9015ce-6725-4327-8fa3-5bf343e5ac81.jpeg").build()).build();
        Post post_스파게티 = Post.builder().title("스파게티").content("스파게티").archived(false).member(NEQUE_ANTE_MEMBER).food(food_스파게티).attachment(Attachment.builder().name("test.png").path("//t1.daumcdn.net/cfile/tistory/9960AD33598BEE2837").build()).build();
        Post post_갈비찜 = Post.builder().title("갈비찜").content("통마늘 돼지 갈비찜").archived(false).member(NEQUE_ANTE_MEMBER).food(food_갈비찜).attachment(Attachment.builder().name("test.png").path("//www.cj.co.kr/images/theKitchen/PHON/0000002320/0000009726/0000002320.jpg").build()).build();
        Post post_비빔국수 = Post.builder().title("비빔국수").content("비빔국수").archived(false).member(NEQUE_ANTE_MEMBER).food(food_비빔국수).attachment(Attachment.builder().name("test.png").path("//t1.daumcdn.net/cfile/tistory/2202DB4158A64E4C34").build()).build();
        Post post_부대찌개 = Post.builder().title("부대찌개").content("스태프 부대찌개").archived(false).member(NEQUE_ANTE_MEMBER).food(food_부대찌개).attachment(Attachment.builder().name("test.png").path("//foodyap.co.kr/shopimages/goldplate1/066001000008.jpg?1560850364").build()).build();
        Post post_햄버거 = Post.builder().title("햄버거").content("싸이버거").archived(false).member(NEQUE_ANTE_MEMBER).food(food_햄버거).attachment(Attachment.builder().name("test.png").path("//w.namu.la/s/b576be4b5714cf0462d9a541f3356a85469b0c5082b906ef0d330c87cb405f70c8be2dc91c36bd5d61780a71714d29db1a7555eca042f1d7f75eb4f0f5fdcfd1b8493908209e3a452ef670bf148f870e").build()).build();
        em.persist(post_소사골);
        em.persist(post_우거지);
        em.persist(post_비빔밥);
        em.persist(post_김치볶음밥);
        em.persist(post_바지락);
        em.persist(post_라면);
        em.persist(post_콩나물);
        em.persist(post_백선생);
        em.persist(post_김치전);
        em.persist(post_순두부);
        em.persist(post_제육덮밥);
        em.persist(post_돼지갈비);
        em.persist(post_쌈밥);
        em.persist(post_잔치국수);
        em.persist(post_돈까스);
        em.persist(post_치즈);
        em.persist(post_떡볶이);
        em.persist(post_카레라이스);
        em.persist(post_오므라이스);
        em.persist(post_참치김밥);
        em.persist(post_잡채);
        em.persist(post_삼각);
        em.persist(post_설렁탕);
        em.persist(post_봉골레);
        em.persist(post_알리오);
        em.persist(post_스테이크);
        em.persist(post_토마토);
        em.persist(post_스파게티);
        em.persist(post_갈비찜);
        em.persist(post_비빔국수);
        em.persist(post_부대찌개);
        em.persist(post_햄버거);
    }
}
