package today.what_should_i_eat_today.domain.member.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import today.what_should_i_eat_today.domain.member.dao.MemberRepository;
import today.what_should_i_eat_today.global.security.CurrentUser;
import today.what_should_i_eat_today.global.security.UserPrincipal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberApi {

    private final MemberRepository memberRepository;

    @GetMapping("/members")
    public ResponseEntity<?> findMember(@CurrentUser UserPrincipal principal) {
        return ResponseEntity.ok(memberRepository.findById(principal.getId()));
    }

}
