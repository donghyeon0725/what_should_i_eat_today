package today.what_should_i_eat_today.domain;

import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Food food;

    @Embedded
    private Attachment attachment;

    private String title;

    @Lob
    private String content;

}
