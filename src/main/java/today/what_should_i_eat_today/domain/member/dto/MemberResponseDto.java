package today.what_should_i_eat_today.domain.member.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
public class MemberResponseDto {

    private Long id;

    private String profileImg;

    private String email;

    private String name;

    private String nickName;

    private String providerId;

}
