package today.what_should_i_eat_today.event;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.admin.entity.Admin;
import today.what_should_i_eat_today.domain.category.entity.Category;
import today.what_should_i_eat_today.domain.country.entity.Country;
import today.what_should_i_eat_today.domain.food.entity.Food;
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

        // Admin
        Admin admin = Admin.builder().email("admin@gmail.com").password("1234").build();
        Admin root = Admin.builder().email("root@gmail.com").password("1234").build();
        em.persist(admin);
        em.persist(root);

        // Member
        Member NIBH_FELIS_MEMBER = Member.builder().email("test1@gmail.com").name("Nibh felis").nickName("Nibh felis").profileImg("//blog.kakaocdn.net/dn/smtrT/btriGVNsK0x/Ksvc9s8wc1kkDEk3DxwfN1/img.png").build();
        Member NEQUE_ANTE_MEMBER = Member.builder().email("test2@gmail.com").name("Neque ante").nickName("Neque ante").profileImg("//blog.kakaocdn.net/dn/bLRr9X/btriGkUpvtS/KE8FocWNkyHkEQoMWYfd4K/img.png").build();
        Member TELLUS_MAURIS_MEMBER = Member.builder().email("test3@gmail.com").name("Tellus mauris").nickName("Tellus mauris").profileImg("//blog.kakaocdn.net/dn/c0PcCL/btriGkNEvm5/whUpqO8Ps34EsNMh1tXm81/img.png").build();
        em.persist(NIBH_FELIS_MEMBER);
        em.persist(NEQUE_ANTE_MEMBER);
        em.persist(TELLUS_MAURIS_MEMBER);

        // Category
        Category category_밥 = Category.builder().name("죽").description("쌀, 보리, 밀, 조, 기장, 녹두, 수수, 콩, 메밀, 팥, 피, 옥수수 등의 곡식 알갱이들을 물에 특정한 방법으로 익혀서 먹는 것을 칭한다. 죽 역시 갖은 곡물 알갱이를 물에 익히지만 조리 방법이 다르다. 밥은 죽과 달리 곡식 알갱이의 형태가 유지되며, 물과 온도의 조절이 중요하다. 정확하게 말하면 곡식을 끓는 물에 강한 압력을 넣어 고온, 고압에 익히는 독특한 요리법이다. 또한 글루텐화 가공(반죽)을 거치지 않는다는 점에서 떡, 빵과도 확연히 구분된다.").visible(true).admin(admin).build();
        Category category_죽 = Category.builder().name("죽").description("곡식을 묽게 끓여낸 음식.").visible(true).admin(admin).build();
        Category category_김밥 = Category.builder().name("김밥").description("밀가루가 들어가지 않지만, 가볍게 즐길 수 있는 분식의 일종으로, 밥을 김으로 감싸 둥글게 만 뒤 잘라낸 음식. 위에 김을 깐 뒤 밥과 재료들을 얹고 말아낸 음식이다. 대부분의 경우 이를 낱개로 잘라 먹으나, 충무김밥과 같이 그냥 먹는 경우도 있다.").visible(true).admin(admin).build();
        Category category_덮밥 = Category.builder().name("덮밥").description("밥 위에 고기, 야채, 소스 등을 넣고 같이 섞어 먹는 요리의 일종이다. 쉽게 말해 밥 위에 반찬을 얹어 먹는 요리이다.").visible(true).admin(admin).build();
        Category category_볶음밥 = Category.builder().name("볶음밥").description("밥을 다른 재료와 함께 넣고 기름에 볶아 만든 음식의 통칭이다. 여기서는 한국의 볶음밥에 대해서만 다룬다.").visible(true).admin(admin).build();
        Category category_국밥 = Category.builder().name("국밥").description("국에다가 밥을 넣어 말아먹는 한국의 문화이자 요리의 통칭. 한국의 대표적인 전통 서민 음식을 꼽았을 때 국밥이 있다고 말할 수 있을 정도로 대중적 인지도가 높으며 역사도 깊다.").visible(true).admin(admin).build();
        Category category_해장국 = Category.builder().name("해장국").description("전날의 술기운으로 거북한 속을 풀기 위하여 먹는 국[1]을 말한다. 돼지 뼈를 넣고 끓인 뼈해장국, 선지를 넣고 끓인 선지해장국, 콩나물을 넣고 끓인 콩나물해장국 등이 전국적으로 유명하다.").visible(true).admin(admin).build();

        Category category_찜탕 = Category.builder().name("찜/탕").description("음식").visible(true).admin(admin).build();

        Category category_떡 = Category.builder().name("떡").description("음식").visible(true).admin(admin).build();
        Category category_떡볶이 = Category.builder().name("떡볶이").description("음식").visible(true).admin(admin).build();

        Category category_면 = Category.builder().name("면").description("음식").visible(true).admin(admin).build();
        Category category_국수 = Category.builder().name("국수").description("음식").visible(true).admin(admin).build();
        Category category_라면 = Category.builder().name("라면").description("음식").visible(true).admin(admin).build();
        Category category_냉면 = Category.builder().name("냉면").description("음식").visible(true).admin(admin).build();
        Category category_쫄면 = Category.builder().name("쫄면").description("음식").visible(true).admin(admin).build();
        Category category_파스타 = Category.builder().name("파스타").description("음식").visible(true).admin(admin).build();
        Category category_뇨끼 = Category.builder().name("뇨끼").description("음식").visible(true).admin(admin).build();

        Category category_빵 = Category.builder().name("빵").description("음식").visible(true).admin(admin).build();
        Category category_토스트 = Category.builder().name("토스트").description("음식").visible(true).admin(admin).build();
        Category category_와플 = Category.builder().name("와플").description("음식").visible(true).admin(admin).build();
        Category category_케익 = Category.builder().name("케익").description("음식").visible(true).admin(admin).build();
        Category category_도넛 = Category.builder().name("도넛").description("음식").visible(true).admin(admin).build();


        Category category_패스트푸드 = Category.builder().name("패스트푸드").description("음식").visible(true).admin(admin).build();

        Category category_햄버거 = Category.builder().name("햄버거").description("음식").visible(true).admin(admin).build();
        Category category_피자 = Category.builder().name("피자").description("음식").visible(true).admin(admin).build();
        Category category_카츠 = Category.builder().name("카츠").description("음식").visible(true).admin(admin).build();
        Category category_나베 = Category.builder().name("나베").description("음식").visible(true).admin(admin).build();
        Category category_치킨 = Category.builder().name("치킨").description("음식").visible(true).admin(admin).build();
        Category category_닭강정 = Category.builder().name("닭강정").description("음식").visible(true).admin(admin).build();

        Category category_분식 = Category.builder().name("분식").description("음식").visible(true).admin(admin).build();
        Category category_튀김 = Category.builder().name("튀김").description("음식").visible(true).admin(admin).build();
        Category category_전 = Category.builder().name("전").description("음식").visible(true).admin(admin).build();
        Category category_핫도그 = Category.builder().name("핫도그").description("음식").visible(true).admin(admin).build();

        Category category_고기 = Category.builder().name("고기").description("음식").visible(true).admin(admin).build();
        Category category_돼지고기 = Category.builder().name("돼지고기").description("음식").visible(true).admin(admin).build();
        Category category_소고기 = Category.builder().name("소고기").description("음식").visible(true).admin(admin).build();
        Category category_닭고기 = Category.builder().name("닭고기").description("음식").visible(true).admin(admin).build();
        Category category_오리 = Category.builder().name("오리").description("음식").visible(true).admin(admin).build();
        Category category_양고기 = Category.builder().name("양고기").description("음식").visible(true).admin(admin).build();
        Category category_스테이크 = Category.builder().name("스테이크").description("음식").visible(true).admin(admin).build();

        Category category_해산물 = Category.builder().name("해산물").description("음식").visible(true).admin(admin).build();
        Category category_생선 = Category.builder().name("생선").description("음식").visible(true).admin(admin).build();
        Category category_회 = Category.builder().name("회").description("음식").visible(true).admin(admin).build();
        Category category_초밥 = Category.builder().name("초밥").description("음식").visible(true).admin(admin).build();
        Category category_조개류 = Category.builder().name("조개류").description("음식").visible(true).admin(admin).build();
        Category category_갑각류 = Category.builder().name("갑각류").description("음식").visible(true).admin(admin).build();

        Category category_디저트 = Category.builder().name("디저트").description("음식").visible(true).admin(admin).build();

        Category category_한식 = Category.builder().name("한식").description("음식").visible(true).admin(admin).build();
        Category category_일식 = Category.builder().name("일식").description("음식").visible(true).admin(admin).build();
        Category category_중식 = Category.builder().name("중식").description("음식").visible(true).admin(admin).build();
        Category category_양식 = Category.builder().name("양식").description("음식").visible(true).admin(admin).build();
        Category category_베트남식 = Category.builder().name("베트남식").description("음식").visible(true).admin(admin).build();
        Category category_태국식 = Category.builder().name("태국식").description("음식").visible(true).admin(admin).build();
        Category category_이탈리아식 = Category.builder().name("이탈리아식").description("음식").visible(true).admin(admin).build();
        Category category_러시안식 = Category.builder().name("러시안식").description("음식").visible(true).admin(admin).build();
        Category category_프랑스식 = Category.builder().name("프랑스식").description("음식").visible(true).admin(admin).build();


        em.persist(category_밥);
        em.persist(category_죽);
        em.persist(category_김밥);
        em.persist(category_덮밥);
        em.persist(category_볶음밥);
        em.persist(category_국밥);
        em.persist(category_해장국);
        em.persist(category_찜탕);
        em.persist(category_떡);
        em.persist(category_떡볶이);
        em.persist(category_면);
        em.persist(category_국수);
        em.persist(category_라면);
        em.persist(category_냉면);
        em.persist(category_쫄면);
        em.persist(category_파스타);
        em.persist(category_뇨끼);
        em.persist(category_빵);
        em.persist(category_토스트);
        em.persist(category_와플);
        em.persist(category_케익);
        em.persist(category_도넛);
        em.persist(category_패스트푸드);
        em.persist(category_햄버거);
        em.persist(category_피자);
        em.persist(category_카츠);
        em.persist(category_나베);
        em.persist(category_치킨);
        em.persist(category_닭강정);
        em.persist(category_분식);
        em.persist(category_튀김);
        em.persist(category_전);
        em.persist(category_핫도그);
        em.persist(category_고기);
        em.persist(category_돼지고기);
        em.persist(category_소고기);
        em.persist(category_닭고기);
        em.persist(category_오리);
        em.persist(category_양고기);
        em.persist(category_스테이크);
        em.persist(category_해산물);
        em.persist(category_생선);
        em.persist(category_회);
        em.persist(category_초밥);
        em.persist(category_조개류);
        em.persist(category_갑각류);
        em.persist(category_디저트);
        em.persist(category_한식);
        em.persist(category_일식);
        em.persist(category_중식);
        em.persist(category_양식);
        em.persist(category_베트남식);
        em.persist(category_태국식);
        em.persist(category_이탈리아식);
        em.persist(category_러시안식);
        em.persist(category_프랑스식);


        // Country
        Country korean = Country.builder().name("한식").build();
        Country japan = Country.builder().name("일식").build();
        Country chinese = Country.builder().name("중식").build();
        Country western = Country.builder().name("양식").build();
        Country vietnamese = Country.builder().name("베트남식").build();
        Country taiwanese = Country.builder().name("대만식").build();
        Country italy = Country.builder().name("이탈리아식").build();
        Country russia = Country.builder().name("러시아식").build();
        Country france = Country.builder().name("프랑스식").build();
        Country germany = Country.builder().name("독일식").build();
        Country india = Country.builder().name("인도식").build();
        em.persist(korean);
        em.persist(japan);
        em.persist(chinese);
        em.persist(western);
        em.persist(vietnamese);
        em.persist(taiwanese);
        em.persist(italy);
        em.persist(russia);
        em.persist(france);
        em.persist(germany);
        em.persist(india);


        // Food
        Food food_야채죽 = Food.builder().name("야채죽").deleted(false).admin(admin).country(korean).build();
        Food food_쇠고기야채죽 = Food.builder().name("쇠고기야채죽").deleted(false).admin(admin).country(korean).build();
        Food food_전복죽 = Food.builder().name("전복죽").deleted(false).admin(admin).country(korean).build();
        Food food_닭죽 = Food.builder().name("닭죽").deleted(false).admin(admin).country(korean).build();
        Food food_참치김밥 = Food.builder().name("참치김밥").deleted(false).admin(admin).country(korean).build();
        Food food_돈까스김밥 = Food.builder().name("돈까스김밥").deleted(false).admin(admin).country(korean).build();
        Food food_쇠고기김밥 = Food.builder().name("쇠고기김밥").deleted(false).admin(admin).country(korean).build();
        Food food_치즈김밥 = Food.builder().name("치즈김밥").deleted(false).admin(admin).country(korean).build();
        Food food_소세시김밥 = Food.builder().name("소세시김밥").deleted(false).admin(admin).country(korean).build();
        Food food_날치알김밥 = Food.builder().name("날치알김밥").deleted(false).admin(admin).country(korean).build();
        Food food_새우김밥 = Food.builder().name("새우김밥").deleted(false).admin(admin).country(korean).build();
        Food food_오므라이스 = Food.builder().name("오므라이스").deleted(false).admin(admin).country(korean).build();
        Food food_카레라이스 = Food.builder().name("카레라이스").deleted(false).admin(admin).country(india).build();
        Food food_제육덮밥 = Food.builder().name("제육덮밥").deleted(false).admin(admin).country(korean).build();
        Food food_비빔밥 = Food.builder().name("비빔밥").deleted(false).admin(admin).country(korean).build();
        Food food_차슈덮밥 = Food.builder().name("차슈덮밥").deleted(false).admin(admin).country(japan).build();
        Food food_스테이크덮밥 = Food.builder().name("스테이크덮밥").deleted(false).admin(admin).country(korean).build();
        Food food_김치볶음밥 = Food.builder().name("김치볶음밥").deleted(false).admin(admin).country(korean).build();
        Food food_잡채볶음밥 = Food.builder().name("잡채볶음밥").deleted(false).admin(admin).country(korean).build();
        Food food_새우볶음밥 = Food.builder().name("새우볶음밥").deleted(false).admin(admin).country(korean).build();
        Food food_낙지볶음밥 = Food.builder().name("낙지볶음밥").deleted(false).admin(admin).country(korean).build();
        Food food_카레볶음밥 = Food.builder().name("카레볶음밥").deleted(false).admin(admin).country(korean).build();
        Food food_순대국밥 = Food.builder().name("순대국밥").deleted(false).admin(admin).country(korean).build();
        Food food_육개장 = Food.builder().name("육개장").deleted(false).admin(admin).country(korean).build();
        Food food_설렁탕 = Food.builder().name("설렁탕").deleted(false).admin(admin).country(korean).build();
        Food food_삼계탕 = Food.builder().name("삼계탕").deleted(false).admin(admin).country(korean).build();
        Food food_순두부찌개 = Food.builder().name("순두부찌개").deleted(false).admin(admin).country(korean).build();
        Food food_부대찌개 = Food.builder().name("부대찌개").deleted(false).admin(admin).country(korean).build();
        Food food_김치찌개 = Food.builder().name("김치찌개").deleted(false).admin(admin).country(korean).build();
        Food food_뼈해장국 = Food.builder().name("뼈해장국").deleted(false).admin(admin).country(korean).build();
        Food food_감자탕 = Food.builder().name("감자탕").deleted(false).admin(admin).country(korean).build();
        Food food_마라탕 = Food.builder().name("마라탕").deleted(false).admin(admin).country(chinese).build();
        Food food_안동찜닭 = Food.builder().name("안동찜닭").deleted(false).admin(admin).country(korean).build();
        Food food_갈비찜 = Food.builder().name("갈비찜").deleted(false).admin(admin).country(korean).build();
        Food food_매운탕 = Food.builder().name("매운탕").deleted(false).admin(admin).country(korean).build();
        Food food_알탕 = Food.builder().name("알탕").deleted(false).admin(admin).country(korean).build();
        Food food_대구탕 = Food.builder().name("대구탕").deleted(false).admin(admin).country(korean).build();
        Food food_닭볶음탕 = Food.builder().name("닭볶음탕").deleted(false).admin(admin).country(korean).build();
        Food food_떡볶이 = Food.builder().name("떡볶이").deleted(false).admin(admin).country(western).build();
        Food food_치즈떡볶이 = Food.builder().name("치즈떡볶이").deleted(false).admin(admin).country(korean).build();
        Food food_로제떡볶이 = Food.builder().name("로제떡볶이").deleted(false).admin(admin).country(korean).build();
        Food food_비빔국수 = Food.builder().name("비빔국수").deleted(false).admin(admin).country(korean).build();
        Food food_잔치국수 = Food.builder().name("잔치국수").deleted(false).admin(admin).country(korean).build();
        Food food_열무국수 = Food.builder().name("열무국수").deleted(false).admin(admin).country(korean).build();
        Food food_막국수 = Food.builder().name("막국수").deleted(false).admin(admin).country(korean).build();
        Food food_칼국수 = Food.builder().name("칼국수").deleted(false).admin(admin).country(korean).build();
        Food food_수제비 = Food.builder().name("수제비").deleted(false).admin(admin).country(korean).build();
        Food food_라면 = Food.builder().name("라면").deleted(false).admin(admin).country(korean).build();
        Food food_떡라면 = Food.builder().name("떡라면").deleted(false).admin(admin).country(korean).build();
        Food food_만두라면 = Food.builder().name("만두라면").deleted(false).admin(admin).country(korean).build();
        Food food_치즈라면 = Food.builder().name("치즈라면").deleted(false).admin(admin).country(korean).build();
        Food food_콩나물라면 = Food.builder().name("콩나물라면").deleted(false).admin(admin).country(korean).build();
        Food food_라볶이 = Food.builder().name("라볶이").deleted(false).admin(admin).country(korean).build();
        Food food_치즈라볶이 = Food.builder().name("치즈라볶이").deleted(false).admin(admin).country(korean).build();
        Food food_우삼겹부대라면 = Food.builder().name("우삼겹부대라면").deleted(false).admin(admin).country(korean).build();
        Food food_물냉면 = Food.builder().name("물냉면").deleted(false).admin(admin).country(korean).build();
        Food food_비빔냉면 = Food.builder().name("비빔냉면").deleted(false).admin(admin).country(korean).build();
        Food food_열무냉면 = Food.builder().name("열무냉면").deleted(false).admin(admin).country(korean).build();
        Food food_쫄면 = Food.builder().name("쫄면").deleted(false).admin(admin).country(korean).build();
        Food food_김치쫄면 = Food.builder().name("김치쫄면").deleted(false).admin(admin).country(korean).build();
        Food food_스파게티 = Food.builder().name("스파게티").deleted(false).admin(admin).country(italy).build();
        Food food_봉골레파스타 = Food.builder().name("봉골레파스타").deleted(false).admin(admin).country(italy).build();
        Food food_알리오올리오 = Food.builder().name("food_알리오올리오").deleted(false).admin(admin).country(italy).build();
        Food food_매운우삼겹오일파스타 = Food.builder().name("매운우삼겹오일파스타").deleted(false).admin(admin).country(italy).build();
        Food food_스테이크크림파스타 = Food.builder().name("스테이크크림파스타").deleted(false).admin(admin).country(italy).build();
        Food food_해물크림파스타 = Food.builder().name("해물크림파스타").deleted(false).admin(admin).country(italy).build();
        Food food_매운크림파스타 = Food.builder().name("매운크림파스타").deleted(false).admin(admin).country(italy).build();
        Food food_로제크림쉬림프파스타 = Food.builder().name("로제크림쉬림프파스타").deleted(false).admin(admin).country(italy).build();
        Food food_까르보나라 = Food.builder().name("까르보나라").deleted(false).admin(admin).country(italy).build();
        Food food_통소시지토마토파스타 = Food.builder().name("통소시지토마토파스타").deleted(false).admin(admin).country(italy).build();
        Food food_해물토마토파스타 = Food.builder().name("해물토마토파스타").deleted(false).admin(admin).country(italy).build();
        Food food_매운우삽겹토마토파스타 = Food.builder().name("매운우삽겹토마토파스타").deleted(false).admin(admin).country(italy).build();
        Food food_토마토파스타 = Food.builder().name("토마토파스타").deleted(false).admin(admin).country(italy).build();
        Food food_버섯크림뇨끼 = Food.builder().name("버섯크림뇨끼").deleted(false).admin(admin).country(italy).build();
        Food food_뇨끼감바스 = Food.builder().name("뇨끼감바스").deleted(false).admin(admin).country(italy).build();
        Food food_라비올리 = Food.builder().name("라비올리").deleted(false).admin(admin).country(italy).build();
        Food food_토르델리니 = Food.builder().name("토르델리니").deleted(false).admin(admin).country(italy).build();
        Food food_탈리아텔레 = Food.builder().name("탈리아텔레").deleted(false).admin(admin).country(italy).build();
        Food food_리소토 = Food.builder().name("리소토").deleted(false).admin(admin).country(italy).build();
        Food food_오레키에테 = Food.builder().name("오레키에테").deleted(false).admin(admin).country(italy).build();
        Food food_브루스케타 = Food.builder().name("브루스케타").deleted(false).admin(admin).country(italy).build();
        Food food_슈페츨레 = Food.builder().name("슈페츨레").deleted(false).admin(admin).country(italy).build();
        Food food_마카로니 = Food.builder().name("마카로니").deleted(false).admin(admin).country(italy).build();
        Food food_공갈빵 = Food.builder().name("공갈빵").deleted(false).admin(admin).country(korean).build();
        Food food_캄파뉴 = Food.builder().name("캄파뉴").deleted(false).admin(admin).country(korean).build();
        Food food_꽃빵 = Food.builder().name("꽃빵").deleted(false).admin(admin).country(korean).build();
        Food food_단팥빵 = Food.builder().name("단팥빵").deleted(false).admin(admin).country(korean).build();
        Food food_도넛 = Food.builder().name("도넛").deleted(false).admin(admin).country(korean).build();
        Food food_붕어빵 = Food.builder().name("붕어빵").deleted(false).admin(admin).country(korean).build();
        Food food_롤빵 = Food.builder().name("롤빵").deleted(false).admin(admin).country(korean).build();
        Food food_소라빵 = Food.builder().name("소라빵").deleted(false).admin(admin).country(korean).build();
        Food food_소보로빵 = Food.builder().name("소보로빵").deleted(false).admin(admin).country(korean).build();
        Food food_마늘빵 = Food.builder().name("마늘빵").deleted(false).admin(admin).country(korean).build();
        Food food_베이컨토스트 = Food.builder().name("베이컨토스트").deleted(false).admin(admin).country(france).build();
        Food food_피자토스트 = Food.builder().name("피자토스트").deleted(false).admin(admin).country(france).build();
        Food food_감자토스트 = Food.builder().name("감자토스트").deleted(false).admin(admin).country(france).build();
        Food food_햄버거 = Food.builder().name("햄버거").deleted(false).admin(admin).country(western).build();
        Food food_와플 = Food.builder().name("와플").deleted(false).admin(admin).country(western).build();
        Food food_팬케이크 = Food.builder().name("팬케이크").deleted(false).admin(admin).country(western).build();
        Food food_크루아상 = Food.builder().name("크루아상").deleted(false).admin(admin).country(korean).build();
        Food food_생크림케익 = Food.builder().name("생크림케익").deleted(false).admin(admin).country(western).build();
        Food food_초코케익 = Food.builder().name("초코케익").deleted(false).admin(admin).country(western).build();
        Food food_딸기케익 = Food.builder().name("딸기케익").deleted(false).admin(admin).country(western).build();
        Food food_오징어버거 = Food.builder().name("오징어버거").deleted(false).admin(admin).country(western).build();
        Food food_치즈버거 = Food.builder().name("치즈버거").deleted(false).admin(admin).country(western).build();
        Food food_새우버거 = Food.builder().name("새우버거").deleted(false).admin(admin).country(western).build();
        Food food_데리버거 = Food.builder().name("데리버거").deleted(false).admin(admin).country(western).build();
        Food food_불고기버거 = Food.builder().name("불고기버거").deleted(false).admin(admin).country(western).build();
        Food food_핫크리스피버거 = Food.builder().name("핫크리스피버거").deleted(false).admin(admin).country(western).build();
        Food food_한우불고기버거 = Food.builder().name("한우불고기버거").deleted(false).admin(admin).country(western).build();
        Food food_치킨버거 = Food.builder().name("치킨버거").deleted(false).admin(admin).country(western).build();
        Food food_콤비네이션피자 = Food.builder().name("콤비네이션피자").deleted(false).admin(admin).country(italy).build();
        Food food_불고기피자 = Food.builder().name("불고기피자").deleted(false).admin(admin).country(italy).build();
        Food food_고구마피자 = Food.builder().name("고구마피자").deleted(false).admin(admin).country(italy).build();
        Food food_포테이토피자 = Food.builder().name("포테이토피자").deleted(false).admin(admin).country(italy).build();
        Food food_쉬림프피자 = Food.builder().name("쉬림프피자").deleted(false).admin(admin).country(italy).build();
        Food food_스테이크피자 = Food.builder().name("스테이크피자").deleted(false).admin(admin).country(italy).build();
        Food food_페퍼로니피자 = Food.builder().name("페퍼로니피자").deleted(false).admin(admin).country(italy).build();
        Food food_하와이안피자 = Food.builder().name("하와이안피자").deleted(false).admin(admin).country(italy).build();
        Food food_돈카츠 = Food.builder().name("돈카츠").deleted(false).admin(admin).country(japan).build();
        Food food_치즈돈카츠 = Food.builder().name("치즈돈카츠").deleted(false).admin(admin).country(japan).build();
        Food food_고구마돈카츠 = Food.builder().name("고구마돈카츠").deleted(false).admin(admin).country(japan).build();
        Food food_생선카츠 = Food.builder().name("생선카츠").deleted(false).admin(admin).country(japan).build();
        Food food_새우카츠 = Food.builder().name("새우카츠").deleted(false).admin(admin).country(japan).build();
        Food food_로스카츠 = Food.builder().name("로스카츠").deleted(false).admin(admin).country(japan).build();
        Food food_히레카츠 = Food.builder().name("히레카츠").deleted(false).admin(admin).country(japan).build();
        Food food_돈카츠김치나베 = Food.builder().name("돈카츠김치나베").deleted(false).admin(admin).country(japan).build();
        Food food_후라이드 = Food.builder().name("후라이드").deleted(false).admin(admin).country(korean).build();
        Food food_양념치킨 = Food.builder().name("양념치킨").deleted(false).admin(admin).country(korean).build();
        Food food_간장치킨 = Food.builder().name("간장치킨").deleted(false).admin(admin).country(korean).build();
        Food food_시즈닝치킨 = Food.builder().name("시즈닝치킨").deleted(false).admin(admin).country(korean).build();
        Food food_바베큐치킨 = Food.builder().name("바베큐치킨").deleted(false).admin(admin).country(korean).build();
        Food food_순살치킨 = Food.builder().name("순살치킨").deleted(false).admin(admin).country(korean).build();
        Food food_닭강정 = Food.builder().name("닭강정").deleted(false).admin(admin).country(korean).build();
        Food food_순대 = Food.builder().name("순대").deleted(false).admin(admin).country(korean).build();
        Food food_오뎅 = Food.builder().name("오뎅").deleted(false).admin(admin).country(japan).build();
        Food food_감자튀김 = Food.builder().name("감자튀김").deleted(false).admin(admin).country(france).build();
        Food food_고구마튀김 = Food.builder().name("고구마튀김").deleted(false).admin(admin).country(korean).build();
        Food food_오징어튀김 = Food.builder().name("오징어튀김").deleted(false).admin(admin).country(korean).build();
        Food food_김말이 = Food.builder().name("김말이").deleted(false).admin(admin).country(korean).build();
        Food food_오뎅튀김 = Food.builder().name("오뎅튀김").deleted(false).admin(admin).country(korean).build();
        Food food_고로케 = Food.builder().name("고로케").deleted(false).admin(admin).country(japan).build();
        Food food_새우튀김 = Food.builder().name("새우튀김").deleted(false).admin(admin).country(japan).build();
        Food food_김치전 = Food.builder().name("김치전").deleted(false).admin(admin).country(korean).build();
        Food food_감자전 = Food.builder().name("감자전").deleted(false).admin(admin).country(korean).build();
        Food food_부추전 = Food.builder().name("부추전").deleted(false).admin(admin).country(korean).build();
        Food food_해물파전 = Food.builder().name("해물파전").deleted(false).admin(admin).country(korean).build();
        Food food_핫도그 = Food.builder().name("핫도그").deleted(false).admin(admin).country(western).build();
        Food food_체다치즈핫도그 = Food.builder().name("체다치즈핫도그").deleted(false).admin(admin).country(western).build();
        Food food_감자핫도그 = Food.builder().name("감자핫도그").deleted(false).admin(admin).country(western).build();
        Food food_통가래떡핫도그 = Food.builder().name("통가래떡핫도그").deleted(false).admin(admin).country(western).build();
        Food food_고구마통모짜핫도그 = Food.builder().name("고구마통모짜핫도그").deleted(false).admin(admin).country(western).build();
        Food food_삼겹살 = Food.builder().name("삼겹살").deleted(false).admin(admin).country(korean).build();
        Food food_고추장삼겹살 = Food.builder().name("고추장삼겹살").deleted(false).admin(admin).country(korean).build();
        Food food_통삼겹 = Food.builder().name("통삼겹").deleted(false).admin(admin).country(korean).build();
        Food food_수육 = Food.builder().name("수육").deleted(false).admin(admin).country(korean).build();
        Food food_대패삼겹살 = Food.builder().name("대패삼겹살").deleted(false).admin(admin).country(korean).build();
        Food food_족발 = Food.builder().name("족발").deleted(false).admin(admin).country(korean).build();
        Food food_곱창 = Food.builder().name("곱창").deleted(false).admin(admin).country(korean).build();
        Food food_대창 = Food.builder().name("대창").deleted(false).admin(admin).country(korean).build();
        Food food_돼지껍데기 = Food.builder().name("돼지껍데기").deleted(false).admin(admin).country(korean).build();
        Food food_돼지갈비 = Food.builder().name("돼지갈비").deleted(false).admin(admin).country(korean).build();
        Food food_보쌈 = Food.builder().name("보쌈").deleted(false).admin(admin).country(korean).build();
        Food food_삼겹살숙주볶음 = Food.builder().name("삼겹살숙주볶음").deleted(false).admin(admin).country(korean).build();
        Food food_소고기미역국 = Food.builder().name("소고기미역국").deleted(false).admin(admin).country(korean).build();
        Food food_소불고기 = Food.builder().name("소불고기").deleted(false).admin(admin).country(korean).build();
        Food food_소고기장조림 = Food.builder().name("소고기장조림").deleted(false).admin(admin).country(korean).build();
        Food food_스테이크 = Food.builder().name("스테이크").deleted(false).admin(admin).country(korean).build();
        Food food_닭발 = Food.builder().name("닭발").deleted(false).admin(admin).country(korean).build();
        Food food_닭발볶음 = Food.builder().name("닭발볶음").deleted(false).admin(admin).country(korean).build();
        Food food_닭꼬치 = Food.builder().name("닭꼬치").deleted(false).admin(admin).country(korean).build();
        Food food_닭똥집볶음 = Food.builder().name("닭똥집볶음").deleted(false).admin(admin).country(korean).build();
        Food food_훈제오리고기 = Food.builder().name("훈제오리고기").deleted(false).admin(admin).country(korean).build();
        Food food_오리불고기 = Food.builder().name("오리불고기").deleted(false).admin(admin).country(korean).build();
        Food food_오리진흙구이 = Food.builder().name("오리진흙구이").deleted(false).admin(admin).country(korean).build();
        Food food_오리양념볶음 = Food.builder().name("오리양념볶음").deleted(false).admin(admin).country(korean).build();
        Food food_양갈비스테이크 = Food.builder().name("양갈비스테이크").deleted(false).admin(admin).country(western).build();
        Food food_케밥 = Food.builder().name("케밥").deleted(false).admin(admin).country(western).build();
        Food food_양꼬치 = Food.builder().name("양꼬치").deleted(false).admin(admin).country(chinese).build();
        Food food_뉴옥스트립 = Food.builder().name("뉴옥스트립").deleted(false).admin(admin).country(western).build();
        Food food_포터하우스 = Food.builder().name("포터하우스").deleted(false).admin(admin).country(western).build();
        Food food_티본스테이크 = Food.builder().name("티본스테이크").deleted(false).admin(admin).country(western).build();
        Food food_안심스테이크 = Food.builder().name("안심스테이크").deleted(false).admin(admin).country(western).build();
        Food food_립아이 = Food.builder().name("립아이").deleted(false).admin(admin).country(western).build();
        Food food_백립 = Food.builder().name("백립").deleted(false).admin(admin).country(western).build();
        Food food_토마호크 = Food.builder().name("토마호크").deleted(false).admin(admin).country(western).build();
        Food food_우럭구이 = Food.builder().name("우럭구이").deleted(false).admin(admin).country(korean).build();
        Food food_돌돔구이 = Food.builder().name("돌돔구이").deleted(false).admin(admin).country(korean).build();
        Food food_복어구이 = Food.builder().name("복어구이").deleted(false).admin(admin).country(korean).build();
        Food food_숭어구이 = Food.builder().name("숭어구이").deleted(false).admin(admin).country(korean).build();
        Food food_송어구이 = Food.builder().name("송어구이").deleted(false).admin(admin).country(korean).build();
        Food food_연어구이 = Food.builder().name("연어구이").deleted(false).admin(admin).country(korean).build();
        Food food_참치구이 = Food.builder().name("참치구이").deleted(false).admin(admin).country(korean).build();
        Food food_홍어구이 = Food.builder().name("홍어구이").deleted(false).admin(admin).country(korean).build();
        Food food_고등어구이 = Food.builder().name("고등어구이").deleted(false).admin(admin).country(korean).build();
        Food food_꽁치구이 = Food.builder().name("꽁치구이").deleted(false).admin(admin).country(korean).build();
        Food food_우럭회 = Food.builder().name("우럭회").deleted(false).admin(admin).country(korean).build();
        Food food_돌돔회 = Food.builder().name("돌돔회").deleted(false).admin(admin).country(korean).build();
        Food food_생새우회 = Food.builder().name("생새우회").deleted(false).admin(admin).country(korean).build();
        Food food_복어회 = Food.builder().name("복어회").deleted(false).admin(admin).country(korean).build();
        Food food_숭어회 = Food.builder().name("숭어회").deleted(false).admin(admin).country(korean).build();
        Food food_송어회 = Food.builder().name("송어회").deleted(false).admin(admin).country(korean).build();
        Food food_연어회 = Food.builder().name("연어회").deleted(false).admin(admin).country(korean).build();
        Food food_참치회 = Food.builder().name("참치회").deleted(false).admin(admin).country(korean).build();
        Food food_오징어회 = Food.builder().name("오징어회").deleted(false).admin(admin).country(korean).build();
        Food food_회무침 = Food.builder().name("회무침").deleted(false).admin(admin).country(korean).build();
        Food food_물회 = Food.builder().name("물회").deleted(false).admin(admin).country(korean).build();
        Food food_홍어회 = Food.builder().name("홍어회").deleted(false).admin(admin).country(korean).build();
        Food food_방어회 = Food.builder().name("방어회").deleted(false).admin(admin).country(korean).build();
        Food food_새우초밥 = Food.builder().name("새우초밥").deleted(false).admin(admin).country(korean).build();
        Food food_연어초밥 = Food.builder().name("연어초밥").deleted(false).admin(admin).country(korean).build();
        Food food_광어초밥 = Food.builder().name("광어초밥").deleted(false).admin(admin).country(korean).build();
        Food food_참치초밥 = Food.builder().name("참치초밥").deleted(false).admin(admin).country(korean).build();
        Food food_오징어초밥 = Food.builder().name("오징어초밥").deleted(false).admin(admin).country(korean).build();
        Food food_계란초밥 = Food.builder().name("계란초밥").deleted(false).admin(admin).country(korean).build();
        Food food_유부초밥 = Food.builder().name("유부초밥").deleted(false).admin(admin).country(korean).build();
        Food food_캘리포니아롤 = Food.builder().name("캘리포니아롤").deleted(false).admin(admin).country(western).build();
        Food food_장어초밥 = Food.builder().name("장어초밥").deleted(false).admin(admin).country(korean).build();
        Food food_대아 = Food.builder().name("대아").deleted(false).admin(admin).country(korean).build();
        Food food_랍스타 = Food.builder().name("랍스타").deleted(false).admin(admin).country(western).build();
        Food food_대게 = Food.builder().name("대게").deleted(false).admin(admin).country(korean).build();
        Food food_조개구이 = Food.builder().name("조개구이").deleted(false).admin(admin).country(korean).build();

        em.persist(food_야채죽);
        em.persist(food_쇠고기야채죽);
        em.persist(food_전복죽);
        em.persist(food_닭죽);
        em.persist(food_참치김밥);
        em.persist(food_돈까스김밥);
        em.persist(food_쇠고기김밥);
        em.persist(food_치즈김밥);
        em.persist(food_소세시김밥);
        em.persist(food_날치알김밥);
        em.persist(food_새우김밥);
        em.persist(food_오므라이스);
        em.persist(food_카레라이스);
        em.persist(food_제육덮밥);
        em.persist(food_비빔밥);
        em.persist(food_차슈덮밥);
        em.persist(food_스테이크덮밥);
        em.persist(food_김치볶음밥);
        em.persist(food_잡채볶음밥);
        em.persist(food_새우볶음밥);
        em.persist(food_낙지볶음밥);
        em.persist(food_카레볶음밥);
        em.persist(food_순대국밥);
        em.persist(food_육개장);
        em.persist(food_설렁탕);
        em.persist(food_삼계탕);
        em.persist(food_순두부찌개);
        em.persist(food_부대찌개);
        em.persist(food_김치찌개);
        em.persist(food_뼈해장국);
        em.persist(food_감자탕);
        em.persist(food_마라탕);
        em.persist(food_안동찜닭);
        em.persist(food_갈비찜);
        em.persist(food_매운탕);
        em.persist(food_알탕);
        em.persist(food_대구탕);
        em.persist(food_닭볶음탕);
        em.persist(food_떡볶이);
        em.persist(food_치즈떡볶이);
        em.persist(food_로제떡볶이);
        em.persist(food_비빔국수);
        em.persist(food_잔치국수);
        em.persist(food_열무국수);
        em.persist(food_막국수);
        em.persist(food_칼국수);
        em.persist(food_수제비);
        em.persist(food_라면);
        em.persist(food_떡라면);
        em.persist(food_만두라면);
        em.persist(food_치즈라면);
        em.persist(food_콩나물라면);
        em.persist(food_라볶이);
        em.persist(food_치즈라볶이);
        em.persist(food_우삼겹부대라면);
        em.persist(food_물냉면);
        em.persist(food_비빔냉면);
        em.persist(food_열무냉면);
        em.persist(food_쫄면);
        em.persist(food_김치쫄면);
        em.persist(food_스파게티);
        em.persist(food_봉골레파스타);
        em.persist(food_알리오올리오);
        em.persist(food_매운우삼겹오일파스타);
        em.persist(food_스테이크크림파스타);
        em.persist(food_해물크림파스타);
        em.persist(food_매운크림파스타);
        em.persist(food_로제크림쉬림프파스타);
        em.persist(food_까르보나라);
        em.persist(food_통소시지토마토파스타);
        em.persist(food_해물토마토파스타);
        em.persist(food_매운우삽겹토마토파스타);
        em.persist(food_토마토파스타);
        em.persist(food_버섯크림뇨끼);
        em.persist(food_뇨끼감바스);
        em.persist(food_라비올리);
        em.persist(food_토르델리니);
        em.persist(food_탈리아텔레);
        em.persist(food_리소토);
        em.persist(food_오레키에테);
        em.persist(food_브루스케타);
        em.persist(food_슈페츨레);
        em.persist(food_마카로니);
        em.persist(food_공갈빵);
        em.persist(food_캄파뉴);
        em.persist(food_꽃빵);
        em.persist(food_단팥빵);
        em.persist(food_도넛);
        em.persist(food_붕어빵);
        em.persist(food_롤빵);
        em.persist(food_소라빵);
        em.persist(food_소보로빵);
        em.persist(food_마늘빵);
        em.persist(food_베이컨토스트);
        em.persist(food_피자토스트);
        em.persist(food_감자토스트);
        em.persist(food_햄버거);
        em.persist(food_와플);
        em.persist(food_팬케이크);
        em.persist(food_크루아상);
        em.persist(food_생크림케익);
        em.persist(food_초코케익);
        em.persist(food_딸기케익);
        em.persist(food_오징어버거);
        em.persist(food_치즈버거);
        em.persist(food_새우버거);
        em.persist(food_데리버거);
        em.persist(food_불고기버거);
        em.persist(food_핫크리스피버거);
        em.persist(food_한우불고기버거);
        em.persist(food_치킨버거);
        em.persist(food_콤비네이션피자);
        em.persist(food_불고기피자);
        em.persist(food_고구마피자);
        em.persist(food_포테이토피자);
        em.persist(food_쉬림프피자);
        em.persist(food_스테이크피자);
        em.persist(food_페퍼로니피자);
        em.persist(food_하와이안피자);
        em.persist(food_돈카츠);
        em.persist(food_치즈돈카츠);
        em.persist(food_고구마돈카츠);
        em.persist(food_생선카츠);
        em.persist(food_새우카츠);
        em.persist(food_로스카츠);
        em.persist(food_히레카츠);
        em.persist(food_돈카츠김치나베);
        em.persist(food_후라이드);
        em.persist(food_양념치킨);
        em.persist(food_간장치킨);
        em.persist(food_시즈닝치킨);
        em.persist(food_바베큐치킨);
        em.persist(food_순살치킨);
        em.persist(food_닭강정);
        em.persist(food_순대);
        em.persist(food_오뎅);
        em.persist(food_감자튀김);
        em.persist(food_고구마튀김);
        em.persist(food_오징어튀김);
        em.persist(food_김말이);
        em.persist(food_오뎅튀김);
        em.persist(food_고로케);
        em.persist(food_새우튀김);
        em.persist(food_김치전);
        em.persist(food_감자전);
        em.persist(food_부추전);
        em.persist(food_해물파전);
        em.persist(food_핫도그);
        em.persist(food_체다치즈핫도그);
        em.persist(food_감자핫도그);
        em.persist(food_통가래떡핫도그);
        em.persist(food_고구마통모짜핫도그);
        em.persist(food_삼겹살);
        em.persist(food_고추장삼겹살);
        em.persist(food_통삼겹);
        em.persist(food_수육);
        em.persist(food_대패삼겹살);
        em.persist(food_족발);
        em.persist(food_곱창);
        em.persist(food_대창);
        em.persist(food_돼지껍데기);
        em.persist(food_돼지갈비);
        em.persist(food_보쌈);
        em.persist(food_삼겹살숙주볶음);
        em.persist(food_소고기미역국);
        em.persist(food_소불고기);
        em.persist(food_소고기장조림);
        em.persist(food_스테이크);
        em.persist(food_닭발);
        em.persist(food_닭발볶음);
        em.persist(food_닭꼬치);
        em.persist(food_닭똥집볶음);
        em.persist(food_훈제오리고기);
        em.persist(food_오리불고기);
        em.persist(food_오리진흙구이);
        em.persist(food_오리양념볶음);
        em.persist(food_양갈비스테이크);
        em.persist(food_케밥);
        em.persist(food_양꼬치);
        em.persist(food_뉴옥스트립);
        em.persist(food_포터하우스);
        em.persist(food_티본스테이크);
        em.persist(food_안심스테이크);
        em.persist(food_립아이);
        em.persist(food_백립);
        em.persist(food_토마호크);
        em.persist(food_우럭구이);
        em.persist(food_돌돔구이);
        em.persist(food_복어구이);
        em.persist(food_숭어구이);
        em.persist(food_송어구이);
        em.persist(food_연어구이);
        em.persist(food_참치구이);
        em.persist(food_홍어구이);
        em.persist(food_고등어구이);
        em.persist(food_꽁치구이);
        em.persist(food_우럭회);
        em.persist(food_돌돔회);
        em.persist(food_생새우회);
        em.persist(food_복어회);
        em.persist(food_숭어회);
        em.persist(food_송어회);
        em.persist(food_연어회);
        em.persist(food_참치회);
        em.persist(food_오징어회);
        em.persist(food_회무침);
        em.persist(food_물회);
        em.persist(food_홍어회);
        em.persist(food_방어회);
        em.persist(food_새우초밥);
        em.persist(food_연어초밥);
        em.persist(food_광어초밥);
        em.persist(food_참치초밥);
        em.persist(food_오징어초밥);
        em.persist(food_계란초밥);
        em.persist(food_유부초밥);
        em.persist(food_캘리포니아롤);
        em.persist(food_장어초밥);
        em.persist(food_대아);
        em.persist(food_랍스타);
        em.persist(food_대게);
        em.persist(food_조개구이);

        // category & food mapping
        category_밥.addFoodMappings(
                food_야채죽,
                food_쇠고기야채죽,
                food_전복죽,
                food_닭죽,
                food_참치김밥,
                food_돈까스김밥,
                food_쇠고기김밥,
                food_치즈김밥,
                food_소세시김밥,
                food_날치알김밥,
                food_새우김밥,
                food_오므라이스,
                food_카레라이스,
                food_제육덮밥,
                food_비빔밥,
                food_차슈덮밥,
                food_스테이크덮밥,
                food_김치볶음밥,
                food_잡채볶음밥,
                food_새우볶음밥,
                food_낙지볶음밥,
                food_카레볶음밥,
                food_순대국밥,
                food_육개장,
                food_설렁탕,
                food_삼계탕,
                food_순두부찌개,
                food_부대찌개,
                food_김치찌개,
                food_뼈해장국,
                food_감자탕,
                food_마라탕,
                food_안동찜닭,
                food_갈비찜,
                food_매운탕,
                food_알탕,
                food_대구탕,
                food_닭볶음탕
        );
        category_죽.addFoodMappings(
                food_야채죽,
                food_쇠고기야채죽,
                food_전복죽,
                food_닭죽
        );
        category_김밥.addFoodMappings(
                food_참치김밥,
                food_돈까스김밥,
                food_쇠고기김밥,
                food_치즈김밥,
                food_소세시김밥,
                food_날치알김밥,
                food_새우김밥

        );
        category_덮밥.addFoodMappings(
                food_오므라이스,
                food_카레라이스,
                food_제육덮밥,
                food_비빔밥,
                food_차슈덮밥,
                food_스테이크덮밥
        );
        category_볶음밥.addFoodMappings(
                food_김치볶음밥,
                food_잡채볶음밥,
                food_새우볶음밥,
                food_낙지볶음밥,
                food_카레볶음밥
        );
        category_국밥.addFoodMappings(
                food_순대국밥,
                food_육개장,
                food_설렁탕,
                food_삼계탕,
                food_순두부찌개,
                food_부대찌개,
                food_김치찌개,
                food_뼈해장국
        );
        category_해장국.addFoodMappings(
                food_뼈해장국
        );
        category_찜탕.addFoodMappings(
                food_감자탕,
                food_마라탕,
                food_안동찜닭,
                food_갈비찜,
                food_매운탕,
                food_알탕,
                food_대구탕,
                food_닭볶음탕
        );
        category_떡.addFoodMappings(
                food_떡볶이,
                food_치즈떡볶이,
                food_로제떡볶이
        );
        category_떡볶이.addFoodMappings(
                food_떡볶이,
                food_치즈떡볶이,
                food_로제떡볶이,

                food_라볶이,
                food_치즈라볶이
        );
        category_면.addFoodMappings(
                food_비빔국수,
                food_잔치국수,
                food_열무국수,
                food_막국수,
                food_칼국수,
                food_라면,
                food_떡라면,
                food_만두라면,
                food_치즈라면,
                food_콩나물라면,
                food_라볶이,
                food_치즈라볶이,
                food_우삼겹부대라면,
                food_물냉면,
                food_비빔냉면,
                food_열무냉면,
                food_쫄면,
                food_김치쫄면,
                food_스파게티,
                food_봉골레파스타,
                food_알리오올리오,
                food_매운우삼겹오일파스타,
                food_스테이크크림파스타,
                food_해물크림파스타,
                food_매운크림파스타,
                food_로제크림쉬림프파스타,
                food_까르보나라,
                food_통소시지토마토파스타,
                food_해물토마토파스타,
                food_매운우삽겹토마토파스타,
                food_토마토파스타
        );
        category_국수.addFoodMappings(
                food_비빔국수,
                food_잔치국수,
                food_열무국수,
                food_막국수,
                food_칼국수
        );
        category_라면.addFoodMappings(
                food_라면,
                food_떡라면,
                food_만두라면,
                food_치즈라면,
                food_콩나물라면,
                food_라볶이,
                food_치즈라볶이
        );
        category_냉면.addFoodMappings(
                food_물냉면,
                food_비빔냉면,
                food_열무냉면
        );
        category_쫄면.addFoodMappings(
                food_쫄면,
                food_김치쫄면
        );
        category_파스타.addFoodMappings(
                food_수제비,
                food_스파게티,
                food_봉골레파스타,
                food_알리오올리오,
                food_매운우삼겹오일파스타,
                food_스테이크크림파스타,
                food_해물크림파스타,
                food_매운크림파스타,
                food_로제크림쉬림프파스타,
                food_까르보나라,
                food_통소시지토마토파스타,
                food_해물토마토파스타,
                food_매운우삽겹토마토파스타,
                food_토마토파스타,
                food_버섯크림뇨끼,
                food_뇨끼감바스,
                food_라비올리,
                food_토르델리니,
                food_탈리아텔레,
                food_리소토,
                food_오레키에테,
                food_브루스케타,
                food_슈페츨레,
                food_마카로니
        );
        category_뇨끼.addFoodMappings(
                food_버섯크림뇨끼,
                food_뇨끼감바스
        );
        category_빵.addFoodMappings(
                food_공갈빵,
                food_캄파뉴,
                food_꽃빵,
                food_단팥빵,
                food_도넛,
                food_붕어빵,
                food_롤빵,
                food_소라빵,
                food_소보로빵,
                food_마늘빵,
                food_베이컨토스트,
                food_피자토스트,
                food_감자토스트,
                food_햄버거,
                food_와플,
                food_팬케이크,
                food_크루아상,
                food_생크림케익,
                food_초코케익,
                food_딸기케익
        );
        category_토스트.addFoodMappings(
                food_베이컨토스트,
                food_피자토스트,
                food_감자토스트
        );
        category_와플.addFoodMappings(
                food_와플
        );
        category_케익.addFoodMappings(
                food_팬케이크,
                food_크루아상,
                food_생크림케익,
                food_초코케익,
                food_딸기케익
        );
        category_도넛.addFoodMappings(
                food_도넛
        );
        category_패스트푸드.addFoodMappings(
                food_불고기버거,
                food_오징어버거,
                food_치즈버거,
                food_새우버거,
                food_데리버거,
                food_핫크리스피버거,
                food_한우불고기버거,
                food_치킨버거,
                food_콤비네이션피자,
                food_불고기피자,
                food_고구마피자,
                food_포테이토피자,
                food_쉬림프피자,
                food_스테이크피자,
                food_페퍼로니피자,
                food_하와이안피자,
                food_후라이드,
                food_양념치킨,
                food_간장치킨,
                food_시즈닝치킨,
                food_바베큐치킨,
                food_순살치킨,
                food_감자튀김
        );
        category_햄버거.addFoodMappings(
                food_불고기버거,
                food_오징어버거,
                food_치즈버거,
                food_새우버거,
                food_데리버거,
                food_핫크리스피버거,
                food_한우불고기버거,
                food_치킨버거
        );
        category_피자.addFoodMappings(
                food_콤비네이션피자,
                food_불고기피자,
                food_고구마피자,
                food_포테이토피자,
                food_쉬림프피자,
                food_스테이크피자,
                food_페퍼로니피자,
                food_하와이안피자
        );
        category_카츠.addFoodMappings(
                food_돈카츠,
                food_치즈돈카츠,
                food_고구마돈카츠,
                food_생선카츠,
                food_새우카츠,
                food_로스카츠,
                food_히레카츠
        );
        category_나베.addFoodMappings(
                food_돈카츠김치나베
        );
        category_치킨.addFoodMappings(
                food_후라이드,
                food_양념치킨,
                food_간장치킨,
                food_시즈닝치킨,
                food_바베큐치킨,
                food_순살치킨
        );
        category_닭강정.addFoodMappings(
                food_닭강정
        );
        category_분식.addFoodMappings(
                food_떡볶이,
                food_치즈떡볶이,
                food_로제떡볶이,

                food_라볶이,
                food_치즈라볶이,
                food_순대,
                food_오뎅,
                food_고구마튀김,
                food_오징어튀김,
                food_새우튀김
        );
        category_튀김.addFoodMappings(
                food_감자튀김,
                food_고구마튀김,
                food_오징어튀김,
                food_김말이,
                food_오뎅튀김,
                food_고로케,
                food_새우튀김

        );
        category_전.addFoodMappings(
                food_김치전,
                food_감자전,
                food_부추전,
                food_해물파전
        );
        category_핫도그.addFoodMappings(
                food_핫도그,
                food_체다치즈핫도그,
                food_감자핫도그,
                food_통가래떡핫도그,
                food_고구마통모짜핫도그
        );
        category_고기.addFoodMappings(
                food_쇠고기야채죽,
                food_닭죽,
                food_돈까스김밥,
                food_쇠고기김밥,
                food_소세시김밥,
                food_제육덮밥,
                food_차슈덮밥,
                food_스테이크덮밥,
                food_카레볶음밥,
                food_육개장,
                food_설렁탕,
                food_삼계탕,
                food_김치찌개,
                food_뼈해장국,
                food_감자탕,
                food_마라탕,
                food_안동찜닭,
                food_갈비찜,
                food_닭볶음탕,
                food_우삼겹부대라면,
                food_매운우삼겹오일파스타,
                food_스테이크크림파스타,
                food_통소시지토마토파스타,
                food_매운우삽겹토마토파스타,
                food_햄버거,
                food_불고기버거,
                food_핫크리스피버거,
                food_한우불고기버거,
                food_치킨버거,
                food_불고기피자,
                food_스테이크피자,
                food_돈카츠,
                food_치즈돈카츠,
                food_고구마돈카츠,
                food_로스카츠,
                food_히레카츠,
                food_돈카츠김치나베,
                food_후라이드,
                food_양념치킨,
                food_간장치킨,
                food_시즈닝치킨,
                food_바베큐치킨,
                food_순살치킨,
                food_핫도그,
                food_체다치즈핫도그,
                food_감자핫도그,
                food_통가래떡핫도그,
                food_고구마통모짜핫도그,

                food_삼겹살,
                food_고추장삼겹살,
                food_통삼겹,
                food_수육,
                food_대패삼겹살,
                food_족발,
                food_곱창,
                food_대창,
                food_돼지껍데기,
                food_돼지갈비,
                food_보쌈,
                food_삼겹살숙주볶음,
                food_소고기미역국,
                food_소불고기,
                food_소고기장조림,
                food_스테이크,
                food_닭발,
                food_닭발볶음,
                food_닭꼬치,
                food_닭똥집볶음,
                food_훈제오리고기,
                food_오리불고기,
                food_오리진흙구이,
                food_오리양념볶음,
                food_양갈비스테이크,
                food_케밥,
                food_양꼬치,
                food_뉴옥스트립,
                food_포터하우스,
                food_티본스테이크,
                food_안심스테이크,
                food_립아이,
                food_백립,
                food_토마호크
        );
        category_돼지고기.addFoodMappings(
                // 추가필요
                food_돈까스김밥,
                food_소세시김밥,
                food_제육덮밥,
                food_차슈덮밥,
                food_설렁탕,
                food_뼈해장국,
                food_감자탕,
                food_마라탕,
                food_갈비찜,
                food_통소시지토마토파스타,
                food_햄버거,
                food_불고기버거,
                food_불고기피자,
                food_돈카츠,
                food_치즈돈카츠,
                food_고구마돈카츠,
                food_로스카츠,
                food_히레카츠,
                food_돈카츠김치나베,
                food_핫도그,
                food_체다치즈핫도그,
                food_감자핫도그,
                food_통가래떡핫도그,
                food_고구마통모짜핫도그,

                food_삼겹살,
                food_고추장삼겹살,
                food_통삼겹,
                food_수육,
                food_대패삼겹살,
                food_족발,
                food_곱창,
                food_대창,
                food_돼지껍데기,
                food_돼지갈비,
                food_보쌈,
                food_삼겹살숙주볶음
        );
        category_소고기.addFoodMappings(
                food_쇠고기야채죽,
                food_쇠고기김밥,
                food_스테이크덮밥,
                food_육개장,
                food_매운우삼겹오일파스타,
                food_스테이크크림파스타,
                food_매운우삽겹토마토파스타,
                food_한우불고기버거,
                food_스테이크피자,
                food_소고기미역국,
                food_소불고기,
                food_소고기장조림,
                food_스테이크
        );
        category_닭고기.addFoodMappings(
                food_닭죽,
                food_삼계탕,
                food_안동찜닭,
                food_닭볶음탕,
                food_치킨버거,
                food_후라이드,
                food_양념치킨,
                food_간장치킨,
                food_시즈닝치킨,
                food_바베큐치킨,
                food_순살치킨,
                food_닭강정,

                food_닭발,
                food_닭발볶음,
                food_닭꼬치,
                food_닭똥집볶음
        );
        category_오리.addFoodMappings(
                food_훈제오리고기,
                food_오리불고기,
                food_오리진흙구이,
                food_오리양념볶음
        );
        category_양고기.addFoodMappings(
                food_양갈비스테이크,
                food_케밥,
                food_양꼬치
        );
        category_스테이크.addFoodMappings(
                food_뉴옥스트립,
                food_포터하우스,
                food_티본스테이크,
                food_안심스테이크,
                food_립아이,
                food_백립,
                food_토마호크
        );
        category_해산물.addFoodMappings(
                food_우럭구이,
                food_돌돔구이,
                food_복어구이,
                food_숭어구이,
                food_송어구이,
                food_연어구이,
                food_참치구이,
                food_홍어구이,
                food_고등어구이,
                food_꽁치구이,
                food_우럭회,
                food_돌돔회,
                food_생새우회,
                food_복어회,
                food_숭어회,
                food_송어회,
                food_연어회,
                food_참치회,
                food_오징어회,
                food_회무침,
                food_물회,
                food_홍어회,
                food_방어회,
                food_새우초밥,
                food_연어초밥,
                food_광어초밥,
                food_참치초밥,
                food_오징어초밥,
                food_계란초밥,
                food_유부초밥,
                food_캘리포니아롤,
                food_장어초밥,
                food_대아,
                food_랍스타,
                food_대게,
                food_조개구이
        );
        category_생선.addFoodMappings(
                food_우럭구이,
                food_돌돔구이,
                food_복어구이,
                food_숭어구이,
                food_송어구이,
                food_연어구이,
                food_참치구이,
                food_홍어구이,
                food_고등어구이,
                food_꽁치구이
        );
        category_회.addFoodMappings(
                food_우럭회,
                food_돌돔회,
                food_생새우회,
                food_복어회,
                food_숭어회,
                food_송어회,
                food_연어회,
                food_참치회,
                food_오징어회,
                food_회무침,
                food_물회,
                food_홍어회,
                food_방어회
        );
        category_초밥.addFoodMappings(
                food_새우초밥,
                food_연어초밥,
                food_광어초밥,
                food_참치초밥,
                food_오징어초밥,
                food_계란초밥,
                food_유부초밥,
                food_캘리포니아롤,
                food_장어초밥
        );
        category_갑각류.addFoodMappings(
                food_대아,
                food_랍스타,
                food_대게
        );
        category_조개류.addFoodMappings(
                food_조개구이
        );
        category_디저트.addFoodMappings(
                food_도넛,
                food_롤빵,
                food_와플,
                food_팬케이크,
                food_생크림케익,
                food_초코케익,
                food_딸기케익
        );
        category_한식.addFoodMappings(
                food_야채죽,
                food_쇠고기야채죽,
                food_전복죽,
                food_닭죽,
                food_참치김밥,
                food_돈까스김밥,
                food_쇠고기김밥,
                food_치즈김밥,
                food_소세시김밥,
                food_날치알김밥,
                food_새우김밥,
                food_오므라이스,
                food_제육덮밥,
                food_비빔밥,
                food_차슈덮밥,
                food_스테이크덮밥,
                food_김치볶음밥,
                food_잡채볶음밥,
                food_새우볶음밥,
                food_낙지볶음밥,
                food_카레볶음밥,
                food_순대국밥,
                food_육개장,
                food_설렁탕,
                food_삼계탕,
                food_순두부찌개,
                food_부대찌개,
                food_김치찌개,
                food_뼈해장국,
                food_감자탕,
                food_안동찜닭,
                food_갈비찜,
                food_매운탕,
                food_알탕,
                food_대구탕,
                food_닭볶음탕,
                food_떡볶이,
                food_치즈떡볶이,
                food_로제떡볶이,
                food_비빔국수,
                food_잔치국수,
                food_열무국수,
                food_막국수,
                food_칼국수,
                food_수제비,
                food_물냉면,
                food_비빔냉면,
                food_열무냉면,
                food_쫄면,
                food_김치쫄면,
                food_순대,
                food_고구마튀김,
                food_오징어튀김,
                food_김말이,
                food_오뎅튀김,
                food_새우튀김,
                food_김치전,
                food_감자전,
                food_부추전,
                food_해물파전,
                food_삼겹살,
                food_고추장삼겹살,
                food_통삼겹,
                food_수육,
                food_대패삼겹살,
                food_족발,
                food_곱창,
                food_대창,
                food_돼지껍데기,
                food_돼지갈비,
                food_보쌈,
                food_삼겹살숙주볶음,
                food_소고기미역국,
                food_소불고기,
                food_소고기장조림,
                food_스테이크,
                food_닭발,
                food_닭발볶음,
                food_닭꼬치,
                food_닭똥집볶음,
                food_훈제오리고기,
                food_오리불고기,
                food_오리진흙구이,
                food_오리양념볶음,
                food_우럭구이,
                food_돌돔구이,
                food_복어구이,
                food_숭어구이,
                food_송어구이,
                food_연어구이,
                food_참치구이,
                food_홍어구이,
                food_고등어구이,
                food_꽁치구이,
                food_우럭회,
                food_돌돔회,
                food_생새우회,
                food_복어회,
                food_숭어회,
                food_송어회,
                food_연어회,
                food_참치회,
                food_오징어회,
                food_회무침,
                food_물회,
                food_홍어회,
                food_방어회,
                food_조개구이
        );
        category_일식.addFoodMappings(
                food_차슈덮밥,
                food_돈카츠,
                food_치즈돈카츠,
                food_고구마돈카츠,
                food_생선카츠,
                food_새우카츠,
                food_로스카츠,
                food_히레카츠,
                food_돈카츠김치나베,
                food_오뎅,
                food_고로케,
                food_새우튀김,
                food_새우초밥,
                food_연어초밥,
                food_광어초밥,
                food_참치초밥,
                food_오징어초밥,
                food_계란초밥,
                food_유부초밥,
                food_장어초밥

        );
        category_중식.addFoodMappings(
                food_마라탕,
                food_양꼬치
        );
        category_양식.addFoodMappings(
                food_도넛,
                food_마늘빵,
                food_베이컨토스트,
                food_피자토스트,
                food_감자토스트,
                food_햄버거,
                food_와플,
                food_팬케이크,
                food_오징어버거,
                food_치즈버거,
                food_새우버거,
                food_데리버거,
                food_불고기버거,
                food_핫크리스피버거,
                food_한우불고기버거,
                food_치킨버거,
                food_체다치즈핫도그,
                food_감자핫도그,
                food_통가래떡핫도그,
                food_고구마통모짜핫도그,
                food_스테이크,
                food_양갈비스테이크,
                food_뉴옥스트립,
                food_포터하우스,
                food_티본스테이크,
                food_안심스테이크,
                food_립아이,
                food_백립,
                food_토마호크,
                food_랍스타
        );
//        category_베트남식.addFoodMappings(
//
//        );
//        category_태국식.addFoodMappings(
//
//        );
        category_이탈리아식.addFoodMappings(
                food_스파게티,
                food_봉골레파스타,
                food_매운우삼겹오일파스타,
                food_알리오올리오,
                food_스테이크크림파스타,
                food_해물크림파스타,
                food_매운크림파스타,
                food_로제크림쉬림프파스타,
                food_까르보나라,
                food_통소시지토마토파스타,
                food_해물토마토파스타,
                food_매운우삽겹토마토파스타,
                food_토마토파스타,
                food_버섯크림뇨끼,
                food_뇨끼감바스,
                food_라비올리,
                food_토르델리니,
                food_탈리아텔레,
                food_리소토,
                food_오레키에테,
                food_브루스케타,
                food_마카로니,
                food_콤비네이션피자,
                food_불고기피자,
                food_고구마피자,
                food_포테이토피자,
                food_쉬림프피자,
                food_스테이크피자,
                food_페퍼로니피자,
                food_하와이안피자
        );
//        food_슈페츨레, // 독일식

//        category_러시안식.addFoodMappings(
//
//        );
        category_프랑스식.addFoodMappings(
                food_감자튀김
                // 그라탕
                // 마카롱
                // 푸아그라
        );


        Tag 단맛 = Tag.builder().name("단맛").status(TagStatus.USE).admin(admin).build();
        Tag 신맛 = Tag.builder().name("신맛").status(TagStatus.USE).admin(admin).build();
        Tag 짠맛 = Tag.builder().name("짠맛").status(TagStatus.USE).admin(admin).build();
        Tag 쓴맛 = Tag.builder().name("쓴맛").status(TagStatus.USE).admin(admin).build();
        Tag 감칠맛 = Tag.builder().name("감칠맛").status(TagStatus.USE).admin(admin).build();
        Tag 차가운 = Tag.builder().name("차가운").status(TagStatus.USE).admin(admin).build();
        Tag 미지근한 = Tag.builder().name("미지근한").status(TagStatus.USE).admin(admin).build();
        Tag 따듯한 = Tag.builder().name("따듯한").status(TagStatus.USE).admin(admin).build();
        Tag 뜨거운 = Tag.builder().name("뜨거운").status(TagStatus.USE).admin(admin).build();
        Tag 순한 = Tag.builder().name("순한").status(TagStatus.USE).admin(admin).build();
        Tag 매운 = Tag.builder().name("매운").status(TagStatus.USE).admin(admin).build();
        Tag 매콤한 = Tag.builder().name("매콤한").status(TagStatus.USE).admin(admin).build();
        Tag 얼큰한 = Tag.builder().name("얼큰한").status(TagStatus.USE).admin(admin).build();
        Tag 달콤한 = Tag.builder().name("달콤한").status(TagStatus.USE).admin(admin).build();
        Tag 달달한 = Tag.builder().name("달달한").status(TagStatus.USE).admin(admin).build();
        Tag 싱거운 = Tag.builder().name("싱거운").status(TagStatus.USE).admin(admin).build();
        Tag 바삭한 = Tag.builder().name("바삭한").status(TagStatus.USE).admin(admin).build();
        Tag 눅눅한 = Tag.builder().name("눅눅한").status(TagStatus.USE).admin(admin).build();
        Tag 부드러운 = Tag.builder().name("부드러운").status(TagStatus.USE).admin(admin).build();
        Tag 쫄깃한 = Tag.builder().name("쫄깃한").status(TagStatus.USE).admin(admin).build();
        Tag 질긴 = Tag.builder().name("질긴").status(TagStatus.USE).admin(admin).build();
        Tag 신선한 = Tag.builder().name("신선한").status(TagStatus.USE).admin(admin).build();
        Tag 기름진 = Tag.builder().name("기름진").status(TagStatus.USE).admin(admin).build();
        Tag 느끼한 = Tag.builder().name("느끼한").status(TagStatus.USE).admin(admin).build();
        Tag 담백한 = Tag.builder().name("담백한").status(TagStatus.USE).admin(admin).build();
        Tag 고칼로리 = Tag.builder().name("고칼로리").status(TagStatus.USE).admin(admin).build();
        Tag 저칼로리 = Tag.builder().name("저칼로리").status(TagStatus.USE).admin(admin).build();
        Tag 든든한 = Tag.builder().name("든든한").status(TagStatus.USE).admin(admin).build();
        Tag 고소한 = Tag.builder().name("고소한").status(TagStatus.USE).admin(admin).build();
        Tag 구수한 = Tag.builder().name("구수한").status(TagStatus.USE).admin(admin).build();
        Tag 건강식 = Tag.builder().name("건강식").status(TagStatus.USE).admin(admin).member(NIBH_FELIS_MEMBER).build();
        Tag 해장 = Tag.builder().name("해장").status(TagStatus.USE).admin(admin).member(NIBH_FELIS_MEMBER).build();
        Tag 시원한 = Tag.builder().name("시원한").status(TagStatus.USE).admin(admin).member(NIBH_FELIS_MEMBER).build();
        Tag 깔끔한 = Tag.builder().name("깔끔한").status(TagStatus.USE).admin(admin).member(NIBH_FELIS_MEMBER).build();
        Tag 비린 = Tag.builder().name("비린").status(TagStatus.USE).admin(admin).member(NIBH_FELIS_MEMBER).build();
        Tag 밤에_먹기_좋은 = Tag.builder().name("밤에 먹기 좋은").status(TagStatus.USE).admin(admin).member(NIBH_FELIS_MEMBER).build();
        Tag 퇴근_후_먹기_좋은 = Tag.builder().name("퇴근 후 먹기 좋은").status(TagStatus.USE).admin(admin).member(NIBH_FELIS_MEMBER).build();
        Tag 옛날_음식 = Tag.builder().name("옛날 음식").status(TagStatus.USE).admin(admin).member(NIBH_FELIS_MEMBER).build();
        Tag 아플_때_먹기좋은 = Tag.builder().name("아플 때 먹기좋은").status(TagStatus.USE).admin(admin).member(NIBH_FELIS_MEMBER).build();

        em.persist(단맛);
        em.persist(신맛);
        em.persist(짠맛);
        em.persist(쓴맛);
        em.persist(감칠맛);
        em.persist(차가운);
        em.persist(미지근한);
        em.persist(따듯한);
        em.persist(뜨거운);
        em.persist(순한);
        em.persist(매운);
        em.persist(매콤한);
        em.persist(얼큰한);
        em.persist(달콤한);
        em.persist(달달한);
        em.persist(싱거운);
        em.persist(바삭한);
        em.persist(눅눅한);
        em.persist(부드러운);
        em.persist(쫄깃한);
        em.persist(질긴);
        em.persist(신선한);
        em.persist(기름진);
        em.persist(느끼한);
        em.persist(담백한);
        em.persist(고칼로리);
        em.persist(저칼로리);
        em.persist(든든한);
        em.persist(고소한);
        em.persist(구수한);
        em.persist(건강식);
        em.persist(해장);
        em.persist(시원한);
        em.persist(깔끔한);
        em.persist(비린);
        em.persist(밤에_먹기_좋은);
        em.persist(퇴근_후_먹기_좋은);
        em.persist(옛날_음식);
        em.persist(아플_때_먹기좋은);


        food_야채죽.addFoodTags(
                짠맛,
                뜨거운,
                아플_때_먹기좋은
        );
        food_쇠고기야채죽.addFoodTags(
                짠맛,
                뜨거운,
                부드러운,
                담백한,
                아플_때_먹기좋은
        );
        food_전복죽.addFoodTags(
                짠맛,
                뜨거운,
                건강식,
                아플_때_먹기좋은
        );
        food_닭죽.addFoodTags(
                짠맛,
                뜨거운,
                부드러운,
                담백한,
                아플_때_먹기좋은
        );
        food_참치김밥.addFoodTags(
                짠맛,
                기름진,
                고소한
        );
        food_돈까스김밥.addFoodTags(
                짠맛,
                달콤한,
                바삭한,
                기름진,
                느끼한,
                담백한,
                든든한,
                고소한
        );
        food_쇠고기김밥.addFoodTags(
                짠맛,
                담백한,
                고소한
        );
        food_치즈김밥.addFoodTags(
                짠맛,
                고소한
        );
        food_소세시김밥.addFoodTags(
                짠맛,
                기름진,
                고소한
        );
        food_날치알김밥.addFoodTags(
                짠맛,
                고소한
        );
        food_새우김밥.addFoodTags(
                짠맛,
                고소한
        );
        food_오므라이스.addFoodTags(
                단맛,
                짠맛,
                고소한
        );
        food_카레라이스.addFoodTags(
                매운
        );
        food_제육덮밥.addFoodTags(
                매운,
                기름진,
                담백한
        );
        food_비빔밥.addFoodTags(
                짠맛,
                매콤한,
                기름진,
                고소한,
                밤에_먹기_좋은
        );
        food_차슈덮밥.addFoodTags(
                단맛,
                짠맛,
                담백한,
                고소한
        );
        food_스테이크덮밥.addFoodTags(
                단맛,
                짠맛,
                따듯한,
                뜨거운,
                담백한
        );
        food_김치볶음밥.addFoodTags(
                짠맛,
                매콤한,
                기름진,
                고소한
        );
        food_잡채볶음밥.addFoodTags(
                짠맛,
                기름진,
                고소한
        );
        food_새우볶음밥.addFoodTags(
                짠맛,
                고소한
        );
        food_낙지볶음밥.addFoodTags(
                매운,
                고소한
        );
        food_카레볶음밥.addFoodTags(
                매운
        );
        food_순대국밥.addFoodTags(
                짠맛,
                뜨거운,
                담백한,
                든든한,
                구수한,
                건강식,
                해장
        );
        food_육개장.addFoodTags(
                짠맛,
                뜨거운,
                얼큰한,
                담백한,
                든든한,
                구수한
        );
        food_설렁탕.addFoodTags(
                짠맛,
                뜨거운,
                든든한,
                구수한,
                건강식
        );
        food_삼계탕.addFoodTags(
                짠맛,
                뜨거운,
                기름진,
                담백한,
                든든한,
                구수한,
                건강식
        );
        food_순두부찌개.addFoodTags(
                짠맛,
                뜨거운,
                얼큰한,
                구수한,
                해장
        );
        food_부대찌개.addFoodTags(
                짠맛,
                뜨거운,
                매운,
                얼큰한,
                달콤한,
                든든한
        );
        food_김치찌개.addFoodTags(
                짠맛,
                뜨거운,
                매운,
                달콤한
        );
        food_뼈해장국.addFoodTags(
                짠맛,
                뜨거운,
                매운,
                얼큰한,
                기름진,
                담백한,
                든든한,
                구수한,
                해장
        );
        food_감자탕.addFoodTags(
                짠맛,
                뜨거운,
                매운,
                얼큰한,
                기름진,
                담백한,
                든든한,
                구수한,
                해장
        );
        food_마라탕.addFoodTags(
                뜨거운,
                매운,
                기름진,
                담백한
        );
        food_안동찜닭.addFoodTags(
                단맛,
                짠맛,
                뜨거운,
                매콤한,
                기름진,
                담백한,
                든든한,
                구수한
        );
        food_갈비찜.addFoodTags(
                단맛,
                짠맛,
                기름진,
                담백한
        );
        food_매운탕.addFoodTags(
                짠맛,
                뜨거운,
                매운,
                얼큰한,
                해장
        );
        food_알탕.addFoodTags(
                짠맛,
                뜨거운,
                매운,
                얼큰한,
                해장
        );
        food_대구탕.addFoodTags(
                짠맛,
                뜨거운,
                담백한,
                해장
        );
        food_닭볶음탕.addFoodTags(
                단맛,
                짠맛,
                뜨거운,
                매콤한,
                달콤한,
                담백한,
                든든한
        );
        food_떡볶이.addFoodTags(
                단맛,
                매콤한,
                달콤한,
                쫄깃한
        );
        food_치즈떡볶이.addFoodTags(
                단맛,
                짠맛,
                매콤한,
                달콤한,
                쫄깃한,
                느끼한,
                담백한
        );
        food_로제떡볶이.addFoodTags(
                단맛,
                짠맛,
                매콤한,
                달콤한,
                쫄깃한,
                느끼한,
                담백한
        );
        food_비빔국수.addFoodTags(
                신맛,
                짠맛,
                차가운,
                매콤한,
                달콤한,
                쫄깃한
        );
        food_잔치국수.addFoodTags(
                짠맛,
                뜨거운,
                쫄깃한,
                구수한
        );
        food_열무국수.addFoodTags(
                차가운,
                매운,
                쫄깃한,
                시원한,
                깔끔한
        );
        food_막국수.addFoodTags(
                신맛,
                차가운,
                쫄깃한,
                시원한,
                깔끔한
        );
        food_칼국수.addFoodTags(
                짠맛,
                매콤한,
                얼큰한,
                담백한,
                구수한
        );
        food_수제비.addFoodTags(
                짠맛,
                매콤한,
                얼큰한,
                담백한,
                구수한
        );
        food_라면.addFoodTags(
                뜨거운,
                매운,
                얼큰한,
                기름진,
                해장
        );
        food_떡라면.addFoodTags(
                뜨거운,
                매운,
                얼큰한,
                쫄깃한,
                기름진,
                해장
        );
        food_만두라면.addFoodTags(
                뜨거운,
                매운,
                얼큰한,
                담백한,
                든든한,
                구수한,
                해장
        );
        food_치즈라면.addFoodTags(
                짠맛,
                뜨거운,
                순한,
                느끼한,
                담백한
        );
        food_콩나물라면.addFoodTags(
                뜨거운,
                매운,
                얼큰한,
                구수한,
                해장,
                깔끔한
        );
        food_라볶이.addFoodTags(
                단맛,
                뜨거운,
                매콤한,
                달콤한,
                쫄깃한
        );
        food_치즈라볶이.addFoodTags(
                단맛,
                뜨거운,
                매콤한,
                달콤한,
                쫄깃한,
                느끼한,
                담백한
        );
        food_우삼겹부대라면.addFoodTags(
                따듯한,
                매운,
                얼큰한,
                담백한,
                든든한,
                구수한,
                해장
        );
        food_물냉면.addFoodTags(
                신맛,
                차가운,
                쫄깃한,
                시원한

        );
        food_비빔냉면.addFoodTags(
                신맛,
                차가운,
                쫄깃한,
                매콤한,
                시원한
        );
        food_열무냉면.addFoodTags(
                신맛,
                차가운,
                매운,
                쫄깃한,
                시원한,
                깔끔한
        );
        food_쫄면.addFoodTags(
                신맛,
                매콤한,
                쫄깃한,
                시원한
        );
        food_김치쫄면.addFoodTags(
                신맛,
                매콤한,
                쫄깃한,
                시원한
        );
        food_스파게티.addFoodTags(
                단맛,
                짠맛,
                쫄깃한
        );
        food_봉골레파스타.addFoodTags(
                짠맛,
                쫄깃한,
                기름진,
                느끼한,
                담백한
        );
        food_알리오올리오.addFoodTags(
                짠맛,
                쫄깃한,
                기름진,
                느끼한,
                담백한
        );
        food_매운우삼겹오일파스타.addFoodTags(
                매운,
                쫄깃한,
                기름진,
                담백한
        );
        food_스테이크크림파스타.addFoodTags(
                단맛,
                짠맛,
                부드러운,
                쫄깃한,
                담백한
        );
        food_해물크림파스타.addFoodTags(
                짠맛,
                쫄깃한,
                기름진,
                느끼한,
                담백한
        );
        food_매운크림파스타.addFoodTags(
                짠맛,
                매운,
                쫄깃한,
                신선한,
                기름진,
                느끼한,
                담백한
        );
        food_로제크림쉬림프파스타.addFoodTags(
                짠맛,
                매콤한,
                부드러운,
                쫄깃한,
                기름진,
                느끼한,
                담백한
        );
        food_까르보나라.addFoodTags(
                단맛,
                달콤한,
                쫄깃한,
                기름진,
                느끼한,
                담백한
        );
        food_통소시지토마토파스타.addFoodTags(
                짠맛,
                쫄깃한,
                느끼한,
                담백한
        );
        food_해물토마토파스타.addFoodTags(
                짠맛,
                쫄깃한
        );
        food_매운우삽겹토마토파스타.addFoodTags(
                짠맛,
                매운,
                쫄깃한,
                기름진,
                느끼한,
                담백한
        );
        food_토마토파스타.addFoodTags(
                짠맛,
                쫄깃한
        );
        food_버섯크림뇨끼.addFoodTags(
                따듯한,
                느끼한
        );
        food_뇨끼감바스.addFoodTags(
                뜨거운,
                매콤한,
                얼큰한,
                부드러운,
                담백한
        );
        food_라비올리.addFoodTags(
                부드러운,
                담백한,
                고소한
        );
        food_토르델리니.addFoodTags(
                단맛,
                달콤한,
                부드러운,
                느끼한,
                담백한,
                고소한
        );
        food_탈리아텔레.addFoodTags(
                느끼한,
                담백한
        );
        food_리소토.addFoodTags(
                단맛,
                짠맛,
                매콤한,
                느끼한,
                담백한,
                고소한
        );
        food_오레키에테.addFoodTags(
                담백한
        );
        food_브루스케타.addFoodTags(
                짠맛,
                바삭한,
                고소한
        );
        food_슈페츨레.addFoodTags(
                달콤한,
                담백한,
                고소한
        );
        food_마카로니.addFoodTags(
                단맛,
                달콤한,
                부드러운,
                쫄깃한,
                느끼한,
                담백한
        );
        food_공갈빵.addFoodTags(
                단맛,
                바삭한
        );
        food_캄파뉴.addFoodTags(
                바삭한
        );
        food_꽃빵.addFoodTags(
                부드러운
        );
        food_단팥빵.addFoodTags(
                단맛,
                부드러운
        );
        food_도넛.addFoodTags(
                단맛,
                부드러운,
                기름진
        );
        food_붕어빵.addFoodTags(
                단맛,
                부드러운
        );
        food_롤빵.addFoodTags(
                단맛,
                부드러운
        );
        food_소라빵.addFoodTags(
                단맛,
                부드러운
        );
        food_소보로빵.addFoodTags(
                단맛,
                부드러운
        );
        food_마늘빵.addFoodTags(
                바삭한
        );
        food_베이컨토스트.addFoodTags(
                단맛,
                짠맛,
                부드러운,
                기름진,
                느끼한,
                담백한,
                고소한
        );
        food_피자토스트.addFoodTags(
                단맛,
                짠맛,
                부드러운,
                기름진,
                느끼한,
                담백한,
                고소한
        );
        food_감자토스트.addFoodTags(
                단맛,
                짠맛,
                달콤한,
                부드러운,
                기름진,
                느끼한,
                담백한
        );
        food_햄버거.addFoodTags(
                단맛,
                짠맛,
                부드러운,
                기름진,
                느끼한,
                담백한
        );
        food_와플.addFoodTags(
                단맛,
                달콤한,
                달달한,
                바삭한
        );
        food_팬케이크.addFoodTags(
                단맛,
                달콤한,
                달달한,
                바삭한
        );
        food_크루아상.addFoodTags(
                부드러운
        );
        food_생크림케익.addFoodTags(
                단맛,
                달달한,
                느끼한,
                부드러운
        );
        food_초코케익.addFoodTags(
                단맛,
                달달한,
                느끼한,
                부드러운
        );
        food_딸기케익.addFoodTags(
                단맛,
                달달한,
                느끼한,
                부드러운
        );
        food_오징어버거.addFoodTags(
                단맛,
                부드러운,
                고칼로리
        );
        food_치즈버거.addFoodTags(
                단맛,
                짠맛,
                부드러운,
                담백한,
                고칼로리
        );
        food_새우버거.addFoodTags(
                짠맛,
                바삭한,
                고칼로리
        );
        food_데리버거.addFoodTags(
                단맛,
                짠맛,
                부드러운,
                느끼한,
                담백한,
                고칼로리
        );
        food_불고기버거.addFoodTags(
                단맛,
                짠맛,
                부드러운,
                느끼한,
                담백한,
                고칼로리
        );
        food_핫크리스피버거.addFoodTags(
                짠맛,
                매운,
                바삭한,
                부드러운,
                느끼한,
                담백한,
                고칼로리
        );
        food_한우불고기버거.addFoodTags(
                단맛,
                짠맛,
                부드러운,
                기름진,
                느끼한,
                담백한,
                고칼로리
        );
        food_치킨버거.addFoodTags(
                짠맛,
                바삭한,
                부드러운,
                기름진,
                느끼한,
                담백한,
                고칼로리
        );
        food_콤비네이션피자.addFoodTags(
                단맛,
                짠맛,
                부드러운,
                쫄깃한,
                기름진,
                느끼한,
                담백한,
                고칼로리
        );
        food_불고기피자.addFoodTags(
                단맛,
                짠맛,
                달콤한,
                부드러운,
                기름진,
                느끼한,
                담백한,
                고칼로리
        );
        food_고구마피자.addFoodTags(
                단맛,
                짠맛,
                부드러운,
                기름진,
                느끼한,
                담백한,
                고칼로리
        );
        food_포테이토피자.addFoodTags(
                짠맛,
                부드러운,
                기름진,
                느끼한,
                담백한,
                고칼로리
        );
        food_쉬림프피자.addFoodTags(
                단맛,
                짠맛,
                매콤한,
                부드러운,
                기름진,
                느끼한,
                담백한,
                고칼로리
        );
        food_스테이크피자.addFoodTags(
                단맛,
                짠맛,
                부드러운,
                기름진,
                느끼한,
                담백한,
                고칼로리
        );
        food_페퍼로니피자.addFoodTags(
                짠맛,
                부드러운,
                기름진,
                느끼한,
                고칼로리
        );
        food_하와이안피자.addFoodTags(
                단맛,
                짠맛,
                부드러운,
                기름진,
                담백한,
                고칼로리
        );
        food_돈카츠.addFoodTags(
                단맛,
                달콤한,
                바삭한,
                기름진,
                느끼한,
                담백한,
                고칼로리
        );
        food_치즈돈카츠.addFoodTags(
                단맛,
                짠맛,
                달콤한,
                바삭한,
                부드러운,
                쫄깃한,
                기름진,
                느끼한,
                담백한,
                고칼로리
        );
        food_고구마돈카츠.addFoodTags(
                단맛,
                달콤한,
                바삭한,
                기름진,
                느끼한,
                담백한,
                고칼로리
        );
        food_생선카츠.addFoodTags(
                단맛,
                달콤한,
                바삭한,
                기름진,
                느끼한,
                고칼로리
        );
        food_새우카츠.addFoodTags(
                단맛,
                달콤한,
                바삭한,
                기름진,
                느끼한,
                고칼로리
        );
        food_로스카츠.addFoodTags(
                단맛,
                달콤한,
                바삭한,
                기름진,
                느끼한,
                담백한,
                고칼로리
        );
        food_히레카츠.addFoodTags(
                단맛,
                달콤한,
                바삭한,
                기름진,
                느끼한,
                담백한,
                고칼로리
        );
        food_돈카츠김치나베.addFoodTags(
                단맛,
                짠맛,
                뜨거운,
                매운,
                바삭한,
                눅눅한,
                기름진,
                느끼한,
                담백한,
                고칼로리
        );
        food_후라이드.addFoodTags(
                짠맛,
                바삭한,
                기름진,
                느끼한,
                담백한
        );
        food_양념치킨.addFoodTags(
                단맛,
                짠맛,
                매운,
                매콤한,
                눅눅한,
                기름진,
                느끼한,
                담백한,
                고칼로리
        );
        food_간장치킨.addFoodTags(
                단맛,
                짠맛,
                달콤한,
                눅눅한,
                기름진,
                느끼한,
                담백한,
                고칼로리
        );
        food_시즈닝치킨.addFoodTags(
                짠맛,
                매콤한,
                바삭한,
                기름진,
                느끼한,
                담백한,
                고칼로리
        );
        food_바베큐치킨.addFoodTags(
                단맛,
                짠맛,
                매콤한,
                눅눅한,
                기름진,
                느끼한,
                담백한,
                고칼로리
        );
        food_순살치킨.addFoodTags(
                짠맛,
                바삭한,
                기름진,
                느끼한,
                담백한,
                고칼로리
        );
        food_닭강정.addFoodTags(
                단맛,
                짠맛,
                매콤한,
                눅눅한,
                기름진,
                느끼한,
                담백한,
                고칼로리
        );
        food_순대.addFoodTags(
                부드러운,
                담백한
        );
        food_오뎅.addFoodTags(
                단맛,
                짠맛,
                뜨거운,
                얼큰한,
                담백한,
                구수한
        );
        food_감자튀김.addFoodTags(
                짠맛,
                바삭한,
                기름진,
                느끼한
        );
        food_고구마튀김.addFoodTags(
                단맛,
                짠맛,
                바삭한,
                기름진,
                느끼한
        );
        food_오징어튀김.addFoodTags(
                짠맛,
                바삭한,
                기름진,
                느끼한
        );
        food_김말이.addFoodTags(
                짠맛,
                바삭한,
                기름진,
                느끼한
        );
        food_오뎅튀김.addFoodTags(
                단맛,
                짠맛,
                바삭한,
                기름진,
                담백한,
                느끼한
        );
        food_고로케.addFoodTags(
                단맛,
                짠맛,
                뜨거운,
                바삭한,
                기름진,
                느끼한,
                담백한,
                고칼로리
        );
        food_새우튀김.addFoodTags(
                짠맛,
                바삭한,
                부드러운,
                쫄깃한,
                기름진,
                느끼한,
                담백한,
                고칼로리
        );
        food_김치전.addFoodTags(
                짠맛,
                매운,
                바삭한,
                부드러운,
                기름진,
                담백한
        );
        food_감자전.addFoodTags(
                단맛,
                짠맛,
                부드러운,
                기름진,
                느끼한,
                담백한,
                고칼로리
        );
        food_부추전.addFoodTags(
                짠맛,
                매운,
                바삭한,
                부드러운,
                기름진,
                느끼한,
                담백한
        );
        food_해물파전.addFoodTags(
                짠맛,
                매운,
                바삭한,
                부드러운,
                기름진,
                느끼한,
                담백한
        );
        food_핫도그.addFoodTags(
                단맛,
                짠맛,
                달콤한,
                바삭한,
                쫄깃한,
                기름진,
                느끼한,
                담백한,
                고칼로리
        );
        food_체다치즈핫도그.addFoodTags(
                단맛,
                짠맛,
                달콤한,
                바삭한,
                부드러운,
                쫄깃한,
                기름진,
                느끼한,
                담백한,
                고칼로리
        );
        food_감자핫도그.addFoodTags(
                단맛,
                짠맛,
                달콤한,
                바삭한,
                부드러운,
                쫄깃한,
                기름진,
                느끼한,
                담백한,
                고칼로리
        );
        food_통가래떡핫도그.addFoodTags(
                단맛,
                짠맛,
                달콤한,
                바삭한,
                쫄깃한,
                기름진,
                느끼한,
                담백한,
                고칼로리
        );
        food_고구마통모짜핫도그.addFoodTags(
                단맛,
                짠맛,
                달콤한,
                바삭한,
                부드러운,
                쫄깃한,
                기름진,
                느끼한,
                담백한,
                고칼로리
        );
        food_삼겹살.addFoodTags(
                짠맛,
                감칠맛,
                뜨거운,
                기름진,
                느끼한,
                담백한,
                든든한
        );
        food_고추장삼겹살.addFoodTags(
                짠맛,
                뜨거운,
                매운,
                매콤한,
                기름진,
                느끼한,
                담백한,
                고칼로리,
                든든한
        );
        food_통삼겹.addFoodTags(
                짠맛,
                감칠맛,
                뜨거운,
                기름진,
                느끼한,
                담백한,
                든든한
        );
        food_수육.addFoodTags(
                짠맛,
                뜨거운,
                부드러운,
                기름진,
                느끼한,
                담백한,
                고칼로리,
                든든한
        );
        food_대패삼겹살.addFoodTags(
                짠맛,
                뜨거운,
                기름진,
                느끼한,
                담백한,
                고칼로리
        );
        food_족발.addFoodTags(
                짠맛,
                뜨거운,
                부드러운,
                기름진,
                느끼한,
                담백한,
                고칼로리,
                든든한,
                밤에_먹기_좋은
        );
        food_곱창.addFoodTags(
                짠맛,
                뜨거운,
                부드러운,
                기름진,
                느끼한,
                담백한,
                고칼로리,
                든든한
        );
        food_대창.addFoodTags(
                짠맛,
                뜨거운,
                부드러운,
                기름진,
                느끼한,
                담백한,
                고칼로리,
                든든한
        );
        food_돼지껍데기.addFoodTags(
                짠맛,
                뜨거운,
                쫄깃한,
                기름진,
                느끼한,
                담백한
        );
        food_돼지갈비.addFoodTags(
                단맛,
                짠맛,
                뜨거운,
                달콤한,
                부드러운,
                기름진,
                느끼한,
                담백한
        );
        food_보쌈.addFoodTags(
                짠맛,
                뜨거운,
                부드러운,
                기름진,
                느끼한,
                담백한
        );
        food_삼겹살숙주볶음.addFoodTags(
                단맛,
                짠맛,
                뜨거운,
                부드러운,
                기름진,
                느끼한,
                담백한
        );
        food_소고기미역국.addFoodTags(
                짠맛,
                뜨거운,
                담백한,
                구수한,
                시원한
        );
        food_소불고기.addFoodTags(
                단맛,
                짠맛,
                뜨거운,
                매콤한,
                부드러운,
                기름진,
                느끼한,
                담백한
        );
        food_소고기장조림.addFoodTags(
                단맛,
                짠맛,
                부드러운,
                담백한
        );
        food_스테이크.addFoodTags(
                단맛,
                짠맛,
                뜨거운,
                부드러운,
                쫄깃한,
                기름진,
                느끼한,
                담백한
        );
        food_닭발.addFoodTags(
                매운,
                매콤한,
                기름진,
                담백한
        );
        food_닭발볶음.addFoodTags(
                매운,
                매콤한,
                기름진,
                담백한
        );
        food_닭꼬치.addFoodTags(
                단맛,
                매콤한,
                달콤한,
                부드러운,
                기름진,
                느끼한,
                담백한
        );
        food_닭똥집볶음.addFoodTags(
                매콤한,
                기름진,
                담백한
        );
        food_훈제오리고기.addFoodTags(
                짠맛,
                부드러운,
                기름진,
                느끼한,
                담백한
        );
        food_오리불고기.addFoodTags(
                단맛,
                짠맛,
                매콤한,
                부드러운,
                기름진,
                담백한
        );
        food_오리진흙구이.addFoodTags(
                짠맛,
                뜨거운,
                부드러운,
                기름진,
                느끼한,
                담백한
        );
        food_오리양념볶음.addFoodTags(
                뜨거운,
                매운,
                매콤한,
                부드러운,
                기름진,
                느끼한,
                담백한
        );
        food_양갈비스테이크.addFoodTags(
                단맛,
                짠맛,
                뜨거운,
                달콤한,
                부드러운,
                기름진,
                느끼한,
                담백한
        );
        food_케밥.addFoodTags(
                매운,
                매콤한,
                부드러운,
                기름진,
                담백한
        );
        food_양꼬치.addFoodTags(
                매운,
                매콤한,
                부드러운,
                기름진,
                담백한,
                비린
        );
        food_뉴옥스트립.addFoodTags(
                단맛,
                짠맛,
                달콤한,
                부드러운,
                쫄깃한,
                기름진,
                느끼한,
                담백한
        );
        food_포터하우스.addFoodTags(
                단맛,
                짠맛,
                달콤한,
                부드러운,
                쫄깃한,
                기름진,
                느끼한,
                담백한
        );
        food_티본스테이크.addFoodTags(
                단맛,
                짠맛,
                달콤한,
                부드러운,
                쫄깃한,
                기름진,
                느끼한,
                담백한
        );
        food_안심스테이크.addFoodTags(
                단맛,
                짠맛,
                달콤한,
                부드러운,
                쫄깃한,
                기름진,
                느끼한,
                담백한
        );
        food_립아이.addFoodTags(
                단맛,
                짠맛,
                달콤한,
                부드러운,
                쫄깃한,
                기름진,
                느끼한,
                담백한
        );
        food_백립.addFoodTags(
                단맛,
                짠맛,
                달콤한,
                부드러운,
                쫄깃한,
                기름진,
                느끼한,
                담백한
        );
        food_토마호크.addFoodTags(
                단맛,
                짠맛,
                달콤한,
                부드러운,
                쫄깃한,
                기름진,
                느끼한,
                담백한
        );
        food_우럭구이.addFoodTags(
                감칠맛,
                뜨거운,
                부드러운,
                쫄깃한,
                담백한,
                비린
        );
        food_돌돔구이.addFoodTags(
                감칠맛,
                뜨거운,
                부드러운,
                쫄깃한,
                담백한,
                비린
        );
        food_복어구이.addFoodTags(
                감칠맛,
                뜨거운,
                부드러운,
                쫄깃한,
                담백한,
                비린
        );
        food_숭어구이.addFoodTags(
                감칠맛,
                뜨거운,
                부드러운,
                쫄깃한,
                담백한,
                비린
        );
        food_송어구이.addFoodTags(
                감칠맛,
                뜨거운,
                부드러운,
                쫄깃한,
                담백한,
                비린
        );
        food_연어구이.addFoodTags(
                감칠맛,
                뜨거운,
                부드러운,
                쫄깃한,
                담백한,
                비린
        );
        food_참치구이.addFoodTags(
                감칠맛,
                뜨거운,
                부드러운,
                쫄깃한,
                담백한,
                비린
        );
        food_홍어구이.addFoodTags(
                감칠맛,
                뜨거운,
                부드러운,
                쫄깃한,
                담백한,
                비린
        );
        food_고등어구이.addFoodTags(
                감칠맛,
                뜨거운,
                부드러운,
                쫄깃한,
                담백한,
                비린
        );
        food_꽁치구이.addFoodTags(
                감칠맛,
                뜨거운,
                부드러운,
                쫄깃한,
                담백한,
                비린
        );
        food_우럭회.addFoodTags(
                감칠맛,
                차가운,
                부드러운,
                쫄깃한,
                담백한,
                비린
        );
        food_돌돔회.addFoodTags(
                감칠맛,
                차가운,
                부드러운,
                쫄깃한,
                담백한,
                비린
        );
        food_생새우회.addFoodTags(
                감칠맛,
                차가운,
                부드러운,
                쫄깃한,
                담백한,
                비린
        );
        food_복어회.addFoodTags(
                감칠맛,
                차가운,
                부드러운,
                쫄깃한,
                담백한,
                비린
        );
        food_숭어회.addFoodTags(
                감칠맛,
                차가운,
                부드러운,
                쫄깃한,
                담백한,
                비린
        );
        food_송어회.addFoodTags(
                감칠맛,
                차가운,
                부드러운,
                쫄깃한,
                담백한,
                비린
        );
        food_연어회.addFoodTags(
                감칠맛,
                차가운,
                부드러운,
                쫄깃한,
                담백한,
                비린
        );
        food_참치회.addFoodTags(
                감칠맛,
                차가운,
                부드러운,
                쫄깃한,
                담백한,
                비린
        );
        food_오징어회.addFoodTags(
                감칠맛,
                차가운,
                부드러운,
                쫄깃한,
                담백한,
                비린
        );
        food_회무침.addFoodTags(
                감칠맛,
                차가운,
                부드러운,
                쫄깃한,
                담백한,
                비린
        );
        food_물회.addFoodTags(
                감칠맛,
                차가운,
                부드러운,
                쫄깃한,
                담백한,
                비린
        );
        food_홍어회.addFoodTags(
                감칠맛,
                차가운,
                부드러운,
                쫄깃한,
                담백한,
                비린
        );
        food_방어회.addFoodTags(
                감칠맛,
                차가운,
                부드러운,
                쫄깃한,
                담백한,
                비린
        );
        food_새우초밥.addFoodTags(
                감칠맛,
                차가운,
                부드러운,
                쫄깃한,
                담백한,
                비린
        );
        food_연어초밥.addFoodTags(
                감칠맛,
                차가운,
                부드러운,
                쫄깃한,
                담백한,
                비린
        );
        food_광어초밥.addFoodTags(
                감칠맛,
                차가운,
                부드러운,
                쫄깃한,
                담백한,
                비린
        );
        food_참치초밥.addFoodTags(
                감칠맛,
                차가운,
                부드러운,
                쫄깃한,
                담백한,
                비린
        );
        food_오징어초밥.addFoodTags(
                감칠맛,
                차가운,
                부드러운,
                쫄깃한,
                담백한,
                비린
        );
        food_계란초밥.addFoodTags(
                감칠맛,
                차가운,
                부드러운,
                쫄깃한,
                담백한,
                비린
        );
        food_유부초밥.addFoodTags(
                감칠맛,
                차가운,
                부드러운,
                쫄깃한,
                담백한,
                비린
        );
        food_캘리포니아롤.addFoodTags(
                감칠맛,
                차가운,
                부드러운,
                쫄깃한,
                담백한,
                비린
        );
        food_장어초밥.addFoodTags(
                감칠맛,
                차가운,
                부드러운,
                쫄깃한,
                담백한,
                비린
        );
        food_대아.addFoodTags(
                감칠맛,
                뜨거운,
                부드러운,
                쫄깃한,
                담백한,
                비린
        );
        food_랍스타.addFoodTags(
                감칠맛,
                뜨거운,
                부드러운,
                쫄깃한,
                담백한,
                비린
        );
        food_대게.addFoodTags(
                감칠맛,
                뜨거운,
                부드러운,
                쫄깃한,
                담백한,
                비린
        );
        food_조개구이.addFoodTags(
                감칠맛,
                뜨거운,
                부드러운,
                쫄깃한,
                담백한,
                비린
        );


        Post post_야채죽 = Post.builder().food(food_야채죽).title("야채죽").content("본죽").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://cdn.bonif.co.kr/cmdt/detail/BF101_dt_main_10000275.jpg").build()).build();
        Post post_쇠고기야채죽 = Post.builder().food(food_쇠고기야채죽).title("쇠고기 야채죽").content("본죽").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("http://image.auction.co.kr/itemimage/15/24/8b/15248b91b1.jpg").build()).build();
        Post post_전복죽 = Post.builder().food(food_전복죽).title("전복죽").content("본죽").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://cdn.bonif.co.kr/cmdt/BF101_pic_ywpBSwEl.jpg").build()).build();
        Post post_닭죽 = Post.builder().food(food_닭죽).title("닭죽").content("본죽").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://cdn.bonif.co.kr/cmdt/BF101_pic_2lnc2Wwe.jpg").build()).build();
        Post post_참치김밥 = Post.builder().food(food_참치김밥).title("참치김밥").content("김밥천국").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("//m.cookstopmall.co.kr/web/product/big/201904/d8ce567b8d4936d6ee546be6aa61f5df.jpg").build()).build();
        Post post_돈까스김밥 = Post.builder().food(food_돈까스김밥).title("돈까스김밥").content("김밥천국").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://mblogthumb-phinf.pstatic.net/MjAxOTA1MjNfOTAg/MDAxNTU4NTc0NTMzMzM1.XKARW431WBNJpSC3-xLVnS0RKUoRPzaPnCoZ_32XRJkg.5I_eh-4Eq3_CZDLLid3y8BPQeG5-QaUyRmhNk7IhE6cg.JPEG.misoo0612/20190515_154923.jpg?type=w800").build()).build();
        Post post_쇠고기김밥 = Post.builder().food(food_쇠고기김밥).title("쇠고기김밥").content("김밥천국").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://t1.daumcdn.net/cfile/blog/174DC8404FB2EE521A").build()).build();
        Post post_치즈김밥 = Post.builder().food(food_치즈김밥).title("치즈김밥").content("김밥천국").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://cdn.mkhealth.co.kr/news/photo/202008/img_MKH200810001_0.jpg").build()).build();
        Post post_소세지김밥 = Post.builder().food(food_소세시김밥).title("소세지김밥").content("김밥천국").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://mblogthumb-phinf.pstatic.net/MjAyMDA0MDNfMTQg/MDAxNTg1ODk0NDA1NDYz.T7g7s9-aCFUOQf6KRHHhH4HcDCj50fUjTaWgHhf5-Lkg.e54Vt16frFl6ZY4YNqew3lxBo5sDIa1EmpiBvabI8QEg.JPEG.morningapple6/output_589555204.jpg?type=w800").build()).build();
        Post post_날치알김밥 = Post.builder().food(food_날치알김밥).title("날치알김밥").content("김밥천국").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://shop3.daumcdn.net/thumb/R500x500/?fname=http%3A%2F%2Fshop3.daumcdn.net%2Fshophow%2Fp%2FD5102458002.jpg%3Fut%3D20210409205046").build()).build();
        Post post_새우김밥 = Post.builder().food(food_새우김밥).title("새우김밥").content("김밥천국").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://image.utoimage.com/preview/cp871385/2019/01/201901007525_500.jpg").build()).build();
        Post post_오므라이스 = Post.builder().food(food_오므라이스).title("오므라이스").content("김밥천국 오므라이스").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("//ww.namu.la/s/fbd00013bf6c3e4230e9dc13a253cc82474e41e72a66ccbb0e86f6976035d6d4ae24507500630f93c3b32720308398238be15629620d1833288934190a59b3f6139bbe58b20b007f69f9f96c95f2dec1").build()).build();
        Post post_카레라이스 = Post.builder().food(food_카레라이스).title("카레라이스").content("카레라이스").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://recipe1.ezmember.co.kr/cache/recipe/2018/04/09/6bf71f32725ba27c76ad237bce1cab4f1.jpg").build()).build();
        Post post_제육덮밥 = Post.builder().food(food_제육덮밥).title("제육덮밥").content("백종원 제육덮밥").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("//mblogthumb-phinf.pstatic.net/MjAxODA2MjlfMTg3/MDAxNTMwMjI3MTM2MTYw.YAnGJ54iSwne65j2SPsv2jtvvbjVKrpGeXkiNySXTyYg.EBatLj2Hs8R_DT_zUb33VRjh19lSDzIeMw3pW6lDpAsg.JPEG.dew36/image_8966900271530227101909.jpg?type=w800").build()).build();
        Post post_비빔밥 = Post.builder().food(food_비빔밥).title("비빔밥").content("본돌솥 비빔밥").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("//health.chosun.com/site/data/img_dir/2021/01/27/2021012702508_0.jpg").build()).build();
        Post post_차슈덮밥 = Post.builder().food(food_차슈덮밥).title("차슈덮밥").content("우리의 식탁").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://static.wtable.co.kr/image/production/service/recipe/432/1e0e212b-20e9-426c-9e1f-8a8d9e10d4a5.jpg?size=1024x1024?size=200x200").build()).build();
        Post post_스테이크덮밥 = Post.builder().food(food_스테이크덮밥).title("스테이크덮밥").content("1pound스테이크덮밥").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://t1.daumcdn.net/cfile/blog/2523534157A0397C32").build()).build();
        Post post_김치볶음밥 = Post.builder().food(food_김치볶음밥).title("김치볶음밥").content("김밥천국 김치볶음밥").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("//storage.googleapis.com/cbmpress/uploads/sites/3/2018/10/CBM-17.jpg").build()).build();
        Post post_잡채볶음밥 = Post.builder().food(food_잡채볶음밥).title("잡채 볶음밥").content("프레시지 잡채 볶음밥").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("//www.heodak.com/shopimages/heodak/006009000107.jpg?1623305996").build()).build();
        Post post_새우볶음밥 = Post.builder().food(food_새우볶음밥).title("새우볶음밥").content("김밥천국").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://t1.daumcdn.net/cfile/tistory/99FF8B385CA5F8352B").build()).build();
        Post post_낙지볶음밥 = Post.builder().food(food_낙지볶음밥).title("낙지볶음밥").content("김밥천국").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("http://image.auction.co.kr/itemimage/1b/c7/02/1bc702b3c6.jpg").build()).build();
        Post post_카레볶음밥 = Post.builder().food(food_카레볶음밥).title("카레볶음밥").content("푸드장").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://ai.esmplus.com/foodjang01/images/221500346_b_1.jpg").build()).build();
        Post post_순대국밥 = Post.builder().food(food_순대국밥).title("소사골 우순대국").content("담소 순대국").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("//pds.joongang.co.kr/news/component/htmlphoto_mmdata/201411/18/htm_2014111820423954005011.jpg").build()).build();
        Post post_순대국밥2 = Post.builder().food(food_순대국밥).title("병천순대국밥").content("마선생 마약국밥 입니다").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://thumb.mt.co.kr/06/2020/10/2020101315592142473_1.jpg/dims/optimize/").build()).build();
        Post post_순대국밥3 = Post.builder().food(food_순대국밥).title("돈덕순대국밥").content("돈덕순대국밥 입니다").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("http://image.babosarang.co.kr/product/detail/MMB/2001090957553365/_600.jpg").build()).build();
        Post post_육개장 = Post.builder().food(food_육개장).title("육개장").content("푸드얍").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("http://foodyap.co.kr/shopimages/goldplate1/040001000001.jpg?1560837597").build()).build();
        Post post_설렁탕 = Post.builder().food(food_설렁탕).title("설렁탕").content("이병우 착한 설렁탕").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("//th2.tmon.kr/thumbs/image/d06/2d3/4a6/1165706d0_700x700_95_FIT.jpg").build()).build();
        Post post_삼계탕 = Post.builder().food(food_삼계탕).title("삼계탕").content("풍전").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://img.hankyung.com/photo/202103/99.11408102.1.jpg").build()).build();
        Post post_순두부찌개 = Post.builder().food(food_순두부찌개).title("순두부 찌개").content("담소 순두부찌개").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("//mblogthumb-phinf.pstatic.net/MjAyMDA5MjFfODcg/MDAxNjAwNjY3MzE1NjA2.Upndrth268zQQiuntIAXUBp9sXIi0QlUMwM2THMfflMg.i8jEArCsP9xGthG_NWChQnOk21JnI4QIfp-78cg-pegg.JPEG.hwapori/1600667313986.JPG?type=w800").build()).build();
        Post post_부대찌개 = Post.builder().food(food_부대찌개).title("부대찌개").content("스태프 부대찌개").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("//foodyap.co.kr/shopimages/goldplate1/066001000008.jpg?1560850364").build()).build();
        Post post_김치찌개 = Post.builder().food(food_김치찌개).title("김치찌개").content("오모가리").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://image.wingeat.com/item/images/ec626ef5-98c5-4812-96af-944fa8201155-w600.jpg").build()).build();
        Post post_뼈해장국 = Post.builder().food(food_뼈해장국).title("우거지 뼈해장국").content("강강술래 뼈해장국").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("//image.skstoa.com/goods/085/21983085_c.jpg").build()).build();
        Post post_감자탕 = Post.builder().food(food_감자탕).title("감자탕").content("성수동 감자탕").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://mediahub.seoul.go.kr/wp-content/uploads/2017/07/a9a263bbb1d92fff11267186621a5f26.jpg").build()).build();
        Post post_마라탕 = Post.builder().food(food_마라탕).title("마라탕").content("마켓컬리").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://img-cf.kurly.com/shop/data/goodsview/20200901/gv00000117943_1.jpg").build()).build();
        Post post_안동찜닭 = Post.builder().food(food_안동찜닭).title("안동찜닭").content("우리의식탁").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://static.wtable.co.kr/image-resize/production/service/recipe/235/4x3/7c2b5692-bf30-474b-b056-48a2827cbded.jpg").build()).build();
        Post post_갈비찜 = Post.builder().food(food_갈비찜).title("갈비찜").content("통마늘 돼지 갈비찜").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("//www.cj.co.kr/images/theKitchen/PHON/0000002320/0000009726/0000002320.jpg").build()).build();
        Post post_매운탕 = Post.builder().food(food_매운탕).title("매운탕").content("우리 집밥").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://blog.kakaocdn.net/dn/dQacZo/btqZty5L0NI/iLAaLLGMN421vXP0hNPAAK/img.jpg").build()).build();
        Post post_알탕 = Post.builder().food(food_알탕).title("알탕").content("디트리").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://www.d-tree.co.kr/attach/prtimg/1576472209_72362.jpg").build()).build();
        Post post_대구탕 = Post.builder().food(food_대구탕).title("대구탕").content("만개의 레시피").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://recipe1.ezmember.co.kr/cache/recipe/2020/02/18/a7e7832aecf113163e628ead800735f71.jpg").build()).build();
        Post post_닭볶음탕 = Post.builder().food(food_닭볶음탕).title("닭볶음탕").content("만개의 레시피").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://recipe1.ezmember.co.kr/cache/recipe/2017/09/12/8a64e811117ac8b1481ee52bd443b85c1.jpg").build()).build();
        Post post_떡볶이 = Post.builder().food(food_떡볶이).title("떡볶이").content("죠스 떡볶이").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("http://www.seenews365.com/news/photo/201810/31333_31848_1721.jpg").build()).build();
        Post post_치즈떡볶이 = Post.builder().food(food_치즈떡볶이).title("치즈떡볶이").content("프리픽").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRlwwK9-ikroL4inijFtSM0SZUjSr7qqUhPJQ&usqp=CAU").build()).build();
        Post post_로제떡볶이 = Post.builder().food(food_로제떡볶이).title("로제떡볶이").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("http://image.auction.co.kr/itemimage/22/9e/b4/229eb45806.jpg").build()).build();
        Post post_비빔국수 = Post.builder().food(food_비빔국수).title("비빔국수").content("비빔국수").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("//t1.daumcdn.net/cfile/tistory/2202DB4158A64E4C34").build()).build();
        Post post_잔치국수 = Post.builder().food(food_잔치국수).title("잔치국수").content("백종원의 잔치국수").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("//image.board.sbs.co.kr/2021/03/26/iRU1616720284909.jpg").build()).build();
        Post post_열무국수 = Post.builder().food(food_열무국수).title("열무국수").content("이투데이").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://img.etoday.co.kr/pto_db/2019/08/600/20190808171705_1354507_727_485.jpg").build()).build();
        Post post_막국수 = Post.builder().food(food_막국수).title("막국수").content("막국수").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://mediahub.seoul.go.kr/wp-content/uploads/2018/07/f4b1c8f800f687c247a69e7622a7db59.jpg").build()).build();
        Post post_칼국수 = Post.builder().title("바지락 칼국수").content("칼국수").archived(false).member(NIBH_FELIS_MEMBER).food(food_칼국수).attachment(Attachment.builder().name("test.png").path("//t1.daumcdn.net/cfile/tistory/260690505262AD571D").build()).build();
        Post post_수제비 = Post.builder().title("바지락 수제비").content("칼국수").archived(false).member(NIBH_FELIS_MEMBER).food(food_수제비).attachment(Attachment.builder().name("test.png").path("https://www.sk5.co.kr/img_src/s600/1227//12272675.jpg").build()).build();
        Post post_라면 = Post.builder().food(food_라면).title("라면").content("진 라면").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRsh1tKt3Dfvuhys1sMv085NmTd5BOd4Oi4ocioLBJhclQZRe2WDd_10ceJEiFoi1wgwt0&usqp=CAU").build()).build();
        Post post_떡라면 = Post.builder().food(food_떡라면).title("떡라면").content("에누리").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("http://storage.enuri.info/pic_upload/knowbox2/202106/064503763202106236d8f14c9-dbee-4553-9f8c-c5a16f2e3527.JPEG").build()).build();
        Post post_만두라면 = Post.builder().food(food_만두라면).title("만두라면").content("sk5").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://www.sk5.co.kr/img_src/s600/1227//12271459.jpg").build()).build();
        Post post_치즈라면 = Post.builder().food(food_치즈라면).title("치즈라면").content("오뚜기 리얼 치즈라면").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://t1.daumcdn.net/cfile/tistory/99F7953359EC8ECA14").build()).build();
        Post post_콩나물라면 = Post.builder().food(food_콩나물라면).title("콩나물 라면").content("김밥천국 콩나물 라면").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://t1.daumcdn.net/cfile/tistory/2058923D4E0E682002").build()).build();
        Post post_라볶이 = Post.builder().food(food_라볶이).title("라볶이").content("푸드조선").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://lh3.googleusercontent.com/proxy/_PTkpHmYhV553ZpsKpFt0st3J6KVItNOl3BYj_NbTlD_t-m9SbHmwXU_MRVBzG7WS5_Ett1xlRpjYgLP88KnUcXLB9OCUGn0o8CFv8BMRZ7uxqAgtTa0-nDRoam1Pwk").build()).build();
        Post post_치즈라볶이 = Post.builder().food(food_치즈라볶이).title("치즈라볶이").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://mblogthumb-phinf.pstatic.net/20160707_63/dew36_1467898257683gMJT0_JPEG/4.jpg?type=w2").build()).build();
        Post post_우삼겹부대라면 = Post.builder().food(food_우삼겹부대라면).title("우삼겹부대라면").content("미소야").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://mblogthumb-phinf.pstatic.net/MjAxOTA0MTdfMjk0/MDAxNTU1NTA5NTYwNjQ0.CcoMJwqT-rs-Ji6qXBjmHz8O-JtUmJ82Uky424bweuog.MPfVD9VM4UZtiYPFO7-ilXZP_UC1Y9Kmk8CpPau456Yg.JPEG.gkmovingon/SE-f4aea872-39c4-4e6d-95a8-2a217403bc87.jpg?type=w800").build()).build();
        Post post_물냉면 = Post.builder().food(food_물냉면).title("물냉면").content("평양냉면").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("http://image.auction.co.kr/itemimage/22/5f/2b/225f2b6516.jpg").build()).build();
        Post post_비빔냉면 = Post.builder().food(food_비빔냉면).title("비빔냉면").content("함흥냉면").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://m.yorivery.com/data/goods/20/07/30//1000000905/1000000905_detail_388.jpg").build()).build();
        Post post_열무냉면 = Post.builder().food(food_열무냉면).title("열무냉면").content("만개의 레시피").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://recipe1.ezmember.co.kr/cache/recipe/2018/07/17/5c963426f98889a96817621060798d171.jpg").build()).build();
        Post post_쫄면 = Post.builder().food(food_쫄면).title("쫄면").content("이투데이").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://img.etoday.co.kr/pto_db/2015/06/600/20150618042525_657515_600_402.jpg").build()).build();
        Post post_김치쫄면 = Post.builder().food(food_김치쫄면).title("김치쫄면").content("쿠캣").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://crcf.cookatmarket.com/product/images/2020/07/cota_1594864927_7121.jpg").build()).build();
        Post post_스파게티 = Post.builder().food(food_스파게티).title("스파게티").content("스파게티").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("//t1.daumcdn.net/cfile/tistory/9960AD33598BEE2837").build()).build();
        Post post_봉골레파스타 = Post.builder().food(food_봉골레파스타).title("봉골레 파스타").content("롤링 파스타").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("//t1.daumcdn.net/cfile/tistory/9938A0435FF71A9022").build()).build();
        Post post_알리오올리오 = Post.builder().food(food_알리오올리오).title("알리오 올리오").content("롤링 파스타").archived(false).member(TELLUS_MAURIS_MEMBER).attachment(Attachment.builder().name("test.png").path("//img.danawa.com/images/descFiles/5/68/4067903_1573739178901.jpeg").build()).build();
        Post post_매운우삼겹오일파스타 = Post.builder().food(food_매운우삼겹오일파스타).title("매운 우삼겹 오일 파스타").content("롤링 파스타").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://mblogthumb-phinf.pstatic.net/MjAyMTAxMDdfMTE5/MDAxNjEwMDI2NjQyMTg2.x3Sch_wcJKevMe2GTuUYRtQD3KZtGE7I_qFGXiRyEJMg.UrNsjSWrJlRu2gOOSZsP1U1rc4gmF0WFR8Gdu39FbbQg.JPEG.tripod2001/12FB0048-295D-43D8-86F8-B4F2CFF634C7.jpg?type=w800").build()).build();
        Post post_스테이크크림파스타 = Post.builder().title("스테이크 크림 파스타").content("롤링 파스타").archived(false).member(TELLUS_MAURIS_MEMBER).food(food_스테이크크림파스타).attachment(Attachment.builder().name("test.png").path("//blog.kakaocdn.net/dn/nM5dN/btq0bw6NOTG/46vCl3py0mkiX5OSfPwR8k/img.jpg").build()).build();
        Post post_해물크림파스타 = Post.builder().food(food_해물크림파스타).title("해물크림파스타").content("롤링 파스타").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://mblogthumb-phinf.pstatic.net/MjAyMDEyMDNfMTE3/MDAxNjA2OTcxNjM2ODg1.jBGySBGZMqN3RPLYMgAcr45pSojiD-N6A-0463WF9b0g.jYpbnHojXGe_8FqT1x8odOXHRbzj3vMuk64mBOhivlcg.JPEG.sum_n_mu/1606971637324.jpg?type=w800").build()).build();
        Post post_매운크림파스타 = Post.builder().food(food_매운크림파스타).title("매운크림파스타").content("롤링 파스타").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://mblogthumb-phinf.pstatic.net/MjAxOTA0MjRfMTU0/MDAxNTU2MDg4NzE5MzU2.pUvpmJraeE61LY5DCem9mXkv0hEz__U2k569dq_eN8Qg.b2hjC0cjLTpVQf29WJ4VuHUyqVEe6eBd1XU4Ly68GAgg.JPEG.yuncorn/output_887537955.jpg?type=w800").build()).build();
        Post post_로제크림쉬림프파스타 = Post.builder().food(food_로제크림쉬림프파스타).title("로제크림쉬림프파스타").content("롤링 파스타").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://blog.kakaocdn.net/dn/cinXsq/btqEn77dG5V/cU4k7074wDj9llBIJwbvuK/img.jpg").build()).build();
        Post post_까르보나라 = Post.builder().food(food_까르보나라).title("까르보나라").content("롤링 파스타").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("http://cdn.dealbada.com/data/editor/1811/e342fb4118a0fc74fa1daddf9a5e4a94_1542548188_0624.jpg").build()).build();
        Post post_통소시지토마토파스타 = Post.builder().food(food_통소시지토마토파스타).title("통소시지토마토파스타").content("롤링 파스타").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://mblogthumb-phinf.pstatic.net/MjAyMTA4MDdfMTM2/MDAxNjI4Mjk4NzMyMzQ0.qnWOOnhuBwTPBf9cAcres4Kpg3MVIyogtC-rqo4Qc1Ig.T0t0j6TaCPJF3Hid8eD_UpECH_z-U7fvpyv7xCrUar8g.JPEG.gjtpdud5547/KakaoTalk_20210807_100514253_05.jpg?type=w800").build()).build();
        Post post_해물토마토파스타 = Post.builder().food(food_해물토마토파스타).title("해물토마토파스타").content("롤링 파스타").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://blog.kakaocdn.net/dn/b0cWeI/btqC0J2lH3E/BWZJY8LjzdMAoaHI6Ai9kK/img.jpg").build()).build();
        Post post_매운우삽겹토마토파스타 = Post.builder().food(food_매운우삽겹토마토파스타).title("매운우삽겹토마토파스타").content("롤링 파스타").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
        Post post_토마토파스타 = Post.builder().food(food_토마토파스타).title("토마토 파스타").content("롤링 파스타").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("//static.tasteem.io/uploads/3993/post/32791/content_6f9015ce-6725-4327-8fa3-5bf343e5ac81.jpeg").build()).build();
        Post post_버섯크림뇨끼 = Post.builder().food(food_버섯크림뇨끼).title("버섯크림뇨끼").content("롤링 파스타").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://mblogthumb-phinf.pstatic.net/MjAyMTA5MTdfMTAx/MDAxNjMxODA5NzM1NDEz.fYdjfh8N3pdZePfNIIle5T8K2uNVjOBrnXOymf4j61cg.y0iuh49wTgYEQ_WPD8GWn9aZQSa4ZIBDFy6tPPtpO9kg.JPEG.dgnmm123/20210916%EF%BC%BF192220.jpg?type=w800").build()).build();
        Post post_뇨끼감바스 = Post.builder().food(food_뇨끼감바스).title("뇨끼감바스").content("롤링 파스타").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://t1.daumcdn.net/cfile/tistory/99FE3A415F662EB508").build()).build();
        Post post_라비올리 = Post.builder().food(food_라비올리).title("라비올리").content("나무위키").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://w.namu.la/s/193b3a1cb9083a6e2709989741dc277b3c286677b5d05aaa07636461feba2d62c19164e68c88716fedce398f092ca87c6448bbf0cbbeca9ade4761ad58d6eaea35e1cc585ed49c3796d02e8fbb576dac").build()).build();
        Post post_토르델리니 = Post.builder().food(food_토르델리니).title("토르델리니").content("게티이미지뱅크").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://imagescdn.gettyimagesbank.com/500/20/732/144/0/1268584409.jpg").build()).build();
        Post post_탈리아텔레 = Post.builder().food(food_탈리아텔레).title("탈리아텔레").content("두피디아").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://storage.doopedia.co.kr/upload/_upload/image4/1707/26/170726021259422/170726021259422_thumb_400.jpg").build()).build();
        Post post_리소토 = Post.builder().food(food_리소토).title("리소토|리조또").content("클래스101").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://cdn.class101.net/images/3a482702-f8cd-4737-8714-26de79d0d476").build()).build();
        Post post_오레키에테 = Post.builder().food(food_오레키에테).title("오레키에테").content("맘스데일리").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://img.momsdiary.co.kr/board/suda/spboard2/pds/momspr/33191/133299178871.jpg").build()).build();
        Post post_브루스케타 = Post.builder().food(food_브루스케타).title("브루스케타").content("만개의 레시피").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://recipe1.ezmember.co.kr/cache/recipe/2019/07/05/e5b7fd16b42219066effef42337b304d1.jpg").build()).build();
        Post post_슈페츨레 = Post.builder().food(food_슈페츨레).title("슈페츨레").content("독일 슈페츨레").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://mblogthumb-phinf.pstatic.net/MjAxOTAzMTVfOSAg/MDAxNTUyNTkzNDYyMTkx.N62GXp-HBdxh-Pq7pFHKzjFi2yjwO8MHYub66mIu-5Mg.ikaWpgRuVYKtontnK47SRgxEPljCv66Ry6AISZaJvwog.JPEG.kimjoo6/1552593381530.jpg?type=w800").build()).build();
        Post post_마카로니 = Post.builder().food(food_마카로니).title("마카로니").content("마카로니 새럴드").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://t1.daumcdn.net/cfile/tistory/997EBF505DD538092D").build()).build();
        Post post_공갈빵 = Post.builder().food(food_공갈빵).title("공갈빵").content("공갈빵").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("http://image.aladin.co.kr/Community/paper/2015/0122/pimg_7830151931140124.jpg").build()).build();
        Post post_캄파뉴 = Post.builder().food(food_캄파뉴).title("캄파뉴").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://i.ytimg.com/vi/bAoGUWG0LW0/maxresdefault.jpg").build()).build();
        Post post_꽃빵 = Post.builder().food(food_꽃빵).title("꽃빵").content("네니아").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://econenia.com/shopEconenia/detail/10001190/d10001190_01.jpg").build()).build();
        Post post_단팥빵 = Post.builder().food(food_단팥빵).title("단팥빵").content("단팥빵 입니다").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://cdn.imweb.me/upload/S202005223f9d933957d0a/2941ed2fc3216.jpg").build()).build();
        Post post_도넛 = Post.builder().food(food_도넛).title("도넛").content("도넛").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://t1.daumcdn.net/cfile/tistory/995AFD33599FCA2D0E").build()).build();
        Post post_붕어빵 = Post.builder().food(food_붕어빵).title("붕어빵").content("붕어빵").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://partner.yogiyo.co.kr/public/upload/editor/2021/01/25/d4b837aad7524462a1d0f5f552b7cd03.jpg").build()).build();
        Post post_롤빵 = Post.builder().food(food_롤빵).title("롤빵").content("롤빵").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://t1.daumcdn.net/cfile/tistory/222D9D3E5357D50630").build()).build();
        Post post_소라빵 = Post.builder().food(food_소라빵).title("소라빵").content("소라빵").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://cdn.imweb.me/thumbnail/20210516/6804e10fe543d.jpg").build()).build();
        Post post_소보로빵 = Post.builder().food(food_소보로빵).title("소보로빵").content("소보로빵").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://mblogthumb-phinf.pstatic.net/20160310_32/0323lena_1457592241289dmLmg_PNG/56efge4.png?type=w2").build()).build();
        Post post_마늘빵 = Post.builder().food(food_마늘빵).title("마늘빵").content("마늘빵").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://mblogthumb-phinf.pstatic.net/MjAxNzA1MjBfMTAy/MDAxNDk1MjYwNDk2ODQx.ex2wnv92bYNnKj2iFrDVqZIpZQ72O8pzrZwf2NFIMd4g.Fa8k3PfHTeLwpxTUFM-MWfo_Nvz8u31H1kpwsebAowUg.JPEG.1125sy/%EB%A7%88%EB%8A%98%EB%B9%B5_%EB%A7%8C%EB%93%A4%EA%B8%B0_01.JPG?type=w800").build()).build();
        Post post_베이컨토스트 = Post.builder().food(food_베이컨토스트).title("베이컨 베스트").content("베이컨 베스트").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://www.isaac-toast.co.kr/upload/product/1890188341_txc4ALVY_20210223115212.png").build()).build();
        Post post_피자토스트 = Post.builder().food(food_피자토스트).title("피자토스트").content("피자토스트").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://www.isaac-toast.co.kr/upload/product/1890188341_YlTL1en8_20210223034032.png").build()).build();
        Post post_감자토스트 = Post.builder().food(food_감자토스트).title("감자토스트").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://www.isaac-toast.co.kr/upload/product/1890188341_JCae046m_20210223121034.png").build()).build();
        Post post_햄버거 = Post.builder().food(food_햄버거).title("햄버거").content("싸이버거").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("//w.namu.la/s/b576be4b5714cf0462d9a541f3356a85469b0c5082b906ef0d330c87cb405f70c8be2dc91c36bd5d61780a71714d29db1a7555eca042f1d7f75eb4f0f5fdcfd1b8493908209e3a452ef670bf148f870e").build()).build();
        Post post_와플 = Post.builder().food(food_와플).title("와플").content("와플").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://mblogthumb-phinf.pstatic.net/MjAxODExMjZfMTM3/MDAxNTQzMjI1MDcyODA2.2oxffZSUH5V_K5BiZdIiTorQvKw4AIkpBjpz8zwwerUg.jP0v1XNWzeqC0Ox2X9NlHNIrsc2q8RqRt5y3SccKPMgg.JPEG.seoulcoopcenter/%EB%B2%A0%EB%9F%AC%EB%8C%84%EC%99%80%ED%94%8C8.jpg?type=w800").build()).build();
        Post post_팬케이크 = Post.builder().food(food_팬케이크).title("팬케이크").content("팬케이크").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://i.ytimg.com/vi/gc-kjplpdA4/maxresdefault.jpg").build()).build();
        Post post_크루아상 = Post.builder().food(food_크루아상).title("크루아상").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("http://meloncoffee.com/wp-content/uploads/2019/08/bread-1284438.jpg").build()).build();
        Post post_생크림케익 = Post.builder().food(food_생크림케익).title("생크림케익").content("생크림케익").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://recipe1.ezmember.co.kr/cache/recipe/2018/12/21/c17da5253dce7731e5f221c64dbce46a1.jpg").build()).build();
        Post post_초코케익 = Post.builder().food(food_초코케익).title("초코케익").content("초코케익").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://i.ytimg.com/vi/1fvQcBEWhUQ/maxresdefault.jpg").build()).build();
        Post post_딸기케익 = Post.builder().food(food_딸기케익).title("딸기케익").content("딸기케익").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://i.ytimg.com/vi/COJ2XX3N3zo/maxresdefault.jpg").build()).build();
        Post post_오징어버거 = Post.builder().food(food_오징어버거).title("오징어버거").content("오징어버거").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://cdn.newspost.kr/news/photo/201908/72854_74811_563.jpg").build()).build();
        Post post_치즈버거 = Post.builder().food(food_치즈버거).title("치즈버거").content("치즈버거").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://t1.daumcdn.net/cfile/tistory/9908C3385EE2293B01").build()).build();
        Post post_새우버거 = Post.builder().food(food_새우버거).title("새우버거").content("새우버거").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://img.insight.co.kr/static/2016/03/04/700/292z8mhg980269202q5s.jpg").build()).build();
        Post post_데리버거 = Post.builder().food(food_데리버거).title("데리버거").content("데리버거").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/dl/17/1/6/2/4/9/7/eEpxT/1738162497_117989156.jpg").build()).build();
        Post post_불고기버거 = Post.builder().food(food_불고기버거).title("불고기버거").content("불고기버거").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://images.chosun.com/resizer/yQWGsPX6aRdZhFRe4wshexOkW24=/464x0/smart/cloudfront-ap-northeast-1.images.arcpublishing.com/chosun/4DR2QP2OFCQX5BHGLGOXYJVXJM.jpg").build()).build();
        Post post_핫크리스피버거 = Post.builder().food(food_핫크리스피버거).title("핫크리스피버거").content("핫크리스피버거").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://image.news1.kr/system/photos/2012/3/15/97579/medium.jpg?1331798976").build()).build();
        Post post_한우불고기버거 = Post.builder().food(food_한우불고기버거).title("한우불고기버거").content("한우불고기버거").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://lh3.googleusercontent.com/proxy/sQhNxOXq9z1KukQ2NUpP0WlZefBLHGEcn6YgxXGlLXz-d9ORkqjR0GlMNd76Spy06Bq_29u0M02whSmM3kQ9bc4IFt4CRQQMb3erFhZSRFB_").build()).build();
        Post post_치킨버거 = Post.builder().food(food_치킨버거).title("치킨버거").content("치킨버거").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://cphoto.asiae.co.kr/listimglink/7/2015060909143267840_1.jpg").build()).build();
        Post post_콤비네이션피자 = Post.builder().food(food_콤비네이션피자).title("콤비네이션피자").content("콤비네이션피자").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://www.kukgangmo.com/rankup_module/rankup_board/attach/gallery1/15439984661707.jpg").build()).build();
        Post post_불고기피자 = Post.builder().food(food_불고기피자).title("리얼 불고기 피자").content("도미노 피자").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://cdn.dominos.co.kr/admin/upload/goods/20200508_gH22my39.jpg").build()).build();
        Post post_고구마피자 = Post.builder().food(food_고구마피자).title("우리 고구마 피자").content("도미노 피자").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://cdn.dominos.co.kr/admin/upload/goods/20200508_KdroBehI.jpg").build()).build();
        Post post_포테이토피자 = Post.builder().food(food_포테이토피자).title("포테이토피자").content("도미노 피자").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://cdn.dominos.co.kr/admin/upload/goods/20200311_M9Q50gtd.jpg").build()).build();
        Post post_쉬림프피자 = Post.builder().food(food_쉬림프피자).title("블랙타이거 슈림프 피자").content("도미노 피자").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://cdn.dominos.co.kr/admin/upload/goods/20200508_780B32i8.jpg").build()).build();
        Post post_스테이크피자 = Post.builder().food(food_스테이크피자).title("블랙앵거스 스테이크 피자").content("도미노 피자").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://cdn.dominos.co.kr/admin/upload/goods/20200508_1fYuDcMq.jpg").build()).build();
        Post post_페퍼로니피자 = Post.builder().food(food_페퍼로니피자).title("페퍼로니피자").content("도미노 피자").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://cdn.dominos.co.kr/admin/upload/goods/20200311_x8StB1t3.jpg").build()).build();
        Post post_하와이안피자 = Post.builder().food(food_하와이안피자).title("하와이안 슈림프 피자").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://cdn.dominos.co.kr/admin/upload/goods/20210226_jH15FEzi.jpg").build()).build();
        Post post_돈카츠 = Post.builder().food(food_돈카츠).title("돈카츠").content("왕 돈까스").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("//t1.daumcdn.net/cfile/tistory/9951473F5D4633FD2C").build()).build();
        Post post_치즈돈카츠 = Post.builder().food(food_치즈돈카츠).title("치즈 돈까스").content("홍익 돈까스").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("//contents.sixshop.com/thumbnails/uploadedFiles/39154/default/image_1594709054909_1000.jpg").build()).build();

        em.persist(post_야채죽);
        em.persist(post_쇠고기야채죽);
        em.persist(post_전복죽);
        em.persist(post_닭죽);
        em.persist(post_참치김밥);
        em.persist(post_돈까스김밥);
        em.persist(post_쇠고기김밥);
        em.persist(post_치즈김밥);
        em.persist(post_소세지김밥);
        em.persist(post_날치알김밥);
        em.persist(post_새우김밥);
        em.persist(post_오므라이스);
        em.persist(post_카레라이스);
        em.persist(post_제육덮밥);
        em.persist(post_비빔밥);
        em.persist(post_차슈덮밥);
        em.persist(post_스테이크덮밥);
        em.persist(post_김치볶음밥);
        em.persist(post_잡채볶음밥);
        em.persist(post_새우볶음밥);
        em.persist(post_낙지볶음밥);
        em.persist(post_카레볶음밥);
        em.persist(post_순대국밥);
        em.persist(post_순대국밥2);
        em.persist(post_순대국밥3);
        em.persist(post_육개장);
        em.persist(post_설렁탕);
        em.persist(post_삼계탕);
        em.persist(post_순두부찌개);
        em.persist(post_부대찌개);
        em.persist(post_김치찌개);
        em.persist(post_뼈해장국);
        em.persist(post_감자탕);
        em.persist(post_마라탕);
        em.persist(post_안동찜닭);
        em.persist(post_갈비찜);
        em.persist(post_매운탕);
        em.persist(post_알탕);
        em.persist(post_대구탕);
        em.persist(post_닭볶음탕);
        em.persist(post_떡볶이);
        em.persist(post_치즈떡볶이);
        em.persist(post_로제떡볶이);
        em.persist(post_비빔국수);
        em.persist(post_잔치국수);
        em.persist(post_열무국수);
        em.persist(post_막국수);
        em.persist(post_칼국수);
        em.persist(post_수제비);
        em.persist(post_라면);
        em.persist(post_떡라면);
        em.persist(post_만두라면);
        em.persist(post_치즈라면);
        em.persist(post_콩나물라면);
        em.persist(post_라볶이);
        em.persist(post_치즈라볶이);
        em.persist(post_우삼겹부대라면);
        em.persist(post_물냉면);
        em.persist(post_비빔냉면);
        em.persist(post_열무냉면);
        em.persist(post_쫄면);
        em.persist(post_김치쫄면);
        em.persist(post_스파게티);
        em.persist(post_봉골레파스타);
        em.persist(post_알리오올리오);
        em.persist(post_매운우삼겹오일파스타);
        em.persist(post_스테이크크림파스타);
        em.persist(post_해물크림파스타);
        em.persist(post_매운크림파스타);
        em.persist(post_로제크림쉬림프파스타);
        em.persist(post_까르보나라);
        em.persist(post_통소시지토마토파스타);
        em.persist(post_해물토마토파스타);
        em.persist(post_매운우삽겹토마토파스타);
        em.persist(post_토마토파스타);
        em.persist(post_버섯크림뇨끼);
        em.persist(post_뇨끼감바스);
        em.persist(post_라비올리);
        em.persist(post_토르델리니);
        em.persist(post_탈리아텔레);
        em.persist(post_리소토);
        em.persist(post_오레키에테);
        em.persist(post_브루스케타);
        em.persist(post_슈페츨레);
        em.persist(post_마카로니);
        em.persist(post_공갈빵);
        em.persist(post_캄파뉴);
        em.persist(post_꽃빵);
        em.persist(post_단팥빵);
        em.persist(post_도넛);
        em.persist(post_붕어빵);
        em.persist(post_롤빵);
        em.persist(post_소라빵);
        em.persist(post_소보로빵);
        em.persist(post_마늘빵);
        em.persist(post_베이컨토스트);
        em.persist(post_피자토스트);
        em.persist(post_감자토스트);
        em.persist(post_햄버거);
        em.persist(post_와플);
        em.persist(post_팬케이크);
        em.persist(post_크루아상);
        em.persist(post_생크림케익);
        em.persist(post_초코케익);
        em.persist(post_딸기케익);
        em.persist(post_오징어버거);
        em.persist(post_치즈버거);
        em.persist(post_새우버거);
        em.persist(post_데리버거);
        em.persist(post_불고기버거);
        em.persist(post_핫크리스피버거);
        em.persist(post_한우불고기버거);
        em.persist(post_치킨버거);
        em.persist(post_콤비네이션피자);
        em.persist(post_불고기피자);
        em.persist(post_고구마피자);
        em.persist(post_포테이토피자);
        em.persist(post_쉬림프피자);
        em.persist(post_스테이크피자);
        em.persist(post_페퍼로니피자);
        em.persist(post_하와이안피자);
        em.persist(post_돈카츠);
        em.persist(post_치즈돈카츠);

//        Post post_고구마돈카츠 = Post.builder().food(food_고구마돈카츠).title("고구마돈카츠").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_생선카츠 = Post.builder().food(food_생선카츠).title("생선카츠").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_새우카츠 = Post.builder().food(food_새우카츠).title("새우카츠").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_로스카츠 = Post.builder().food(food_로스카츠).title("로스카츠").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_히레카츠 = Post.builder().food(food_히레카츠).title("히레카츠").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_돈카츠김치나베 = Post.builder().food(food_돈카츠김치나베).title("돈카츠김치나베").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_후라이드 = Post.builder().food(food_후라이드).title("후라이드").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_양념치킨 = Post.builder().food(food_양념치킨).title("양념치킨").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_간장치킨 = Post.builder().food(food_간장치킨).title("간장치킨").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_시즈닝치킨 = Post.builder().food(food_시즈닝치킨).title("시즈닝치킨").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_바베큐치킨 = Post.builder().food(food_바베큐치킨).title("바베큐치킨").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_순살치킨 = Post.builder().food(food_순살치킨).title("순살치킨").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_닭강정 = Post.builder().food(food_닭강정).title("닭강정").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_순대 = Post.builder().food(food_순대).title("순대").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_오뎅 = Post.builder().food(food_오뎅).title("오뎅").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_감자튀김 = Post.builder().food(food_감자튀김).title("감자튀김").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_고구마튀김 = Post.builder().food(food_고구마튀김).title("고구마튀김").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_오징어튀김 = Post.builder().food(food_오징어튀김).title("오징어튀김").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_김말이 = Post.builder().food(food_김말이).title("김말이").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_오뎅튀김 = Post.builder().food(food_오뎅튀김).title("오뎅튀김").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_고로케 = Post.builder().food(food_고로케).title("고로케").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_새우튀김 = Post.builder().food(food_새우튀김).title("새우튀김").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_김치전 = Post.builder().food(food_김치전).title("김치전").content("백종원의 김치전").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("https://mblogthumb-phinf.pstatic.net/MjAyMTA5MDlfMjUg/MDAxNjMxMTUwMTUwMzAy.wYA-Vy5z0Hses5cLZm_NSvCs1KesK00MbngrY6cqRq4g.P_jWFJteuY4dMDRp5iGqex8uLGZSdDnfyKVm-cr0un8g.JPEG.wy5493/output_362068409.jpg?type=w800").build()).build();
//        Post post_감자전 = Post.builder().food(food_감자전).title("감자전").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_부추전 = Post.builder().food(food_부추전).title("부추전").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_해물파전 = Post.builder().food(food_해물파전).title("해물파전").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_핫도그 = Post.builder().food(food_핫도그).title("핫도그").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_체다치즈핫도그 = Post.builder().food(food_체다치즈핫도그).title("체다치즈핫도그").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_감자핫도그 = Post.builder().food(food_감자핫도그).title("감자핫도그").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_통가래떡핫도그 = Post.builder().food(food_통가래떡핫도그).title("통가래떡핫도그").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_고구마통모짜핫도그 = Post.builder().food(food_고구마통모짜핫도그).title("고구마통모짜핫도그").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_삼겹살 = Post.builder().food(food_삼겹살).title("삼겹살").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_고추장삼겹살 = Post.builder().food(food_고추장삼겹살).title("고추장삼겹살").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_통삼겹 = Post.builder().food(food_통삼겹).title("통삼겹").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_수육 = Post.builder().food(food_수육).title("수육").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_대패삼겹살 = Post.builder().food(food_대패삼겹살).title("대패삼겹살").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_족발 = Post.builder().food(food_족발).title("족발").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_곱창 = Post.builder().food(food_곱창).title("곱창").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_대창 = Post.builder().food(food_대창).title("대창").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_돼지껍데기 = Post.builder().food(food_돼지껍데기).title("돼지껍데기").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_돼지갈비 = Post.builder().food(food_돼지갈비).title("돼지갈비").content("갈비다움").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("http://shop-phinf.pstatic.net/20190123_126/100986059_1548254589607WQ3Fv_JPEG/%BB%F3%BC%BC%C6%E4%C0%CC%C1%F6%B0%ED%B1%E2%BB%E7%C1%F8.jpg?type=w860").build()).build();
//        Post post_보쌈 = Post.builder().food(food_보쌈).title("보쌈").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_삼겹살숙주볶음 = Post.builder().food(food_삼겹살숙주볶음).title("삼겹살숙주볶음").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_소고기미역국 = Post.builder().food(food_소고기미역국).title("소고기미역국").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_소불고기 = Post.builder().food(food_소불고기).title("소불고기").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_소고기장조림 = Post.builder().food(food_소고기장조림).title("소고기장조림").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_스테이크 = Post.builder().food(food_스테이크).title("백선생 스테이크").content("아메리칸 미트스토리").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("//post-phinf.pstatic.net/MjAxODAzMjFfMTI5/MDAxNTIxNjIxNTE0MjU4.K6A7tUiwden3UQMwelM35_4GIFB-NlhoPPIVD6IWokEg.KiHRHoreNOKSDQtEsW2de4ikvCRXlemsHah2xEjUGjQg.PNG/CA180320A_01.png?type=w1200").build()).build();
//        Post post_닭발 = Post.builder().food(food_닭발).title("닭발").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_닭발볶음 = Post.builder().food(food_닭발볶음).title("닭발볶음").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_닭꼬치 = Post.builder().food(food_닭꼬치).title("닭꼬치").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_닭똥집볶음 = Post.builder().food(food_닭똥집볶음).title("닭똥집볶음").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_훈제오리고기 = Post.builder().food(food_훈제오리고기).title("훈제오리고기").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_오리불고기 = Post.builder().food(food_오리불고기).title("오리불고기").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_오리진흙구이 = Post.builder().food(food_오리진흙구이).title("오리진흙구이").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_오리양념볶음 = Post.builder().food(food_오리양념볶음).title("오리양념볶음").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_양갈비스테이크 = Post.builder().food(food_양갈비스테이크).title("양갈비스테이크").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_케밥 = Post.builder().food(food_케밥).title("케밥").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_양꼬치 = Post.builder().food(food_양꼬치).title("양꼬치").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_뉴옥스트립 = Post.builder().food(food_뉴옥스트립).title("뉴옥스트립").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_포터하우스 = Post.builder().food(food_포터하우스).title("포터하우스").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_티본스테이크 = Post.builder().food(food_티본스테이크).title("티본스테이크").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_안심스테이크 = Post.builder().food(food_안심스테이크).title("안심스테이크").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_립아이 = Post.builder().food(food_립아이).title("립아이").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_백립 = Post.builder().food(food_백립).title("백립").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_토마호크 = Post.builder().food(food_토마호크).title("토마호크").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_우럭구이 = Post.builder().food(food_우럭구이).title("우럭구이").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_돌돔구이 = Post.builder().food(food_돌돔구이).title("돌돔구이").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_복어구이 = Post.builder().food(food_복어구이).title("복어구이").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_숭어구이 = Post.builder().food(food_숭어구이).title("숭어구이").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_송어구이 = Post.builder().food(food_송어구이).title("송어구이").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_연어구이 = Post.builder().food(food_연어구이).title("연어구이").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_참치구이 = Post.builder().food(food_참치구이).title("참치구이").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_홍어구이 = Post.builder().food(food_홍어구이).title("홍어구이").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_고등어구이 = Post.builder().food(food_고등어구이).title("고등어구이").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_꽁치구이 = Post.builder().food(food_꽁치구이).title("꽁치구이").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_우럭회 = Post.builder().food(food_우럭회).title("우럭회").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_돌돔회 = Post.builder().food(food_돌돔회).title("돌돔회").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_생새우회 = Post.builder().food(food_생새우회).title("생새우회").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_복어회 = Post.builder().food(food_복어회).title("복어회").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_숭어회 = Post.builder().food(food_숭어회).title("숭어회").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_송어회 = Post.builder().food(food_송어회).title("송어회").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_연어회 = Post.builder().food(food_연어회).title("연어회").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_참치회 = Post.builder().food(food_참치회).title("참치회").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_오징어회 = Post.builder().food(food_오징어회).title("오징어회").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_회무침 = Post.builder().food(food_회무침).title("회무침").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_물회 = Post.builder().food(food_물회).title("물회").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_홍어회 = Post.builder().food(food_홍어회).title("홍어회").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_방어회 = Post.builder().food(food_방어회).title("방어회").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_새우초밥 = Post.builder().food(food_새우초밥).title("새우초밥").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_연어초밥 = Post.builder().food(food_연어초밥).title("연어초밥").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_광어초밥 = Post.builder().food(food_광어초밥).title("광어초밥").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_참치초밥 = Post.builder().food(food_참치초밥).title("참치초밥").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_오징어초밥 = Post.builder().food(food_오징어초밥).title("오징어초밥").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_계란초밥 = Post.builder().food(food_계란초밥).title("계란초밥").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_유부초밥 = Post.builder().food(food_유부초밥).title("유부초밥").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_캘리포니아롤 = Post.builder().food(food_캘리포니아롤).title("캘리포니아롤").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_장어초밥 = Post.builder().food(food_장어초밥).title("장어초밥").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_대아 = Post.builder().food(food_대아).title("대아").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_랍스타 = Post.builder().food(food_랍스타).title("랍스타").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_대게 = Post.builder().food(food_대게).title("대게").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();
//        Post post_조개구이 = Post.builder().food(food_조개구이).title("조개구이").content("").archived(false).member(NIBH_FELIS_MEMBER).attachment(Attachment.builder().name("test.png").path("").build()).build();


    }
}
