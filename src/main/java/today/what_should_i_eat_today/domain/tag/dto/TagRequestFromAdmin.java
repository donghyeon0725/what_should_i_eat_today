package today.what_should_i_eat_today.domain.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import today.what_should_i_eat_today.domain.tag.entity.TagStatus;

@Data
@NoArgsConstructor
public class TagRequestFromAdmin extends TagRequest {

    @Builder
    public TagRequestFromAdmin(Long tagId, String name, TagStatus status) {
        super(tagId, name, status);
    }
}
