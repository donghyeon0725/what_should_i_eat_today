package today.what_should_i_eat_today.domain.report.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import today.what_should_i_eat_today.domain.report.application.ReportService;
import today.what_should_i_eat_today.domain.report.dto.*;
import today.what_should_i_eat_today.domain.report.entity.Report;
import today.what_should_i_eat_today.domain.report.entity.ReportStatus;
import today.what_should_i_eat_today.domain.report.entity.ReportType;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReportApi {

    private final ReportService reportService;

    @GetMapping("/reports")
    @Secured("ROLE_ADMIN")
    public Page<ReportResponseDto> getReportList(@PageableDefault Pageable pageable, ReportCommand reportCommand) {
        final Page<Report> reportList = reportService.getReportList(reportCommand, pageable);
        return reportList.map(s -> new ReportResponseDto(s.getType(), s.getTitle(), s.getContent(), s.getStatus()));
    }


    @PostMapping("/reports")
    @Secured("ROLE_USER")
    public ResponseEntity createReport(@RequestBody ReportRequestDto reportRequestDto) {
        final ReportCommand command = reportRequestDto.getCommand();

        if (ReportType.PROFILE.equals(command.getType())) {
            reportService.createReport((ProfileReportCommand) command);
        }

        if (ReportType.REVIEW.equals(command.getType())) {
            reportService.createReport((ReviewReportCommand) command);
        }

        if (ReportType.POST.equals(command.getType())) {
            reportService.createReport((PostReportCommand) command);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("reports/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity findById(@PathVariable Long id) {
        return ResponseEntity.ok(reportService.findById(id));
    }

    @PatchMapping("/reports/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity processReport(@PathVariable Long id, ReportStatus status) {
        reportService.processReport(new ReportCommand(id, status));

        return ResponseEntity.ok().build();
    }




}
