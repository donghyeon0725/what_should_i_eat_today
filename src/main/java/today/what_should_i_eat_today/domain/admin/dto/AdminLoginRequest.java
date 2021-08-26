package today.what_should_i_eat_today.domain.admin.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class AdminLoginRequest {

    @Email(message = "이메일 형식이 올바르지 않습니다")
    private String email;

    @NotNull(message = "패스워드는 반드시 입력해야 합니다")
    private String password;
}
