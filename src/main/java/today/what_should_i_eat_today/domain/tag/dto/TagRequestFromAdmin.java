package today.what_should_i_eat_today.domain.tag.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import today.what_should_i_eat_today.domain.tag.entity.TagStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class TagRequestFromAdmin extends TagRequest {

    @Builder
    public TagRequestFromAdmin(Long tagId, String name, TagStatus status) {
        super(tagId, name, status);
    }
}
