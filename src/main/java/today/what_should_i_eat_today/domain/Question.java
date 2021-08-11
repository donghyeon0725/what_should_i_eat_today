package today.what_should_i_eat_today.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private LocalDateTime createAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Tag tag;

}
