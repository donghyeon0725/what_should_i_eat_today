package today.what_should_i_eat_today.domain;


import lombok.*;

import javax.persistence.Embeddable;


@Getter
@Builder
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Attachment {

    private String path;

    private String name;

}
