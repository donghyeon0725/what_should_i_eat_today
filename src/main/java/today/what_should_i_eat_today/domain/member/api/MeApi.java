package today.what_should_i_eat_today.domain.member.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import today.what_should_i_eat_today.domain.member.application.MemberFindService;
import today.what_should_i_eat_today.domain.member.application.MemberProfileService;
import today.what_should_i_eat_today.domain.member.dto.MemberProfileRequestDto;
import today.what_should_i_eat_today.domain.member.dto.MemberResponseDto;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.global.security.CurrentUser;
import today.what_should_i_eat_today.global.security.UserPrincipal;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MeApi {

    private final MemberFindService memberFindService;
    private final MemberProfileService memberProfileService;

    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> getMe(@CurrentUser UserPrincipal principal) {

        Member member = memberFindService.findById(principal.getId());

        return ResponseEntity.ok(toResponseDto(member));
    }

    @PutMapping("/me/profile")
    public ResponseEntity<MemberResponseDto> updateNicknameProfileImage(@CurrentUser UserPrincipal principal, @RequestBody @Valid MemberProfileRequestDto requestDto) {

        Member member = memberProfileService.updateNickNameProfileImage(principal.getId(), requestDto.getNickName(), requestDto.getProfileImage());

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
