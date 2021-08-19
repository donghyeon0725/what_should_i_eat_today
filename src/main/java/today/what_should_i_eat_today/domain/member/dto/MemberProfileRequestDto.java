package today.what_should_i_eat_today.domain.member.dto;

import lombok.*;

import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class MemberProfileRequestDto {

    @Size(max = 255)
    private String nickName;

    @Size(max = 510)
    private String profileImage;
}
