package today.what_should_i_eat_today.domain.review.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.member.dao.MemberRepository;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.post.dao.PostRepository;
import today.what_should_i_eat_today.domain.post.entity.Post;
import today.what_should_i_eat_today.domain.review.dto.ReviewCommand;
import today.what_should_i_eat_today.domain.review.dao.ReviewRepository;
import today.what_should_i_eat_today.domain.review.entity.Review;
import today.what_should_i_eat_today.domain.review.entity.ReviewValidator;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.ResourceNotFoundException;
import today.what_should_i_eat_today.global.security.UserPrincipal;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewValidator reviewValidator;

    private final ReviewRepository reviewRepository;

    private final MemberRepository memberRepository;

    private final PostRepository postRepository;

    @Transactional
    public Long createReview(ReviewCommand command) {
        command.createValidate();

        UserPrincipal principal = (UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member findMember = memberRepository.findByEmail(principal.getEmail()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));
        Post findPost = postRepository.findById(command.getPostId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        Review review = Review.builder().post(findPost).member(findMember).parent(null).content(command.getContent()).build();
        reviewRepository.save(review);

        return review.getId();
    }

    @Transactional
    public Long replyReview(ReviewCommand command) {
        command.replyValidate();

        UserPrincipal principal = (UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member findMember = memberRepository.findByEmail(principal.getEmail()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));
        Post findPost = postRepository.findById(command.getPostId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));
        Review findReview = reviewRepository.findById(command.getParentId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        Review child = Review.builder().post(findPost).member(findMember).content(command.getContent()).build();
        findReview.addChildReview(child, reviewValidator);

        reviewRepository.save(child);

        return child.getId();
    }

    @Transactional
    public void updateReview(ReviewCommand command) {
        command.updateValidate();

        Review findReview = reviewRepository.findById(command.getId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));
        findReview.changeContent(command.getContent(), reviewValidator);
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    // 포스트에 해당하는 리뷰 리스트
    public Page<Review> getReviewList(ReviewCommand command, Pageable pageable) {
        Page<Review> review = reviewRepository.findAllReviewByPostIdAndParentNull(command.getPostId(), pageable);
        review.forEach(s -> {
            s.getChild().forEach(c -> c.getId());
        });
        return review;
    }




}
