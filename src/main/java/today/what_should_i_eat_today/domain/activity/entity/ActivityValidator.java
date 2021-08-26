package today.what_should_i_eat_today.domain.activity.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import today.what_should_i_eat_today.domain.activity.dao.ActivityRepository;
import today.what_should_i_eat_today.domain.member.dao.MemberRepository;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.CannotExecuteException;
import today.what_should_i_eat_today.global.error.exception.UnauthorizedUserException;
import today.what_should_i_eat_today.global.error.exception.UserNotFoundException;
import today.what_should_i_eat_today.global.security.UserPrincipal;

@RequiredArgsConstructor
@Component
public class ActivityValidator {

    private final MemberRepository memberRepository;


    public void validateAuth(Activity activity) {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        final Member member = memberRepository.findByEmail(principal.getEmail()).orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        if (!activity.getMember().equals(member))
            throw new UnauthorizedUserException(ErrorCode.UNAUTHORIZED_USER);
    }
}
