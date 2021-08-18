package today.what_should_i_eat_today.domain.world_cup.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import today.what_should_i_eat_today.domain.world_cup.entity.Question;

/**
 * 쿼리 dsl 과 스프링 data JPA 연동을 위해서 필요한 인터페이스
 * */
public interface QuestionDslRepository {
    Page<Question> findByContent(String content, Pageable pageable);

    Page<Question> findByTagName(String tagName, Pageable pageable);
}
