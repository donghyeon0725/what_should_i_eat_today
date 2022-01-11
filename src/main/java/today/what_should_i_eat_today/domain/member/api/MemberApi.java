package today.what_should_i_eat_today.domain.member.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import today.what_should_i_eat_today.domain.member.application.MemberFindService;
import today.what_should_i_eat_today.domain.member.dto.MemberResponseDto;
import today.what_should_i_eat_today.domain.member.entity.Member;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberApi {

    private final MemberFindService memberFindService;

    /**
     * 멤버 단 건 조회
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/members/{id}")
    public ResponseEntity<MemberResponseDto> findMember(@PathVariable Long id) {

        Member member = memberFindService.findById(id);

        return ResponseEntity.ok(toResponseDto(member));
    }

    private MemberResponseDto toResponseDto(Member member) {
        return MemberResponseDto.builder()
                .id(member.getId())
                .profileImg(member.getProfileImg())
                .email(member.getEmail())
                .name(member.getName())
                .nickName(member.getNickName())
                .providerId(member.getProviderId())
                .build();
    }
}
