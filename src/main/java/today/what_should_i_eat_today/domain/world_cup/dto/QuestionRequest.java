package today.what_should_i_eat_today.domain.world_cup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.ResourceNotFoundException;

import javax.validation.constraints.Size;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequest {
    private Long questionId;
    private Long tagId;

    @Size(min = 2, message = "질문의 내용이 너무 짧습니다.")
    private String content;

    public void updateValidateCheck() {
        if (questionId == null)
            throw new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND);

        if (tagId == null)
            throw new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND);
    }
}
