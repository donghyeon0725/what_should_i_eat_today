package today.what_should_i_eat_today.domain.activity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import today.what_should_i_eat_today.domain.model.Attachment;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateCommand {

    private Long memberId;

    private Long foodId;

    private MultipartFile file;

    private String title;

    private String content;

    public Attachment getAttachment() {
        return Attachment.builder()
                .path(file.getOriginalFilename())
                .name(file.getName())
                .build();
    }
}
