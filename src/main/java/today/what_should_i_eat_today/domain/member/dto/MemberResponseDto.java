package today.what_should_i_eat_today.domain.member.dto;

import lombok.*;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.report.dto.PostReportCommand;
import today.what_should_i_eat_today.domain.report.dto.ProfileReportCommand;
import today.what_should_i_eat_today.domain.report.dto.ReviewReportCommand;
import today.what_should_i_eat_today.domain.report.entity.Report;
import today.what_should_i_eat_today.domain.report.entity.ReportType;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class MemberResponseDto {

    private Long id;

    private String profileImg;

    private String email;

    private String name;

    private String nickName;

    private String providerId;

    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.profileImg = member.getProfileImg();
        this.email = member.getEmail();
        this.name = member.getName();
        this.nickName = member.getNickName();
        this.providerId = member.getProviderId();
    }

}
