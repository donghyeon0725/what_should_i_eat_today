package today.what_should_i_eat_today.domain.qna.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import today.what_should_i_eat_today.domain.member.dao.MemberRepository;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.CannotExecuteException;
import today.what_should_i_eat_today.global.error.exception.UnauthorizedUserException;
import today.what_should_i_eat_today.global.error.exception.UserNotFoundException;
import today.what_should_i_eat_today.global.security.UserPrincipal;

@Component
@RequiredArgsConstructor
public class QnaValidator {

    private final MemberRepository memberRepository;

    public Member getMember() {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return memberRepository.findByEmail(principal.getEmail()).orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
    }


    public void updateValidate(String title, String content, QnaStatus status, Member owner) {
        if (status == QnaStatus.PROCESSED)
            throw new CannotExecuteException(ErrorCode.ALREADY_PROCESSED);

        if (title.length() > 255)
            throw new CannotExecuteException(ErrorCode.INVALID_INPUT_VALUE);

        if (!StringUtils.hasText(content))
            throw new CannotExecuteException(ErrorCode.INVALID_INPUT_VALUE);


        Member member = getMember();
        if (!owner.equals(member))
            throw new UnauthorizedUserException(ErrorCode.UNAUTHORIZED_USER);
    }

    public void qnaReviewAddValidate(Qna qna, QnaReview qnaReview) {
        if (qna.getQnaReview() != null)
            throw new CannotExecuteException(ErrorCode.INVALID_INPUT_VALUE);

        if (!StringUtils.hasText(qnaReview.getContent()))
            throw new CannotExecuteException(ErrorCode.INVALID_INPUT_VALUE);

        if (qnaReview.getAdmin() == null)
            throw new CannotExecuteException(ErrorCode.INVALID_INPUT_VALUE);
    }


    public void qnaReviewUpdateValidate(String content) {
        if (!StringUtils.hasText(content))
            throw new CannotExecuteException(ErrorCode.INVALID_INPUT_VALUE);

    }

    public void qnaStatusValidate(QnaStatus qnaStatus) {
        if (qnaStatus == null)
            throw new CannotExecuteException(ErrorCode.INVALID_INPUT_VALUE);
    }
}
