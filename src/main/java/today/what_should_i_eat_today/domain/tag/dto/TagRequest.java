package today.what_should_i_eat_today.domain.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import today.what_should_i_eat_today.domain.tag.entity.TagStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagRequest {
    private Long tagId;

    private String name;

    private TagStatus status;
}
