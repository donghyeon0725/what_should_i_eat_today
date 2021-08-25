package today.what_should_i_eat_today.domain.report.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import today.what_should_i_eat_today.domain.activity.dao.ActivityRepository;
import today.what_should_i_eat_today.domain.activity.entity.Activity;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.member.entity.Profile;
import today.what_should_i_eat_today.domain.member.mock.CustomMockUser;
import today.what_should_i_eat_today.domain.post.entity.Post;
import today.what_should_i_eat_today.domain.post.entity.PostStatus;
import today.what_should_i_eat_today.domain.report.dto.PostReportCommand;
import today.what_should_i_eat_today.domain.report.dto.ProfileReportCommand;
import today.what_should_i_eat_today.domain.report.dto.ReportCommand;
import today.what_should_i_eat_today.domain.report.dto.ReviewReportCommand;
import today.what_should_i_eat_today.domain.report.entity.Report;
import today.what_should_i_eat_today.domain.report.entity.ReportStatus;
import today.what_should_i_eat_today.domain.report.entity.ReportType;
import today.what_should_i_eat_today.domain.review.entity.Review;
import today.what_should_i_eat_today.domain.review.entity.ReviewStatus;
import today.what_should_i_eat_today.domain.review.entity.ReviewType;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReportServiceTest {

    @Autowired
    private ReportService reportService;

    @Autowired
    private EntityManager em;

    @Autowired
    private ActivityRepository activityRepository;

    @Test
    @DisplayName("프로필 리폿하기")
    @CustomMockUser(email = "report1234@test.com", roles = "USER")
    void test1() {
        Member reporter = Member.builder().name("너한번당해봐라").email("report1234@test.com").nickName("리폿한다잉?").build();
        Member reported = Member.builder().name("킹받네").email("reported1234@test.com").nickName("리폿당함?").build();

        em.persist(reporter);
        em.persist(reported);
        em.flush();
        em.clear();

        ProfileReportCommand command = ProfileReportCommand.builder().title("신고좀 해주세요").reportedMemberId(reported.getId()).build();

        final Long reportId = reportService.createReport(command);

        Report report = em.find(Report.class, reportId);

        assertNotNull(report);
        assertNotNull(report.getReportedMember(), "리폿 당한 멤버의 정보가 있어야 한다.");
        assertNotNull(report.getMember(), "리폿한 사람의 정보가 있어야 한다.");
    }

    @Test
    @DisplayName("포스트 리폿하기")
    @CustomMockUser(email = "report1234@test.com", roles = "USER")
    void test2() {
        Member reporter = Member.builder().name("너한번당해봐라").email("report1234@test.com").nickName("리폿한다잉?").build();
        Member reported = Member.builder().name("킹받네").email("reported1234@test.com").nickName("리폿당함?").build();
        Post post = Post.builder().member(reported).title("title").content("content").build();

        em.persist(post);
        em.persist(reporter);
        em.persist(reported);
        em.flush();
        em.clear();

        PostReportCommand command = PostReportCommand.builder().title("신고좀 해주세요").postId(post.getId()).build();

        final Long reportId = reportService.createReport(command);

        Report report = em.find(Report.class, reportId);

        assertNotNull(report);
        assertNotNull(report.getReportedMember(), "리폿 당한 멤버의 정보가 있어야 한다.");
        assertNotNull(report.getMember(), "리폿한 사람의 정보가 있어야 한다.");
        assertNotNull(report.getPost(), "리폿할 게시물이 있어야 한다.");
    }


    @Test
    @DisplayName("리뷰 리폿하기")
    @CustomMockUser(email = "report1234@test.com", roles = "USER")
    void test3() {
        Member reporter = Member.builder().name("너한번당해봐라").email("report1234@test.com").nickName("리폿한다잉?").build();
        Member reported = Member.builder().name("킹받네").email("reported1234@test.com").nickName("리폿당함?").build();
        Review review = Review.builder().reviewType(ReviewType.REVIEW).content("신고당할게시물").member(reported).build();

        em.persist(review);
        em.persist(reporter);
        em.persist(reported);
        em.flush();
        em.clear();

        ReviewReportCommand command = ReviewReportCommand.builder().title("신고좀요").content("content").reviewId(review.getId()).build();

        final Long reportId = reportService.createReport(command);

        Report report = em.find(Report.class, reportId);

        assertNotNull(report);
        assertNotNull(report.getReportedMember(), "리폿 당한 멤버의 정보가 있어야 한다.");
        assertNotNull(report.getMember(), "리폿한 사람의 정보가 있어야 한다.");
        assertNotNull(report.getReview(), "리폿할 리뷰가 있어야 한다.");
    }


    @Test
    @DisplayName("신고 리스트 확인하기")
    void test4() {
        for (int i=0; i<30; i++) {

            ReportType reportType = null;
            if (i % 3 == 0)
                reportType = ReportType.POST;
            else if (i % 3 == 1)
                reportType = ReportType.POST;
            else if (i % 3 == 2)
                reportType = ReportType.POST;

            ReportStatus status = null;
            if (i % 2 == 0)
                status = ReportStatus.NOT_APPROVED;
            else if (i % 2 == 1)
                status = ReportStatus.APPROVED;

            em.persist(Report.builder().status(status).type(reportType).title("리폿"+i).build());
        }

        // 신고 1개 정확하게 검색하기
        // 신고 이름으로 10개 검색하기
        // 리폿 상태로 검색한 모든 타입이 일치하는지 확인
        ReportCommand command1 = new ReportCommand();
        ReportCommand command2 = new ReportCommand();
        ReportCommand command3 = new ReportCommand();
        command1.setTitle("리폿29");
        command2.setTitle("리폿");
        command3.setStatus(ReportStatus.NOT_APPROVED);


        PageRequest pageRequest = PageRequest.of(0, 10);
        final Page<Report> command_exactly_one = reportService.getReportList(command1, pageRequest);
        final Page<Report> command_approximately_ten = reportService.getReportList(command2, pageRequest);
        final Page<Report> command_search_with_type = reportService.getReportList(command3, pageRequest);

        assertEquals(1, command_exactly_one.getContent().size(), "이름으로 검색할 수 있어야 한다.");
        assertEquals(10, command_approximately_ten.getContent().size(), "이름으로 검색할 수 있어야 한다.");
        assertThat(command_search_with_type).extracting("status").containsOnly(ReportStatus.NOT_APPROVED).withFailMessage("타입으로 검색할 수 있어야 한다.");
    }


    @Test
    @DisplayName("포스트 신고 승인 처리하기")
    @CustomMockUser(email = "report1234@test.com", roles = "USER")
    void test5() {
        Member reporter = Member.builder().name("너한번당해봐라").email("report1234@test.com").nickName("리폿한다잉?").build();
        Member reported = Member.builder().name("킹받네").email("reported1234@test.com").nickName("리폿당함?").build();
        Post post = Post.builder().content("content").member(reported).build();

        Report report = Report.builder()
                .post(post).title("title").content("content").type(ReportType.POST)
                .status(ReportStatus.NOT_APPROVED).member(reporter).reportedMember(reported).build();

        em.persist(reporter);
        em.persist(reported);
        em.persist(post);
        em.persist(report);
        em.flush();
        em.clear();

        ReportCommand command = new ReportCommand(report.getId(), ReportStatus.APPROVED);

        reportService.processReport(command);

        PageRequest pageRequest = PageRequest.of(0, 10);

        final Page<Activity> activities_reporter = activityRepository.findByMember(reporter, pageRequest);
        final Page<Activity> activities_reported = activityRepository.findByMember(reported, pageRequest);
        final Post findPost = em.find(Post.class, post.getId());

        assertEquals(1, activities_reporter.getContent().size(), "신고자에게 알람이 간다.");
        assertEquals(1, activities_reported.getContent().size(), "피신고자에게 알람이 간다.");
        assertEquals(PostStatus.BLIND, findPost.getStatus(), "post 는 블라인드 처리가 된다.");
    }


    @Test
    @DisplayName("프로필 신고 승인 처리하기")
    @CustomMockUser(email = "report1234@test.com", roles = "USER")
    void test6() {
        Member reporter = Member.builder().name("너한번당해봐라").email("report1234@test.com").nickName("리폿한다잉?").build();
        Member reported = Member.builder().name("킹받네").email("reported1234@test.com").nickName("리폿당함?").build();

        Report report = Report.builder()
                .title("title").content("content").type(ReportType.PROFILE)
                .status(ReportStatus.NOT_APPROVED).member(reporter).reportedMember(reported).build();

        em.persist(reporter);
        em.persist(reported);
        em.persist(report);
        em.flush();
        em.clear();

        ReportCommand command = new ReportCommand(report.getId(), ReportStatus.APPROVED);

        reportService.processReport(command);

        PageRequest pageRequest = PageRequest.of(0, 10);

        final Page<Activity> activities_reporter = activityRepository.findByMember(reporter, pageRequest);
        final Page<Activity> activities_reported = activityRepository.findByMember(reported, pageRequest);
        final Member findMember = em.find(Member.class, reported.getId());

        assertEquals(1, activities_reporter.getContent().size(), "신고자에게 알람이 간다.");
        assertEquals(1, activities_reported.getContent().size(), "피신고자에게 알람이 간다.");
        assertEquals(Profile.DEFAULT.getPath(), findMember.getProfileImg(), "프로필이 기본 프로필로 변경된다.");
    }


    @Test
    @DisplayName("리뷰 신고 승인 처리하기")
    @CustomMockUser(email = "report1234@test.com", roles = "USER")
    void test7() {
        Member reporter = Member.builder().name("너한번당해봐라").email("report1234@test.com").nickName("리폿한다잉?").build();
        Member reported = Member.builder().name("킹받네").email("reported1234@test.com").nickName("리폿당함?").build();
        Post post = Post.builder().title("title").content("content").member(reporter).build();
        Review review_reported = Review.builder().content("content").member(reported).reviewType(ReviewType.REVIEW).post(post).build();

        Report report = Report.builder()
                .title("title").content("content").type(ReportType.REVIEW).review(review_reported)
                .status(ReportStatus.NOT_APPROVED).member(reporter).reportedMember(reported).build();


        em.persist(reporter);
        em.persist(reported);
        em.persist(post);
        em.persist(review_reported);
        em.persist(report);
        em.flush();
        em.clear();

        ReportCommand command = new ReportCommand(report.getId(), ReportStatus.APPROVED);

        reportService.processReport(command);

        PageRequest pageRequest = PageRequest.of(0, 10);

        final Page<Activity> activities_reporter = activityRepository.findByMember(reporter, pageRequest);
        final Page<Activity> activities_reported = activityRepository.findByMember(reported, pageRequest);
        final Review findReview = em.find(Review.class, review_reported.getId());

        assertEquals(1, activities_reporter.getContent().size(), "신고자에게 알람이 간다.");
        assertEquals(1, activities_reported.getContent().size(), "피신고자에게 알람이 간다.");
        assertEquals(ReviewStatus.BLIND, findReview.getStatus(), "리뷰가 블라인드 처리 된다.");
    }

}
