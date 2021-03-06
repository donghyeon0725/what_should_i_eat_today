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
    @DisplayName("????????? ???????????? ?????? ????????? ??????")
    void test() {
        Member member = Member.builder()
                .name("root")
                .build();

        Post post = Post.builder()
                .content("???1")
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
    @DisplayName("?????? ????????? ??? ????????? ???????????? ????????????")
    void test2() {
        Member member = Member.builder()
                .name("root")
                .build();

        Post post = Post.builder()
                .content("???1")
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
    @DisplayName("??? ?????? ??????")
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
                .title("?????? ??????")
                .content("?????? ??????")
                .build();

        // when
        Post post = postService.createPost(command);


        // then
        assertThat(post).isNotNull();
        assertThat(post.getTitle()).isEqualTo("?????? ??????");
        assertThat(post.getMember().getName()).isEqualTo("martin");
        assertThat(post.getFood().getName()).isEqualTo("rice");

        List<Food> foods = em.createQuery("SELECT f FROM Food f", Food.class).getResultList();
        assertThat(foods).hasSize(1);

        storageService.delete(String.format("%s.%s", fileName, contentType));
    }

    @Test
    @DisplayName("????????? ???????????? ?????? ?????? ??? ?????? ??????")
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
                .memberId(1L) // member ??? persist ?????? ????????? ????????? null ??? ????????? ?????? 1L ??? ???????????????
                .foodId(food.getId())
                .file(mockMultipartFile)
                .title("?????? ??????")
                .content("?????? ??????")
                .build();

        // when & then
        assertThrows(ResourceNotFoundException.class, () -> postService.createPost(command));
    }

    @Test
    @DisplayName("????????? ???????????? ?????? ?????? ??? ?????? ??????")
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
                .title("?????? ??????")
                .content("?????? ??????")
                .build();

        // when & then
        assertThrows(ResourceNotFoundException.class, () -> postService.createPost(command));
    }

    @Test
    @DisplayName("?????? ????????? ???????????? ?????? ?????? ????????????")
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
                .title("?????? ??????")
                .content("?????? ??????")
                .build();

        // when & then
        assertThrows(InvalidStatusException.class, () -> postService.createPost(command));
    }

    @Test
    @DisplayName("??? ???????????? ??????")
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
                .title("?????? ??????")
                .content("?????? ??????")
                .build();
        Post post = postService.createPost(createCommand);
        assertThat(post).isNotNull();
        assertThat(post.getTitle()).isEqualTo("?????? ??????");
        assertThat(post.getContent()).isEqualTo("?????? ??????");
        assertThat(post.getMember().getName()).isEqualTo("martin");
        assertThat(post.getFood().getName()).isEqualTo("rice");


        // given
        String updateFilename = "updateFile";   // saved FileName
        MockMultipartFile updateMockMultipartFile = getMockMultipartFile(updateFilename, contentType, filePath);

        PostUpdateCommand updateCommand = PostUpdateCommand.builder()
                .postId(post.getId())
                .memberId(member.getId())
                .title("????????? ??????")
                .content("????????? ??????")
                .file(updateMockMultipartFile)
                .build();

        // when
        Post updatedPost = postService.updatePost(updateCommand);

        // then
        assertThat(updatedPost.getTitle()).isEqualTo("????????? ??????");
        assertThat(updatedPost.getContent()).isEqualTo("????????? ??????");
        assertThat(updatedPost.getAttachment().getName()).isEqualTo("updateFile.txt");

        storageService.delete(String.format("%s.%s", fileName, contentType));
        storageService.delete(String.format("%s.%s", updateFilename, contentType));
    }

    @Test
    @DisplayName("??? ?????? ??????")
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
                .title("?????? ??????")
                .content("?????? ??????")
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
    @DisplayName("??? ?????? ??????")
    void test9() {

        Attachment attachment = Attachment.builder().name("?????? ??????").path("/static/file/").build();
        Food food = Food.builder().build();
        Member member = Member.builder().name("martin").email("martin@naver.com").profileImg("img").build();

        Post post = Post.builder()
                .title("??? ??????")
                .content("??? ??????")
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
//        assertThat(findPost.getTitle()).isEqualTo("??? ??????");
//        assertThat(findPost.getContent()).isEqualTo("??? ??????");
//        assertThat(findPost.getMember().getName()).isEqualTo("martin");
//        assertThat(findPost.getMember().getEmail()).isEqualTo("martin@naver.com");
//        assertThat(findPost.getAttachment().getPath()).isEqualTo("/static/file/");
    }

    @Test
    @DisplayName("??? ????????? ?????? ??????")
    void test10() {
        for (int i = 0; i < 30; i++) {
            Attachment attachment = Attachment.builder().name("?????? ??????" + i).path("/static/file/" + i).build();
            Food food = Food.builder().build();
            Member member = Member.builder().name("martin" + i).nickName("martin@naver.com" + i).profileImg("img" + i).build();

            Post post = Post.builder()
                    .title("??? ??????" + i)
                    .content("??? ??????" + i)
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
        assertThat(post.getContent()).extracting("title").contains("??? ??????0", "??? ??????1", "??? ??????2", "??? ??????9");

    }

    @Test
    @DisplayName("????????? ????????? post ??? ??? ??? ??????")
    void test11() {


        for (int i = 0; i < 50; i++) {
            Post post = Post.builder()
                    .title("??? ??????" + i)
                    .content("??? ??????" + i)
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
    @DisplayName("????????? ???????????? ?????? ??????")
    void test12() {

        Food food1 = Food.builder().name("???????????????").build();
        Food food2 = Food.builder().name("?????????").build();

        Post post1 = Post.builder().title("????????? ???????????????").food(food1).build();
        Post post2 = Post.builder().title("???????????? ???????????????").food(food1).build();
        Post post3 = Post.builder().title("???????????? ???????????????").food(food1).build();
        Post post4 = Post.builder().title("????????? ?????? ?????????").food(food2).build();

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
        assertThat(posts.getContent().get(0).getFood().getName()).isEqualTo("???????????????");
        assertThat(posts.getContent())
                .extracting("title")
                .containsExactly("????????? ???????????????", "???????????? ???????????????", "???????????? ???????????????")
        ;
    }

    public static MockMultipartFile getMockMultipartFile(String fileName, String contentType, String path) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(path);
        return new MockMultipartFile(fileName, fileName + "." + contentType, contentType, fileInputStream);
    }

//    @Test
//    @DisplayName("?????? ????????? ??? ????????????")
//    void test12() {
//
//        Member martinMember = Member.builder().name("martin").build();
//
//        em.persist(martinMember);
//
//        for (int i = 0; i < 50; i++) {
//            Post post = Post.builder().member(martinMember).title("??? 1").build();
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
    @DisplayName("?????? ?????? ?????? ?????????|????????????")
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
    @DisplayName("?????? ??? ?????? ???????????? ?????? ??? ??? ??????")
    void test14() {
        Member martinMember = Member.builder().name("martin").build();
        Post post = Post.builder().member(martinMember).title("post1").build();

        em.persist(martinMember);
        em.persist(post);
        em.clear();

        assertThrows(InvalidStatusException.class, () -> postService.updateFavorite(post.getId(), martinMember.getId()));

    }


    @Test
    @DisplayName("?????? ????????? ?????? ?????? 1??? ????????? ????????????")
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
            em.persist(Post.builder().title("?????????"+i).content("?????????"+i).food(food).build());
            em.persist(Post.builder().title("??????????????????"+i).content("??????????????????"+i).food(food).build());
        }

        em.flush();
        em.clear();



        // TODO ????????? 10?????? ???????????? ??????. => ???????????? ???????????????
        List<Post> posts1 = postService.getRandomPostList();
        List<Post> posts2 = postService.getRandomPostList();

        posts1 = posts1.stream().sorted(Comparator.comparing(s->s.getId())).collect(Collectors.toList());
        posts2 = posts2.stream().sorted(Comparator.comparing(s->s.getId())).collect(Collectors.toList());

        // TODO ??? ????????? ????????? ??????????????? ?????????.
        boolean isAllSame = true;
        for (int i=0; i<10; i++) {
            if (posts1.get(i) != posts2.get(i)) {
                isAllSame = false;
                break;
            }
        }

        // TODO ????????? ???????????? ?????????.
        // TODO ???????????? ???????????? ?????????.
        List<Long> foodList1 = posts1.stream().map(s->Long.valueOf(s.getFood().getId())).collect(Collectors.toList());
        List<Long> foodList2 = posts2.stream().map(s->Long.valueOf(s.getFood().getId())).collect(Collectors.toList());
        List<Long> postList1 = posts1.stream().map(s->Long.valueOf(s.getId())).collect(Collectors.toList());
        List<Long> postList2 = posts2.stream().map(s->Long.valueOf(s.getId())).collect(Collectors.toList());
        boolean isAllDifferentFood1 = isAllDifferent(foodList1);
        boolean isAllDifferentFood2 = isAllDifferent(foodList2);
        boolean isAllDifferentPost1 = isAllDifferent(postList1);
        boolean isAllDifferentPost2 = isAllDifferent(postList2);


        assertEquals(10, posts1.size(), "????????? 10?????? ???????????? ??????.");
        assertEquals(10, posts2.size(), "????????? 10?????? ???????????? ??????.");
        assertFalse(isAllSame, "??? ????????? ????????? ??????????????? ?????????.");
        assertTrue(isAllDifferentFood1, "????????? ???????????? ?????????.");
        assertTrue(isAllDifferentFood2, "????????? ???????????? ?????????.");
        assertTrue(isAllDifferentPost1, "???????????? ???????????? ?????????.");
        assertTrue(isAllDifferentPost2, "???????????? ???????????? ?????????.");
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
    @DisplayName("?????? ???????????? ?????? 1??? ??????")
//    @Transactional
//    @Rollback(value = false)
    void test16() {
        Tag cold = Tag.builder().name("?????????").status(TagStatus.USE).build();
        Tag hot = Tag.builder().name("?????????").status(TagStatus.USE).build();
        Tag smooth = Tag.builder().name("????????????").status(TagStatus.USE).build();

        Food food1 = Food.builder().name("??????").deleted(false).build();
        Food food2 = Food.builder().name("??????").deleted(false).build();
        Food food3 = Food.builder().name("??????").deleted(false).build();
        Food food4 = Food.builder().name("??????").deleted(false).build();
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

        Post post1 = Post.builder().food(food1).title("?????? ?????????").build();
        Post post2 = Post.builder().food(food2).title("?????? ?????????").build();
        Post post3 = Post.builder().food(food3).title("?????? ?????????").build();
        Post post4 = Post.builder().food(food4).title("?????? ?????????").build();



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

        // ????????? ???????????? ???
        List<TagRequest> requests1 = Arrays.asList(
                new TagRequest(hot.getId(), null, null),
                new TagRequest(smooth.getId(), null, null)
        );

        final Post result = postService.findByFoodWithTags(requests1);

        assertEquals("?????? ?????????", result.getTitle());

    }

    @Test
    @DisplayName("?????? ???????????? ?????? ?????? ??????")
    void test17() {
        Tag cold = Tag.builder().name("?????????").status(TagStatus.USE).build();
        Tag hot = Tag.builder().name("?????????").status(TagStatus.USE).build();
        Tag smooth = Tag.builder().name("????????????").status(TagStatus.USE).build();

        Food food1 = Food.builder().name("??????").deleted(false).build();
        Food food2 = Food.builder().name("??????").deleted(false).build();
        Food food3 = Food.builder().name("??????").deleted(false).build();
        Food food4 = Food.builder().name("??????").deleted(false).build();
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

        Post post1 = Post.builder().food(food1).title("?????? ?????????").build();
        Post post2 = Post.builder().food(food2).title("?????? ?????????").build();
        Post post3 = Post.builder().food(food3).title("?????? ?????????").build();
        Post post4 = Post.builder().food(food4).title("?????? ?????????").build();



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

        // ????????? ???????????? ???
        List<TagRequest> requests1 = Arrays.asList(
                new TagRequest(hot.getId(), null, null),
                new TagRequest(smooth.getId(), null, null)
        );

        // ????????? ???????????? ???
        List<TagRequest> requests2 = Arrays.asList(
                new TagRequest(cold.getId(), null, null),
                new TagRequest(smooth.getId(), null, null)
        );

        // ?????? & ??????
        List<TagRequest> requests3 = Arrays.asList(
                new TagRequest(cold.getId(), null, null)
        );

        // ?????? & ??????
        List<TagRequest> requests4 = Arrays.asList(
                new TagRequest(smooth.getId(), null, null)
        );

        final List<Post> result1 = postService.findByFoodsWithTags(requests1);
        final List<Post> result2 = postService.findByFoodsWithTags(requests2);
        final List<Post> result3 = postService.findByFoodsWithTags(requests3);
        final List<Post> result4 = postService.findByFoodsWithTags(requests4);

        assertEquals(1, result1.size(), "????????? ????????? ????????? ??? and ???????????? ????????? ???????????? ?????? ???????????? ?????? ????????? ??????.");
        assertEquals(post1.getId(), result1.get(0).getId(), "????????? ????????? ????????? ??? and ???????????? ????????? ???????????? ?????? ???????????? ?????? ????????? ??????.");
        assertEquals(1, result2.size(), "????????? ????????? ????????? ??? and ???????????? ????????? ???????????? ?????? ???????????? ?????? ????????? ??????.");
        assertEquals(post2.getId(), result2.get(0).getId(), "????????? ????????? ????????? ??? and ???????????? ????????? ???????????? ?????? ???????????? ?????? ????????? ??????.");
        assertEquals(2, result3.size());
        assertThat(result3).extracting("title").containsExactly("?????? ?????????", "?????? ?????????");
        assertThat(result4).extracting("title").containsExactly("?????? ?????????", "?????? ?????????", "?????? ?????????");

    }
}
