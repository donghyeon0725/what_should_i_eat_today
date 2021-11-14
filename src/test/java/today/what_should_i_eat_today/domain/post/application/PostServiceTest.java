package today.what_should_i_eat_today.domain.post.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.activity.dto.PostCreateCommand;
import today.what_should_i_eat_today.domain.activity.dto.PostUpdateCommand;
import today.what_should_i_eat_today.domain.favorite.entity.Favorite;
import today.what_should_i_eat_today.domain.food.entity.Food;
import today.what_should_i_eat_today.domain.food.entity.FoodTag;
import today.what_should_i_eat_today.domain.likes.entity.Likes;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.model.Attachment;
import today.what_should_i_eat_today.domain.post.entity.Post;
import today.what_should_i_eat_today.domain.tag.dto.TagRequest;
import today.what_should_i_eat_today.domain.tag.entity.Tag;
import today.what_should_i_eat_today.domain.tag.entity.TagStatus;
import today.what_should_i_eat_today.global.common.application.file.FileSystemStorageService;
import today.what_should_i_eat_today.global.error.exception.InvalidStatusException;
import today.what_should_i_eat_today.global.error.exception.ResourceNotFoundException;

import javax.persistence.EntityManager;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@ActiveProfiles("local")
@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private PostService postService;

    @Autowired
    private FileSystemStorageService storageService;

    @Test
    @DisplayName("좋아요 하지않은 글에 좋아요 하기")
    void test() {
        Member member = Member.builder()
                .name("root")
                .build();

        Post post = Post.builder()
                .content("글1")
                .build();

        em.persist(member);
        em.persist(post);
        em.clear();

        boolean isLike = postService.updateLike(post.getId(), member.getId());
        Post findPost = em.find(Post.class, post.getId());
        List<Likes> findLikesList = em.createQuery("SELECT l from Likes l", Likes.class).getResultList();

        assertThat(isLike).isTrue();
        assertThat(findPost.getLikesSet()).hasSize(1);
        assertThat(findLikesList).hasSize(1);

    }

    @Test
    @DisplayName("이미 좋아요 한 글에는 좋아요를 삭제한다")
    void test2() {
        Member member = Member.builder()
                .name("root")
                .build();

        Post post = Post.builder()
                .content("글1")
                .build();

        Likes likes = Likes.builder()
                .post(post)
                .member(member)
                .build();

        em.persist(member);
        em.persist(post);
        em.persist(likes);
        em.clear();

        boolean isLike = postService.updateLike(post.getId(), member.getId());
        Post findPost = em.find(Post.class, post.getId());
        List<Likes> findLikesList = em.createQuery("SELECT l from Likes l", Likes.class).getResultList();

        assertThat(isLike).isFalse();
        assertThat(findPost.getLikesSet()).isEmpty();
        assertThat(findLikesList).isEmpty();

    }

    @Test
    @DisplayName("글 작성 성공")
    void test3() throws IOException {

        // given
        String fileName = ".gitignore1";   // saved FileName
        String contentType = "gitignore2"; // saved FileExtension
        String filePath = ".gitignore";    // upload FilePath/FileName
        MockMultipartFile mockMultipartFile = getMockMultipartFile(fileName, contentType, filePath);

        Member member = Member.builder()
                .name("martin")
                .build();

        Food food = Food.builder()
                .member(member)
                .name("rice")
                .build();

        em.persist(member);
        em.persist(food);
        em.clear();

        PostCreateCommand command = PostCreateCommand.builder()
                .memberId(member.getId())
                .foodId(food.getId())
                .file(mockMultipartFile)
                .title("글의 제목")
                .content("글의 내용")
                .build();

        // when
        Post post = postService.createPost(command);


        // then
        assertThat(post).isNotNull();
        assertThat(post.getTitle()).isEqualTo("글의 제목");
        assertThat(post.getMember().getName()).isEqualTo("martin");
        assertThat(post.getFood().getName()).isEqualTo("rice");

        List<Food> foods = em.createQuery("SELECT f FROM Food f", Food.class).getResultList();
        assertThat(foods).hasSize(1);

        storageService.delete(String.format("%s.%s", fileName, contentType));
    }

    @Test
    @DisplayName("멤버가 존재하지 않는 경우 글 작성 실패")
    void test4() throws IOException {

        // given
        String fileName = ".gitignore";
        String contentType = "gitignore";
        String filePath = ".gitignore";
        MockMultipartFile mockMultipartFile = getMockMultipartFile(fileName, contentType, filePath);

        Member member = Member.builder()
                .name("martin")
                .build();

        Food food = Food.builder()
                .member(member)
                .name("rice")
                .build();

        em.persist(food);
        em.clear();

        PostCreateCommand command = PostCreateCommand.builder()
                .memberId(1L) // member 를 persist 하지 않았기 때문에 null 이 됨으로 직접 1L 를 주었습니다
                .foodId(food.getId())
                .file(mockMultipartFile)
                .title("글의 제목")
                .content("글의 내용")
                .build();

        // when & then
        assertThrows(ResourceNotFoundException.class, () -> postService.createPost(command));
    }

    @Test
    @DisplayName("음식이 존재하지 않는 경우 글 작성 실패")
    void test5() throws IOException {

        // given
        String fileName = ".gitignore";
        String contentType = "gitignore";
        String filePath = ".gitignore";
        MockMultipartFile mockMultipartFile = getMockMultipartFile(fileName, contentType, filePath);

        Member member = Member.builder()
                .name("martin")
                .build();

        em.persist(member);
        em.clear();

        PostCreateCommand command = PostCreateCommand.builder()
                .memberId(member.getId())
                .foodId(1L)
                .file(mockMultipartFile)
                .title("글의 제목")
                .content("글의 내용")
                .build();

        // when & then
        assertThrows(ResourceNotFoundException.class, () -> postService.createPost(command));
    }

    @Test
    @DisplayName("음식 사진이 존재하지 않는 경우 실패한다")
    void test6() throws IOException {

        // given
        Member member = Member.builder()
                .name("martin")
                .build();

        Food food = Food.builder()
                .member(member)
                .name("rice")
                .build();

        em.persist(member);
        em.persist(food);
        em.clear();

        PostCreateCommand command = PostCreateCommand.builder()
                .memberId(member.getId())
                .foodId(food.getId())
                .title("글의 제목")
                .content("글의 내용")
                .build();

        // when & then
        assertThrows(InvalidStatusException.class, () -> postService.createPost(command));
    }

    @Test
    @DisplayName("글 수정하기 성공")
    void test7() throws IOException {
        // save init
        String fileName = "createFile";   // saved FileName
        String contentType = "txt"; // saved FileExtension
        String filePath = ".gitignore";    // upload FilePath/FileName
        MockMultipartFile mockMultipartFile = getMockMultipartFile(fileName, contentType, filePath);
        Member member = Member.builder()
                .name("martin")
                .build();
        Food food = Food.builder()
                .member(member)
                .name("rice")
                .build();
        em.persist(member);
        em.persist(food);
        em.clear();
        PostCreateCommand createCommand = PostCreateCommand.builder()
                .memberId(member.getId())
                .foodId(food.getId())
                .file(mockMultipartFile)
                .title("글의 제목")
                .content("글의 내용")
                .build();
        Post post = postService.createPost(createCommand);
        assertThat(post).isNotNull();
        assertThat(post.getTitle()).isEqualTo("글의 제목");
        assertThat(post.getContent()).isEqualTo("글의 내용");
        assertThat(post.getMember().getName()).isEqualTo("martin");
        assertThat(post.getFood().getName()).isEqualTo("rice");


        // given
        String updateFilename = "updateFile";   // saved FileName
        MockMultipartFile updateMockMultipartFile = getMockMultipartFile(updateFilename, contentType, filePath);

        PostUpdateCommand updateCommand = PostUpdateCommand.builder()
                .postId(post.getId())
                .memberId(member.getId())
                .title("수정된 제목")
                .content("수정된 내용")
                .file(updateMockMultipartFile)
                .build();

        // when
        Post updatedPost = postService.updatePost(updateCommand);

        // then
        assertThat(updatedPost.getTitle()).isEqualTo("수정된 제목");
        assertThat(updatedPost.getContent()).isEqualTo("수정된 내용");
        assertThat(updatedPost.getAttachment().getName()).isEqualTo("updateFile.txt");

        storageService.delete(String.format("%s.%s", fileName, contentType));
        storageService.delete(String.format("%s.%s", updateFilename, contentType));
    }

    @Test
    @DisplayName("글 삭제 성공")
    void test8() {
        // given
        Member member = Member.builder()
                .name("martin")
                .build();
        Food food = Food.builder()
                .member(member)
                .name("rice")
                .build();

        Post post = Post.builder()
                .member(member)
                .food(food)
                .title("글의 제목")
                .content("글의 내용")
                .attachment(Attachment.builder().build())
                .build();

        em.persist(member);
        em.persist(food);
        em.persist(post);
        em.clear();

        // when
        postService.deletePost(post.getId(), member.getId());

        // then
        Post deletedPost = em.find(Post.class, post.getId());
        assertThat(deletedPost.isArchived()).isTrue();
    }

    @Test
    @DisplayName("글 보기 성공")
    void test9() {

        Attachment attachment = Attachment.builder().name("음식 사진").path("/static/file/").build();
        Food food = Food.builder().build();
        Member member = Member.builder().name("martin").email("martin@naver.com").profileImg("img").build();

        Post post = Post.builder()
                .title("글 제목")
                .content("글 내용")
                .attachment(attachment)
                .food(food)
                .member(member)
                .build();

        em.persist(member);
        em.persist(food);
        em.persist(post);
        em.clear();

//        Post findPost = postService.getPost(post.getId());
//        assertThat(findPost).isNotNull();
//        assertThat(findPost.getTitle()).isEqualTo("글 제목");
//        assertThat(findPost.getContent()).isEqualTo("글 내용");
//        assertThat(findPost.getMember().getName()).isEqualTo("martin");
//        assertThat(findPost.getMember().getEmail()).isEqualTo("martin@naver.com");
//        assertThat(findPost.getAttachment().getPath()).isEqualTo("/static/file/");
    }

    @Test
    @DisplayName("글 리스트 보기 성공")
    void test10() {
        for (int i = 0; i < 30; i++) {
            Attachment attachment = Attachment.builder().name("음식 사진" + i).path("/static/file/" + i).build();
            Food food = Food.builder().build();
            Member member = Member.builder().name("martin" + i).nickName("martin@naver.com" + i).profileImg("img" + i).build();

            Post post = Post.builder()
                    .title("글 제목" + i)
                    .content("글 내용" + i)
                    .attachment(attachment)
                    .food(food)
                    .member(member)
                    .build();

            em.persist(post);
        }
        em.clear();

        PageRequest pageable = PageRequest.of(0, 10);
        Page<Post> post = postService.getPosts(pageable);

        assertThat(post.getContent()).hasSize(10);
        assertThat(post.getContent()).extracting("title").contains("글 제목0", "글 제목1", "글 제목2", "글 제목9");

    }

    @Test
    @DisplayName("멤버는 제거된 post 를 볼 수 없다")
    void test11() {


        for (int i = 0; i < 50; i++) {
            Post post = Post.builder()
                    .title("글 제목" + i)
                    .content("글 내용" + i)
                    .archived(i % 2 == 0)
                    .build();
            em.persist(post);
        }
        em.clear();

        PageRequest pageReq = PageRequest.of(0, 10);

        Page<Post> posts = postService.getPosts(pageReq);

        assertThat(posts.getTotalElements()).isEqualTo(25);
    }

    @Test
    @DisplayName("음식에 해당하는 글들 조회")
    void test12() {

        Food food1 = Food.builder().name("김치볶음밥").build();
        Food food2 = Food.builder().name("돈까스").build();

        Post post1 = Post.builder().title("김가네 김치볶음밥").food(food1).build();
        Post post2 = Post.builder().title("김밥천국 김치볶음밥").food(food1).build();
        Post post3 = Post.builder().title("김밥나라 김치볶음밥").food(food1).build();
        Post post4 = Post.builder().title("김가네 치즈 돈까스").food(food2).build();

        em.persist(food1);
        em.persist(food2);

        em.persist(post1);
        em.persist(post2);
        em.persist(post3);
        em.persist(post4);

        em.clear();

        PageRequest page = PageRequest.of(0, 10);
        Page<Post> posts = postService.getPostsByFoodId(food1.getId(), page);

        assertThat(posts).isNotNull();
        assertThat(posts.getContent()).hasSize(3);
        assertThat(posts.getContent().get(0).getFood().getName()).isEqualTo("김치볶음밥");
        assertThat(posts.getContent())
                .extracting("title")
                .containsExactly("김가네 김치볶음밥", "김밥천국 김치볶음밥", "김밥나라 김치볶음밥")
        ;
    }

    public static MockMultipartFile getMockMultipartFile(String fileName, String contentType, String path) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(path);
        return new MockMultipartFile(fileName, fileName + "." + contentType, contentType, fileInputStream);
    }

//    @Test
//    @DisplayName("내가 작성한 글 조회하기")
//    void test12() {
//
//        Member martinMember = Member.builder().name("martin").build();
//
//        em.persist(martinMember);
//
//        for (int i = 0; i < 50; i++) {
//            Post post = Post.builder().member(martinMember).title("글 1").build();
//            em.persist(post);
//        }
//
//        em.clear();
//
//        int size = 10;
//        PageRequest page = PageRequest.of(0, size);
//
//        String[] martinArr = new String[size];
//        Arrays.fill(martinArr, "martin");
//
//        Page<Post> myPosts = postService.getMyPosts(martinMember.getId(), page);
//
//        assertThat(myPosts)
//                .hasSize(10)
//                .extracting("member")
//                .extracting("name")
//                .containsExactly(martinArr)
//        ;
//    }

    @Test
    @DisplayName("다른 사람 글에 찜하기|취소하기")
    void test13() {

        Member martinMember = Member.builder().name("martin").build();
        Member tomMember = Member.builder().name("tom").build();

        em.persist(martinMember);
        em.persist(tomMember);

        Post post1 = Post.builder().member(tomMember).title("post1").build();
        Post post2 = Post.builder().member(tomMember).title("post2").build();
        Post post3 = Post.builder().member(tomMember).title("post3").build();

        em.persist(post1);
        em.persist(post2);
        em.persist(post3);
        em.clear();


        boolean addFavoriteSuccess1 = postService.updateFavorite(post1.getId(), martinMember.getId());
        boolean addFavoriteSuccess2 = postService.updateFavorite(post2.getId(), martinMember.getId());
        boolean addFavoriteSuccess3 = postService.updateFavorite(post3.getId(), martinMember.getId());
        assertThat(addFavoriteSuccess1).isTrue();
        assertThat(addFavoriteSuccess2).isTrue();
        assertThat(addFavoriteSuccess3).isTrue();

        List<Favorite> favorites = em.createQuery("select f from Favorite f where f.member.id = " + martinMember.getId(), Favorite.class).getResultList();
        assertThat(favorites).hasSize(3);
        favorites.forEach(favorite -> System.out.printf("%s \t", favorite.getPost().getTitle()));


        boolean removeFavoriteSuccess = postService.updateFavorite(post1.getId(), martinMember.getId());
        assertThat(removeFavoriteSuccess).isFalse();

        favorites = em.createQuery("select f from Favorite f where f.member.id = " + martinMember.getId(), Favorite.class).getResultList();
        assertThat(favorites).hasSize(2);
    }

    @Test
    @DisplayName("내가 쓴 글에 대해서는 찜을 할 수 없다")
    void test14() {
        Member martinMember = Member.builder().name("martin").build();
        Post post = Post.builder().member(martinMember).title("post1").build();

        em.persist(martinMember);
        em.persist(post);
        em.clear();

        assertThrows(InvalidStatusException.class, () -> postService.updateFavorite(post.getId(), martinMember.getId()));

    }


    @Test
    @DisplayName("랜덤 음식에 대한 상위 1개 포스트 가져오기")
//    @Transactional
//    @Rollback(value = false)
    void test15() {
        for (int i=0; i<60; i++) {
            Food food = null;
            if (i % 2 == 0) {
                food = Food.builder().name("test" + i).deleted(true).build();
            } else {
                food = Food.builder().name("test" + i).deleted(false).build();
            }

            em.persist(food);
            em.persist(Post.builder().title("타이틀"+i).content("콘텐츠"+i).food(food).build());
            em.persist(Post.builder().title("타이틀타이틀"+i).content("콘텐츠콘텐츠"+i).food(food).build());
        }

        em.flush();
        em.clear();



        // TODO 음식은 10개를 가져와야 한다. => 포스트가 존재한다면
        List<Post> posts1 = postService.getRandomPostList();
        List<Post> posts2 = postService.getRandomPostList();

        posts1 = posts1.stream().sorted(Comparator.comparing(s->s.getId())).collect(Collectors.toList());
        posts2 = posts2.stream().sorted(Comparator.comparing(s->s.getId())).collect(Collectors.toList());

        // TODO 두 결과과 완전히 일치해서는 안된다.
        boolean isAllSame = true;
        for (int i=0; i<10; i++) {
            if (posts1.get(i) != posts2.get(i)) {
                isAllSame = false;
                break;
            }
        }

        // TODO 음식이 겹쳐서는 안된다.
        // TODO 포스트가 겹쳐서는 안된다.
        List<Long> foodList1 = posts1.stream().map(s->Long.valueOf(s.getFood().getId())).collect(Collectors.toList());
        List<Long> foodList2 = posts2.stream().map(s->Long.valueOf(s.getFood().getId())).collect(Collectors.toList());
        List<Long> postList1 = posts1.stream().map(s->Long.valueOf(s.getId())).collect(Collectors.toList());
        List<Long> postList2 = posts2.stream().map(s->Long.valueOf(s.getId())).collect(Collectors.toList());
        boolean isAllDifferentFood1 = isAllDifferent(foodList1);
        boolean isAllDifferentFood2 = isAllDifferent(foodList2);
        boolean isAllDifferentPost1 = isAllDifferent(postList1);
        boolean isAllDifferentPost2 = isAllDifferent(postList2);


        assertEquals(10, posts1.size(), "음식은 10개를 가져와야 한다.");
        assertEquals(10, posts2.size(), "음식은 10개를 가져와야 한다.");
        assertFalse(isAllSame, "두 결과과 완전히 일치해서는 안된다.");
        assertTrue(isAllDifferentFood1, "음식이 겹쳐서는 안된다.");
        assertTrue(isAllDifferentFood2, "음식이 겹쳐서는 안된다.");
        assertTrue(isAllDifferentPost1, "포스트가 겹쳐서는 안된다.");
        assertTrue(isAllDifferentPost2, "포스트가 겹쳐서는 안된다.");
    }

    public boolean isAllDifferent(List<Long> elements) {
        Set<Long> set = new HashSet<>();
        for (int i=0; i<elements.size(); i++) {
            Long element = elements.get(i);

            if (set.contains(element)) {
                return false;
            }
            set.add(element);
        }
        return true;
    }



    @Test
    @DisplayName("태그 여러개로 음식 1개 찾기")
//    @Transactional
//    @Rollback(value = false)
    void test16() {
        Tag cold = Tag.builder().name("차가운").status(TagStatus.USE).build();
        Tag hot = Tag.builder().name("뜨거운").status(TagStatus.USE).build();
        Tag smooth = Tag.builder().name("부드러운").status(TagStatus.USE).build();

        Food food1 = Food.builder().name("홍차").deleted(false).build();
        Food food2 = Food.builder().name("케익").deleted(false).build();
        Food food3 = Food.builder().name("얼음").deleted(false).build();
        Food food4 = Food.builder().name("크림").deleted(false).build();
        FoodTag foodTag1_1 = FoodTag.builder().tag(hot).build();
        FoodTag foodTag1_2 = FoodTag.builder().tag(smooth).build();
        FoodTag foodTag2_1 = FoodTag.builder().tag(cold).build();
        FoodTag foodTag2_2 = FoodTag.builder().tag(smooth).build();
        FoodTag foodTag3 = FoodTag.builder().tag(cold).build();
        FoodTag foodTag4 = FoodTag.builder().tag(smooth).build();

        food1.addFoodTags(foodTag1_1);
        food1.addFoodTags(foodTag1_2);
        food2.addFoodTags(foodTag2_1);
        food2.addFoodTags(foodTag2_2);
        food3.addFoodTags(foodTag3);
        food4.addFoodTags(foodTag4);

        Post post1 = Post.builder().food(food1).title("홍차 포스트").build();
        Post post2 = Post.builder().food(food2).title("케익 포스트").build();
        Post post3 = Post.builder().food(food3).title("얼음 포스트").build();
        Post post4 = Post.builder().food(food4).title("크림 포스트").build();



        em.persist(cold);
        em.persist(hot);
        em.persist(smooth);
        em.persist(food1);
        em.persist(food2);
        em.persist(food3);
        em.persist(food4);
        em.persist(foodTag1_1);
        em.persist(foodTag1_2);
        em.persist(foodTag2_1);
        em.persist(foodTag2_2);
        em.persist(foodTag3);
        em.persist(foodTag4);
        em.persist(post1);
        em.persist(post2);
        em.persist(post3);
        em.persist(post4);
        em.flush();
        em.clear();

        // 홍차를 찾아와야 함
        List<TagRequest> requests1 = Arrays.asList(
                new TagRequest(hot.getId(), null, null),
                new TagRequest(smooth.getId(), null, null)
        );

        final Post result = postService.findByFoodWithTags(requests1);

        assertEquals("홍차 포스트", result.getTitle());

    }

    @Test
    @DisplayName("태그 여러개로 음식 모두 찾기")
    void test17() {
        Tag cold = Tag.builder().name("차가운").status(TagStatus.USE).build();
        Tag hot = Tag.builder().name("뜨거운").status(TagStatus.USE).build();
        Tag smooth = Tag.builder().name("부드러운").status(TagStatus.USE).build();

        Food food1 = Food.builder().name("홍차").deleted(false).build();
        Food food2 = Food.builder().name("케익").deleted(false).build();
        Food food3 = Food.builder().name("얼음").deleted(false).build();
        Food food4 = Food.builder().name("크림").deleted(false).build();
        FoodTag foodTag1_1 = FoodTag.builder().tag(hot).build();
        FoodTag foodTag1_2 = FoodTag.builder().tag(smooth).build();
        FoodTag foodTag2_1 = FoodTag.builder().tag(cold).build();
        FoodTag foodTag2_2 = FoodTag.builder().tag(smooth).build();
        FoodTag foodTag3 = FoodTag.builder().tag(cold).build();
        FoodTag foodTag4 = FoodTag.builder().tag(smooth).build();

        food1.addFoodTags(foodTag1_1);
        food1.addFoodTags(foodTag1_2);
        food2.addFoodTags(foodTag2_1);
        food2.addFoodTags(foodTag2_2);
        food3.addFoodTags(foodTag3);
        food4.addFoodTags(foodTag4);

        Post post1 = Post.builder().food(food1).title("홍차 포스트").build();
        Post post2 = Post.builder().food(food2).title("케익 포스트").build();
        Post post3 = Post.builder().food(food3).title("얼음 포스트").build();
        Post post4 = Post.builder().food(food4).title("크림 포스트").build();



        em.persist(cold);
        em.persist(hot);
        em.persist(smooth);
        em.persist(food1);
        em.persist(food2);
        em.persist(food3);
        em.persist(food4);
        em.persist(foodTag1_1);
        em.persist(foodTag1_2);
        em.persist(foodTag2_1);
        em.persist(foodTag2_2);
        em.persist(foodTag3);
        em.persist(foodTag4);
        em.persist(post1);
        em.persist(post2);
        em.persist(post3);
        em.persist(post4);
        em.flush();
        em.clear();

        // 홍차를 찾아와야 함
        List<TagRequest> requests1 = Arrays.asList(
                new TagRequest(hot.getId(), null, null),
                new TagRequest(smooth.getId(), null, null)
        );

        // 케익을 찾아와야 함
        List<TagRequest> requests2 = Arrays.asList(
                new TagRequest(cold.getId(), null, null),
                new TagRequest(smooth.getId(), null, null)
        );

        // 케익 & 얼음
        List<TagRequest> requests3 = Arrays.asList(
                new TagRequest(cold.getId(), null, null)
        );

        // 홍차 & 크림
        List<TagRequest> requests4 = Arrays.asList(
                new TagRequest(smooth.getId(), null, null)
        );

        final List<Post> result1 = postService.findByFoodsWithTags(requests1);
        final List<Post> result2 = postService.findByFoodsWithTags(requests2);
        final List<Post> result3 = postService.findByFoodsWithTags(requests3);
        final List<Post> result4 = postService.findByFoodsWithTags(requests4);

        assertEquals(1, result1.size(), "태그를 여러개 주었을 때 and 조건으로 음식이 검색되고 해당 포스트가 조회 되어야 한다.");
        assertEquals(post1.getId(), result1.get(0).getId(), "태그를 여러개 주었을 때 and 조건으로 음식이 검색되고 해당 포스트가 조회 되어야 한다.");
        assertEquals(1, result2.size(), "태그를 여러개 주었을 때 and 조건으로 음식이 검색되고 해당 포스트가 조회 되어야 한다.");
        assertEquals(post2.getId(), result2.get(0).getId(), "태그를 여러개 주었을 때 and 조건으로 음식이 검색되고 해당 포스트가 조회 되어야 한다.");
        assertEquals(2, result3.size());
        assertThat(result3).extracting("title").containsExactly("케익 포스트", "얼음 포스트");
        assertThat(result4).extracting("title").containsExactly("홍차 포스트", "케익 포스트", "크림 포스트");

    }
}
