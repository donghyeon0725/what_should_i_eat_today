package today.what_should_i_eat_today.domain.member.api;

import org.assertj.core.api.SoftAssertions;
import org.json.JSONException;
import org.json.JSONObject;
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

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("local")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MeApiTest {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TestRestTemplate template;

    @LocalServerPort
    int randomServerPort;

    private String token;

    @BeforeEach
    void setUp() {
        Member member = Member.builder()
                .name("sout1217")
                .nickName("heydaze")
                .email("sout1217@naver.com")
                .providerId(UUID.randomUUID().toString())
                .provider(AuthProvider.naver)
                .profileImg("http://localhost:8080/images")
                .build();

        Member martinMember = Member.builder()
                .name("martin0327")
                .nickName("martin")
                .email("martin0327@gmail.com")
                .providerId(UUID.randomUUID().toString())
                .provider(AuthProvider.google)
                .profileImg("http://localhost:8080/images")
                .build();

        Member saveMember = memberRepository.save(member);
        memberRepository.save(martinMember);

        UserPrincipal userPrincipal = UserPrincipal.create(saveMember);

        token = tokenProvider.createToken(userPrincipal);
    }

    @Test
    @DisplayName("JWT로 내 닉네임과 프로필 이미지 찾기")
    void findMyNicknameAndProfileImageWithJWT() {
        String url = "http://localhost:" + randomServerPort + "/api/v1/me";
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        ResponseEntity<MemberResponseDto> result = template.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                MemberResponseDto.class
        );

        MemberResponseDto body = result.getBody();

        System.out.println(result);

        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        softly.assertThat(body).isNotNull();
        softly.assertThat(body.getEmail()).isEqualTo("sout1217@naver.com");
        softly.assertThat(body.getProfileImg()).endsWith("/images");

        softly.assertAll();
    }

    @Test
    @DisplayName("JWT 로 내 닉네임 및 프로필 이미지 수정")
    void updateMyNicknameAndProfileImageWithJWT() throws JSONException {
        String url = "http://localhost:" + randomServerPort + "/api/v1/me/profile";

        String updateNickName = "root";
        String updateProfileImage = "http://root/image";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        JSONObject json = new JSONObject();
        json.put("nickName", updateNickName);
        json.put("profileImage", updateProfileImage);

        ResponseEntity<MemberResponseDto> result = template.exchange(
                url,
                HttpMethod.PUT,
                new HttpEntity<>(json.toString(), headers),
                MemberResponseDto.class
        );

        MemberResponseDto body = result.getBody();

        // 모든 assertions 을 실행한 후 실패 내역만 확인할 때 사용
        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        softly.assertThat(body).isNotNull();
        softly.assertThat(body.getNickName()).isEqualTo(updateNickName);
        softly.assertThat(body.getProfileImg()).isEqualTo(updateProfileImage);

        softly.assertAll();
    }

    @Test
    @DisplayName("중복된 닉네임으로 변경할 수 없습니다")
    void duplicateNicknamesCannotBeChanged() throws JSONException {
        String url = "http://localhost:" + randomServerPort + "/api/v1/me/profile";

        String updateNickName = "martin";
        String updateProfileImage = "http://root/image";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        JSONObject json = new JSONObject();
        json.put("nickName", updateNickName);
        json.put("profileImage", updateProfileImage);

        ResponseEntity<String> result = template.exchange(
                url,
                HttpMethod.PUT,
                new HttpEntity<>(json.toString(), headers),
                String.class
        );

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(body).containsPattern("\"code\":\"S001\"");

    }
}