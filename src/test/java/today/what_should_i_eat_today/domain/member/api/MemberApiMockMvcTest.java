package today.what_should_i_eat_today.domain.member.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import today.what_should_i_eat_today.domain.member.application.MemberFindService;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.member.mock.CustomMockUser;
import today.what_should_i_eat_today.domain.model.AuthProvider;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("local")
@SpringBootTest
class MemberApiMockMvcTest {

    private MockMvc mvc;

    @MockBean
    private MemberFindService memberFindService;

    @BeforeEach
    void setUp(@Autowired WebApplicationContext context) {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("멤버 아이디로 찾기")
    @CustomMockUser(id = 1L, email = "sout1217@gmail.com")
    void memberFindById() throws Exception {

        // given
        given(memberFindService.findById(1L)).willReturn(
                Member.builder()
                        .id(1L)
                        .name("sout1217")
                        .email("sout1217@gmail.com")
                        .provider(AuthProvider.google)
                        .providerId("123123")
                        .profileImg("http://123123")
                        .nickName(null)
                        .build()
        );

        // when
        ResultActions actions = mvc.perform(get("/api/v1/members/1")
        );

        // then
        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("sout1217"))
        ;
    }
}