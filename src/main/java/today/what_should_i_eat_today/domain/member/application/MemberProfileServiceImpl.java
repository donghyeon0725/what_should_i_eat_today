package today.what_should_i_eat_today.domain.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.member.dao.MemberRepository;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.global.error.exception.ResourceDuplicatedException;
import today.what_should_i_eat_today.global.error.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberProfileServiceImpl implements MemberProfileService {

    private final MemberRepository memberRepository;

    @Override
    public Member updateNickNameProfileImage(Long id, String nickname, String profileImg) {

        boolean isDuplicated = memberRepository.existsByNickNameAndIdIsNot(nickname, id);

        if (isDuplicated)
            throw new ResourceDuplicatedException("Member", "NickName", nickname);

        Member member = memberRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Member", "Id", id));

        member.updateNickNameAndImage(nickname, profileImg);

        return memberRepository.save(member);
    }
}
