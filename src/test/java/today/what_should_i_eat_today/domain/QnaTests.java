package today.what_should_i_eat_today.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.qna.entity.Qna;
import today.what_should_i_eat_today.domain.qna.entity.QnaReview;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class QnaTests {

    @Autowired
    private EntityManager em;

    @Test
    void test() {
        Qna qna = Qna.builder()
                .content("qna content")
                .build();

        em.persist(qna);

        QnaReview qnaReview = QnaReview.builder()
                .content("qna review content")
                .build();

        qna.answerQnaReview(qnaReview);

        flushAndClear();

        Qna findQna = em.find(Qna.class, qna.getId());

        assertEquals("qna review content", findQna.getQnaReview().getContent());
    }

    private void flushAndClear() {
        em.flush();
        em.clear();
    }

}
