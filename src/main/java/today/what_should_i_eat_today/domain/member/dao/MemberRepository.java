package today.what_should_i_eat_today.domain.member.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import today.what_should_i_eat_today.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}