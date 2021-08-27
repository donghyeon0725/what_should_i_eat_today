package today.what_should_i_eat_today.domain.recommend.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.member.dao.MemberRepository;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.recommend.dao.RecommendRepository;
import today.what_should_i_eat_today.domain.recommend.dto.RecommendCommand;
import today.what_should_i_eat_today.domain.recommend.entity.Recommend;
import today.what_should_i_eat_today.domain.review.dao.ReviewRepository;
import today.what_should_i_eat_today.domain.review.entity.Review;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.ResourceNotFoundException;
import today.what_should_i_eat_today.global.error.exception.UserNotFoundException;
import today.what_should_i_eat_today.global.security.UserPrincipal;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecommendService {
    private final RecommendRepository recommendRepository;

    private final MemberRepository memberRepository;

    private final ReviewRepository reviewRepository;


    public Member getMember() {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return memberRepository.findByEmail(principal.getEmail()).orElseThrow(() -> new UserNotFoundException(ErrorCode.INVALID_INPUT_VALUE));
    }


    @Transactional
    public Long createRecommend(RecommendCommand command) {
        command.createValidate();


        Member member = getMember();
        final Review review = reviewRepository.findById(command.getReviewId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        final Optional<Recommend> recommend_opt = recommendRepository.findByReviewAndMember(review, member);

        if (recommend_opt.isPresent()) {
            recommendRepository.delete(recommend_opt.get());
        }

        Recommend result = Recommend.builder().review(review).member(member).type(command.getType()).build();
        recommendRepository.save(result);

        return result.getId();
    }

    @Transactional
    public void cancelRecommend(RecommendCommand command) {
        command.cancelValidate();

        final Recommend recommend = recommendRepository.findById(command.getRecommendId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        recommendRepository.delete(recommend);
    }

}
