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
import today.what_should_i_eat_today.domain.review.dao.ReviewRepository;
import today.what_should_i_eat_today.domain.review.dto.ReviewCommand;
import today.what_should_i_eat_today.domain.review.dto.ReviewDto;
import today.what_should_i_eat_today.domain.review.entity.Review;
import today.what_should_i_eat_today.domain.review.entity.ReviewStatus;
import today.what_should_i_eat_today.domain.review.entity.ReviewValidator;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.InvalidStatusException;
import today.what_should_i_eat_today.global.error.exception.ResourceNotFoundException;
import today.what_should_i_eat_today.global.error.exception.UserNotFoundException;
import today.what_should_i_eat_today.global.security.UserPrincipal;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewValidator reviewValidator;

    private final ReviewRepository reviewRepository;

    private final MemberRepository memberRepository;

    private final PostRepository postRepository;


    private Member getMember() {
        UserPrincipal principal = (UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return memberRepository.findByEmail(principal.getEmail()).orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    public Review findById(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }

    @Transactional
    public Long createReview(ReviewCommand command) {
        command.createValidate();

        UserPrincipal principal = (UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member findMember = memberRepository.findByEmail(principal.getEmail()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));
        Post findPost = postRepository.findById(command.getPostId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        Review review = Review.builder().post(findPost).member(findMember).parent(null).content(command.getContent()).build();
        review.placeReview();
        review.show();
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
        child.placeReply();
        child.show();

        reviewRepository.save(child);

        return child.getId();
    }

    @Transactional
    public void updateReview(ReviewCommand command) {
        command.updateValidate();
        memberRepository.findById(command.getMemberId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));
        Review findReview = reviewRepository.findById(command.getId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        findReview.changeContent(command, reviewValidator);
    }

    @Transactional
    public void deleteReview(Long reviewId, Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        if (!review.getMember().equals(member))
            throw new InvalidStatusException(ErrorCode.INVALID_INPUT_VALUE);

        review.delete();

//        reviewRepository.deleteById(reviewId);
    }

    public Long getTotalCountReviewsOfPost(Long postId) {
        return reviewRepository.countAllByPostIdAndStatus(postId, ReviewStatus.SHOW);
    }

    // 포스트에 해당하는 리뷰 리스트
    public Page<Review> getReviewList(ReviewCommand command, Pageable pageable) {
        Page<Review> review = reviewRepository.findAllReviewByPostIdAndParentNull(command.getPostId(), pageable);

        review.forEach(r -> r.getMember().getName());

        // 성능 최적화를 위해 Map으로
//        Map<Long, List<Review>> collect = reviewRepository.findByParentReviewIn(review.getContent()).stream().collect(Collectors.groupingBy(c -> c.getParent().getId()));

        return review;
    }


    public Page<ReviewDto> getReviewDtoList(ReviewCommand command, Pageable pageable) {
        return reviewRepository.findAllDtoByPostIdAndParentNull(command.getPostId(), getMember(), pageable);
    }

    public Page<ReviewDto> getReplyDtoList(Long reviewId, Pageable pageable) {
        return reviewRepository.findAllDtoByReview(reviewId, getMember(), pageable);
    }


    public List<Review> getReviewsForReview(Long postId, Long reviewId) {

        postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));
        reviewRepository.findById(reviewId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        List<Review> reviews = reviewRepository.findAllByPostIdAndParentIdAndStatusOrderByCreatedAtDesc(postId, reviewId, ReviewStatus.SHOW);
        System.out.println("ok");
        return reviews;
    }
}
