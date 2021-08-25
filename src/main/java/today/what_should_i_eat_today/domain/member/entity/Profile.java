package today.what_should_i_eat_today.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum Profile {
    DEFAULT("여기에 기본 경로를 넣어주세요.");

    private String path;
}
