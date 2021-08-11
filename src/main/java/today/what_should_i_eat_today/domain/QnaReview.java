package today.what_should_i_eat_today.domain;

import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class QnaReview extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "qnaReview", fetch = FetchType.LAZY)
    private Qna qna;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @Lob
    private String content;

    public void addedToQna(Qna qna) {
        this.qna = qna;
    }
}
