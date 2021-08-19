package today.what_should_i_eat_today.domain.member.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import today.what_should_i_eat_today.domain.member.dao.MemberRepository;
import today.what_should_i_eat_today.domain.member.dto.MemberResponseDto;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.model.AuthProvider;
import today.what_should_i_eat_today.global.security.TokenProvider;
import today.what_should_i_eat_today.global.security.UserPrincipal;

import java.util.UUID;

@ActiveProfiles("local")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberApiTestRestTemplateTest {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TestRestTemplate template;

    @LocalServerPort
    int randomServerPort;

    private String token;

    private Long id;

    @BeforeEach
    void setUp() {

        Member member = Member.builder()
                .name("sout1217")
                .nickName("sout1217")
                .email("sout1217@naver.com")
                .providerId(UUID.randomUUID().toString())
                .provider(AuthProvider.naver)
                .profileImg("http://localhost:8080/images")
                .build();

        Member saveMember = memberRepository.save(member);

        UserPrincipal userPrincipal = UserPrincipal.create(saveMember);

        token = tokenProvider.createToken(userPrincipal);
        id = member.getId();
    }

    @Test
    @DisplayName("토큰을 통한 RestTemplate 인증")
    public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {

        String url = "http://localhost:" + randomServerPort + "/api/v1/members/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        ResponseEntity<MemberResponseDto> result = template.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                MemberResponseDto.class
        );

        System.out.println("token= " + token);
        System.out.println("url = " + url);
        System.out.println(result);
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}