package today.what_should_i_eat_today.domain.member.application;

import today.what_should_i_eat_today.domain.member.entity.Member;

public interface MemberProfileService {

    Member updateNickNameProfileImage(Long id, String nickname, String profileImg);

}
