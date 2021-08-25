package today.what_should_i_eat_today.domain.post.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import today.what_should_i_eat_today.domain.food.entity.Food;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.member.mock.CustomMockUser;
import today.what_should_i_eat_today.domain.model.Attachment;
import today.what_should_i_eat_today.domain.post.application.PostService;
import today.what_should_i_eat_today.domain.post.entity.Post;

import java.io.FileInputStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("local")
@SpringBootTest
class PostApiMockTest {

    private MockMvc mvc;

    @MockBean
    private PostService postService;

    @BeforeEach
    void setUp(@Autowired WebApplicationContext context) {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("글 작성 성공")
    @CustomMockUser(id = 1L, email = "sout1217@gmail.com")
    void createPost() throws Exception {

        // given
        String fileName = ".gitignore";
        String contentType = "gitignore";
        String filePath = ".gitignore";
        MockMultipartFile mockMultipartFile = new MockMultipartFile(fileName, fileName + "." + contentType, contentType, new FileInputStream(filePath));


        Post post = Post.builder()
                .id(1L)
                .member(Member.builder().id(1L).name("martin").build())
                .food(Food.builder().name("rice").build())
                .attachment(Attachment.builder().name(mockMultipartFile.getName()).path(mockMultipartFile.getOriginalFilename()).build())
                .title("글 제목 1")
                .content("글 내용 1")
                .build();

        // given
        given(postService.createPost(any())).willReturn(post);

        // when
        ResultActions actions = mvc.perform(multipart("/api/v1/posts")
                .file("file", mockMultipartFile.getBytes())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .param("foodId", "1")
                .param("title", "글 제목 1")
                .param("content", "글 내용 1")
        );

        // then
        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("글 제목 1"))
                .andExpect(jsonPath("$.food.name").value("rice"))
        ;
    }
}