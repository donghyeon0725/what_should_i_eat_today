package today.what_should_i_eat_today.domain;

import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Activity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    private Report report;

    @Enumerated(EnumType.STRING)
    private ActivityType type;

    private String details;

}
