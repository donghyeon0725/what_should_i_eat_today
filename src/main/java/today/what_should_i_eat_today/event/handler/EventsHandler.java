package today.what_should_i_eat_today.event.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import today.what_should_i_eat_today.domain.activity.dao.ActivityRepository;
import today.what_should_i_eat_today.domain.activity.entity.Activity;
import today.what_should_i_eat_today.domain.activity.entity.ActivityType;
import today.what_should_i_eat_today.domain.favorite.dao.FavoriteRepository;
import today.what_should_i_eat_today.domain.favorite.entity.Favorite;
import today.what_should_i_eat_today.domain.likes.dao.LikesRepository;
import today.what_should_i_eat_today.domain.likes.entity.Likes;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.post.entity.Post;
import today.what_should_i_eat_today.domain.qna.entity.Qna;
import today.what_should_i_eat_today.domain.report.entity.Report;
import today.what_should_i_eat_today.domain.report.entity.ReportType;
import today.what_should_i_eat_today.domain.review.entity.Review;
import today.what_should_i_eat_today.event.event.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EventsHandler {
    private final ActivityRepository activityRepository;

    private final LikesRepository likesRepository;

    private final FavoriteRepository favoriteRepository;

    private final EntityManager em;

    @Async
    @Transactional
    @EventListener
    public void handle(ReviewActivityEvent event) {
        Review review = event.getReview();
        Post post = review.getPost();
        Member postOwner = post.getMember();

        activityRepository.save(Activity.builder().post(post).member(postOwner).review(review).type(ActivityType.REVIEW).build());
    }

    @Async
    @Transactional
    @EventListener
    public void handle(ReplyActivityEvent event) {
        Review review = event.getReply();
        Post post = review.getPost();
        Member reviewOwner = review.getParent().getMember();

        activityRepository.save(Activity.builder().post(post).member(reviewOwner).review(review).type(ActivityType.REPLY).build());
    }

    @Async
    @Transactional
    @EventListener
    public void handle(PostReportEvent event) {
        Report report = event.getReport();
        Member reporter = report.getMember();
        Member reported = report.getReportedMember();
        Post reportedPost = report.getPost();

        // 게시글 블라인드 처리
        reportedPost.delete();

        // 피신고자에게 알람
        activityRepository.save(
                Activity.builder()
                        .post(reportedPost)
                        .member(reported)
                        .type(ActivityType.REPORT)
                        .reportType(ReportType.POST)
                        .build()
        );

        // 신고자에게 알람
        activityRepository.save(
                Activity.builder()
                        .member(reporter)
                        .type(ActivityType.REPORT)
                        .reportType(ReportType.POST)
                        .build()
        );
    }

    @Async
    @Transactional
    @EventListener
    public void handle(ReviewReportEvent event) {
        Report report = event.getReport();
        Member reported = report.getReportedMember();
        Review reportedReview = report.getReview();
        Member reporter = report.getMember();

        // 댓글 블라인드 처리
        reportedReview.blind();

        // 신고 처리
        activityRepository.save(
                Activity.builder()
                        .review(reportedReview)
                        .member(reported)
                        .reportType(ReportType.REVIEW)
                        .type(ActivityType.REPORT)
                        .build()
        );

        // 신고자에게 알람
        activityRepository.save(
                Activity.builder()
                        .member(reporter)
                        .type(ActivityType.REPORT)
                        .reportType(ReportType.PROFILE)
                        .build()
        );
    }

    @Async
    @Transactional
    @EventListener
    public void handle(ProfileReportEvent event) {
        Report report = event.getReport();
        Member reported = report.getReportedMember();
        Member reporter = report.getMember();

        // 기본 프로필로 변경
        reported.changeToDefaultProfile();

        activityRepository.save(
                Activity.builder()
                        .member(reported)
                        .type(ActivityType.REPORT)
                        .reportType(ReportType.PROFILE)
                        .build()
        );

        // 신고자에게 알람
        activityRepository.save(
                Activity.builder()
                        .member(reporter)
                        .type(ActivityType.REPORT)
                        .reportType(ReportType.PROFILE)
                        .build()
        );
    }

    @Async
    @Transactional
    @EventListener
    public void handle(QnaReviewEvent event) {
        Qna qna = event.getQna();
        Member qnaOwner = qna.getMember();

        activityRepository.save(Activity.builder().member(qnaOwner).qna(qna).type(ActivityType.QNA_REVIEW).build());
    }

    // 좋아요
    @Async
    @Transactional
    @EventListener
    public void handle(LikesEvent event) {
        Member member = event.getMember();
        Post post = event.getPost();

        likesRepository.save(Likes.builder().member(member).post(post).build());
        post.incrementLikes();
    }

    // 찜하기
    @Async
    @Transactional
    @EventListener
    public void handle(FavoriteEvent event) {
        Member member = event.getMember();
        Post post = event.getPost();

        favoriteRepository.save(Favorite.builder().post(post).member(member).build());
        post.incrementFavorites();

    }

    // 좋아요 취소
    @Async
    @Transactional
    @EventListener
    public void handle(DislikeEvent event) {
        Post post = event.getPost();

        Optional<Likes> likes = likesRepository.findByPostAndMember(post, event.getMember());

        likes.ifPresent((value) -> {
            likesRepository.delete(value);
            post.decrementLikes();
        });
    }


    // 찜하기 취소
    @Async
    @Transactional
    @EventListener
    public void handle(UnFavoriteEvent event) {
        Post post = event.getPost();

        Optional<Favorite> favorite = favoriteRepository.findByPostAndMember(post, event.getMember());


        favorite.ifPresent((value) -> {
            favoriteRepository.delete(value);
            post.decrementFavorites();
        });
    }


}
