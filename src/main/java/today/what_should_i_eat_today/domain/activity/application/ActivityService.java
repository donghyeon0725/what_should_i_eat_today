package today.what_should_i_eat_today.domain.activity.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.activity.dao.ActivityRepository;
import today.what_should_i_eat_today.domain.activity.dto.ActivityCommand;
import today.what_should_i_eat_today.domain.activity.entity.Activity;
import today.what_should_i_eat_today.domain.activity.entity.ActivityValidator;
import today.what_should_i_eat_today.domain.member.dao.MemberRepository;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.ResourceNotFoundException;
import today.what_should_i_eat_today.global.error.exception.UserNotFoundException;
import today.what_should_i_eat_today.global.security.UserPrincipal;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ActivityService {

    private final ActivityRepository activityRepository;

    private final MemberRepository memberRepository;

    private final ActivityValidator activityValidator;

    public Page<Activity> getActivityList(Pageable pageable) {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Member member = memberRepository.findByEmail(principal.getEmail()).orElseThrow(() -> new UserNotFoundException(ErrorCode.INVALID_INPUT_VALUE));

        return activityRepository.findByMember(member, pageable);
    }

    // 삭제
    @Transactional
    public void deleteActivity(ActivityCommand command) {
        final Activity activity = activityRepository.findById(command.getId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.INVALID_INPUT_VALUE));
        activity.validateAuth(activityValidator);

        activityRepository.delete(activity);
    }
}
