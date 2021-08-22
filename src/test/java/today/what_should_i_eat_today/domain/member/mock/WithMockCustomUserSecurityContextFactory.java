package today.what_should_i_eat_today.domain.member.mock;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.global.security.UserPrincipal;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<CustomMockUser> {

    @Override
    public SecurityContext createSecurityContext(CustomMockUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        Member member = Member.builder()
                .id(customUser.id())
                .email(customUser.email())
                .build();

        UserPrincipal principal = UserPrincipal.create(member, customUser.roles());

        Authentication auth =
                new UsernamePasswordAuthenticationToken(principal, "password", principal.getAuthorities());
        context.setAuthentication(auth);

        return context;
    }
}
