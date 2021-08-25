package today.what_should_i_eat_today.domain.report.application;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.omg.CORBA.CODESET_INCOMPATIBLE;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.member.dao.MemberRepository;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.post.dao.PostRepository;
import today.what_should_i_eat_today.domain.post.entity.Post;
import today.what_should_i_eat_today.domain.report.dao.ReportRepository;
import today.what_should_i_eat_today.domain.report.dto.PostReportCommand;
import today.what_should_i_eat_today.domain.report.dto.ProfileReportCommand;
import today.what_should_i_eat_today.domain.report.dto.ReportCommand;
import today.what_should_i_eat_today.domain.report.dto.ReviewReportCommand;
import today.what_should_i_eat_today.domain.report.entity.Report;
import today.what_should_i_eat_today.domain.report.entity.ReportStatus;
import today.what_should_i_eat_today.domain.review.dao.ReviewRepository;
import today.what_should_i_eat_today.domain.review.entity.Review;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.ResourceNotFoundException;
import today.what_should_i_eat_today.global.error.exception.UserNotFoundException;
import today.what_should_i_eat_today.global.security.UserPrincipal;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

    private final ReportRepository reportRepository;

    private final MemberRepository memberRepository;

    private final PostRepository postRepository;

    private final ReviewRepository reviewRepository;


    @Transactional
    public Long createReport(ProfileReportCommand command) {
        command.validate();

        UserPrincipal principal = (UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member reporter = memberRepository.findByEmail(principal.getEmail()).orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
        Member reported = memberRepository.findById(command.getReportedMemberId()).orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        Report report = Report.builder().title(command.getTitle()).content(command.getContent()).member(reporter).reportedMember(reported).type(command.getType()).build();
        report.notApprove();
        reportRepository.save(report);

        return report.getId();
    }

    @Transactional
    public Long createReport(PostReportCommand command) {
        command.validate();

        UserPrincipal principal = (UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member reporter = memberRepository.findByEmail(principal.getEmail()).orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
        Post reported = postRepository.findById(command.getPostId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        Report report = Report.builder().title(command.getTitle()).content(command.getContent()).member(reporter).post(reported).reportedMember(reported.getMember()).type(command.getType()).build();
        report.notApprove();
        reportRepository.save(report);

        return report.getId();
    }

    @Transactional
    public Long createReport(ReviewReportCommand command) {
        command.validate();

        UserPrincipal principal = (UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member reporter = memberRepository.findByEmail(principal.getEmail()).orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
        Review reported = reviewRepository.findById(command.getReviewId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        Report report = Report.builder().title(command.getTitle()).content(command.getContent()).member(reporter).review(reported).reportedMember(reported.getMember()).type(command.getType()).build();
        report.notApprove();
        reportRepository.save(report);

        return report.getId();
    }

    public Page<Report> getReportList(ReportCommand command, Pageable pageable) {
        return reportRepository.findByTitleAndStatus(command.getTitle(), command.getStatus(), pageable);
    }

    public Report findById(Long reportId) {
        return reportRepository.findById(reportId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));
    }

    @Transactional
    public void processReport(ReportCommand command) {
        command.statusValidate();

        final Report report = reportRepository.findById(command.getId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        if (command.getStatus() == ReportStatus.APPROVED)
            report.processApprove(report.getType());
        else if (command.getStatus() == ReportStatus.NOT_APPROVED)
            report.processNotApprove();
    }


}
