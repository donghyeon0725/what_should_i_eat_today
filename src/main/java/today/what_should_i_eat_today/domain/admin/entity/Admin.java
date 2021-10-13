package today.what_should_i_eat_today.domain.admin.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import today.what_should_i_eat_today.global.common.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties(value = "password")
public class Admin extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }
}
