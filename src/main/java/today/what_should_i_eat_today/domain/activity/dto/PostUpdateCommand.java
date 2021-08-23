package today.what_should_i_eat_today.domain.activity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateCommand {

    private Long memberId;

    private Long postId;

    private MultipartFile file;

    private String title;

    private String content;

}
