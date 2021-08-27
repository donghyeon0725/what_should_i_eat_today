package today.what_should_i_eat_today.domain.qna.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import today.what_should_i_eat_today.domain.qna.entity.Qna;
import today.what_should_i_eat_today.domain.qna.entity.QnaStatus;

public interface QnaDslRepository {
    Page<Qna> findByTitleAndStatus(String title, QnaStatus qnaStatus, Pageable pageable);
}
