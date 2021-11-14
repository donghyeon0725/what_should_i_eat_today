package today.what_should_i_eat_today.domain.world_cup.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import today.what_should_i_eat_today.domain.world_cup.entity.Package;

/**
 * 쿼리 dsl 과 스프링 data JPA 연동을 위해서 필요한 인터페이스
 * */
public interface PackageDslRepository {
    Page<Package> findBySubject(String subject, Pageable pageable);

    Page<Package> findByQuestionId(Long questionId, Pageable pageable);
}
