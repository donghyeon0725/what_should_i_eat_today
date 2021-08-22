package today.what_should_i_eat_today.domain.member.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import today.what_should_i_eat_today.domain.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    boolean existsByNickNameAndIdIsNot(String nickName, Long id);
}