package today.what_should_i_eat_today.domain.activity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequest {

    @NotNull
    private Long foodId;

//    @NotNull(message = "음식사진은 반드시 있어야 합니다")
//    private MultipartFile file;

    @Size(min = 1, max = 50, message = "제목은 최소 1자 이상 50자 이하입니다")
    private String title;

    @Size(min = 1, max = 3000, message = "내용은 최소 1자 이상 3000자 이하입니다")
    private String content;

    public PostCreateCommand toCommand(Long memberId, MultipartFile file) {

        return PostCreateCommand.builder()
                .memberId(memberId)
                .foodId(foodId)
                .file(file)
                .foodId(foodId)
                .title(title)
                .content(content)
                .build();
    }
}
