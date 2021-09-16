package today.what_should_i_eat_today.global.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import today.what_should_i_eat_today.domain.admin.dao.AdminRepository;
import today.what_should_i_eat_today.domain.admin.entity.Admin;
import today.what_should_i_eat_today.domain.member.dao.MemberRepository;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.model.AuthProvider;

import java.util.UUID;


@SpringBootTest
@ActiveProfiles("local")
class TokenProviderTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private TokenProvider tokenProvider;

    @Test
    void getToken() {

        Member member = Member.builder()
                .name("sout1217")
                .nickName("sout1217")
                .email("testtesttesttest1217@naver.com")
                .providerId(UUID.randomUUID().toString())
                .provider(AuthProvider.naver)
                .profileImg("http://localhost:8080/images")
                .build();

        Admin admin = Admin.builder()
                .email("admin@naver.com")
                .password("1234")
                .build();


        Member saveMember = memberRepository.save(member);
        Admin saveAdmin = adminRepository.save(admin);

        UserPrincipal userPrincipal = UserPrincipal.create(saveMember);
        UserPrincipal adminPrincipal = UserPrincipal.create(saveAdmin);

        String token = tokenProvider.createToken(userPrincipal);
        String adminToken = tokenProvider.createToken(adminPrincipal);

        System.out.println(token);
        System.out.println(adminToken);

        /*
         * 서버를 실행할 때 마다 토큰 값이 달라지는 것을 알고 있어야 합니다
         * 만약 클라이언트에서 브라우저를 닫아도 쿠키에 저장되어 인증하는 방식으로 사용하였다면,
         * 서버가 터지거나 닫혔다 재실행되면 인증을 실패합니다?
         */
        // assertThat("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjI5MjgwNTgyLCJleHAiOjE2MzAxNDQ1ODJ9.d5CbdsqNpCS954_yiJPIns5wSt697deJ17bBGSvuKS6NkqJ4gpN98kZC3EyaLaJO7MwGXgHFCAo2RcAgNDu37w").isEqualTo(token);
    }
}
