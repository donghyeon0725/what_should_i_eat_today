package today.what_should_i_eat_today.domain.member.application;

import today.what_should_i_eat_today.domain.member.entity.Member;

public interface MemberFindService {

    Member findById(Long id);

}
