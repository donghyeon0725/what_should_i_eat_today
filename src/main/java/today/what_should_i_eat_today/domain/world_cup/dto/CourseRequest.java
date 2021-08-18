package today.what_should_i_eat_today.domain.world_cup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import today.what_should_i_eat_today.domain.model.Status;

import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequest {
    private Long courseId;

    private Long packageId;

    @Size(min = 2, message = "과정 주제의 길이는 2자 이상이여야 합니다.")
    private String subject;

    private Status status;
}
