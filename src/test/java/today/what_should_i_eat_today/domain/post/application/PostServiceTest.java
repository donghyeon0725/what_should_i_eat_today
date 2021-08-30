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
import today.what_should_i_eat_today.domain.food.entity.Food;
import today.what_should_i_eat_today.domain.likes.entity.Likes;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.model.Attachment;
import today.what_should_i_eat_today.domain.post.entity.Post;
import today.what_should_i_eat_today.global.common.application.file.FileSystemStorageService;
import today.what_should_i_eat_today.global.error.exception.InvalidStatusException;
import today.what_should_i_eat_today.global.error.exception.ResourceNotFoundException;

import javax.persistence.EntityManager;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


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

        Post findPost = postService.getPost(post.getId());
        assertThat(findPost).isNotNull();
        assertThat(findPost.getTitle()).isEqualTo("글 제목");
        assertThat(findPost.getContent()).isEqualTo("글 내용");
        assertThat(findPost.getMember().getName()).isEqualTo("martin");
        assertThat(findPost.getMember().getEmail()).isEqualTo("martin@naver.com");
        assertThat(findPost.getAttachment().getPath()).isEqualTo("/static/file/");
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

}