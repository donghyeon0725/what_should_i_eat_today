package today.what_should_i_eat_today.domain.world_cup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackageRequest {

    private Long packageId;

    private Long questionId;

    @Size(min = 2, message = "주제의 길이는 2자 초과여야 합니다")
    private String subject;
}
