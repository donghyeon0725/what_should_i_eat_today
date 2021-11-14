package today.what_should_i_eat_today.domain.qna.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.qna.entity.Qna;

import java.util.Optional;

public interface QnaRepository extends JpaRepository<Qna, Long>, QnaDslRepository {
    Page<Qna> findByMember(Member member, Pageable pageable);

    Qna findByIdAndMember(Long qnaId, Member member);

    @EntityGraph(attributePaths = {"qnaReview", "member"})
    Optional<Qna> findById(Long id);
}
