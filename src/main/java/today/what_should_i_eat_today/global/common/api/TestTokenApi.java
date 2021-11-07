package today.what_should_i_eat_today.global.common.api;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import today.what_should_i_eat_today.domain.admin.dao.AdminRepository;
import today.what_should_i_eat_today.domain.admin.entity.Admin;
import today.what_should_i_eat_today.domain.member.dao.MemberRepository;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.global.security.TokenProvider;
import today.what_should_i_eat_today.global.security.UserPrincipal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static today.what_should_i_eat_today.event.DataInitializer2.ADMIN_EMAIL;

@Profile(value = {"local"})
@RestController
@RequiredArgsConstructor
public class TestTokenApi {

    private static final long TEST_EXPIRE_DAY = 60L * 24 * 60 * 60 * 3600;
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final AdminRepository adminRepository;

    @GetMapping("/test-token")
    public ResponseEntity<?> getTestToken() {

        Admin ADMIN = adminRepository.findByEmail(ADMIN_EMAIL).orElseThrow(() -> new UsernameNotFoundException("not found"));

        List<Member> members = memberRepository.findAll();
        Map<String, String> map = new HashMap<>();

        map.put("VUE_APP_TEMP_ADMIN_TOKEN", tokenProvider.createTestToken(UserPrincipal.create(ADMIN), TEST_EXPIRE_DAY));


        for (Member member : members) {
            map.put(String.format("VUE_APP_TEMP_%s_TOKEN", member.getName().toUpperCase().replaceAll(" ", "_")), tokenProvider.createTestToken(UserPrincipal.create(member), TEST_EXPIRE_DAY));
        }

        return ResponseEntity.ok(map);
    }
}
