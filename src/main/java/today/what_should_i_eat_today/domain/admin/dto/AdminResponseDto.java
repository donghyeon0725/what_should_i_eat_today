package today.what_should_i_eat_today.domain.admin.dto;

import lombok.Data;
import today.what_should_i_eat_today.domain.admin.entity.Admin;

@Data
public class AdminResponseDto {
    private String email;

    public AdminResponseDto(String email) {
        this.email = email;
    }

    public AdminResponseDto(Admin admin) {
        this.email = admin.getEmail();
    }
}
