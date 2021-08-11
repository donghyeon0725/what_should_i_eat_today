package today.what_should_i_eat_today.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Food extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Admin admin;

    private String name;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Boolean deleted;

    private LocalDateTime deletedAt;

}
