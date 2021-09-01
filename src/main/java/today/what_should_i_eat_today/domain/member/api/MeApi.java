package today.what_should_i_eat_today.domain.member.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import today.what_should_i_eat_today.domain.favorite.application.FavoriteService;
import today.what_should_i_eat_today.domain.likes.application.LikesService;
import today.what_should_i_eat_today.domain.member.application.MemberFindService;
import today.what_should_i_eat_today.domain.member.application.MemberProfileService;
import today.what_should_i_eat_today.domain.member.dto.MemberProfileRequestDto;
import today.what_should_i_eat_today.domain.member.dto.MemberResponseDto;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.post.application.PostService;
import today.what_should_i_eat_today.domain.post.entity.Post;
import today.what_should_i_eat_today.global.security.CurrentUser;
import today.what_should_i_eat_today.global.security.UserPrincipal;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MeApi {

    private final MemberFindService memberFindService;
    private final MemberProfileService memberProfileService;
    private final FavoriteService favoriteService;
    private final LikesService likesService;
    private final PostService postService;

    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> getMe(@CurrentUser UserPrincipal principal) {

        Member member = memberFindService.findById(principal.getId());

        return ResponseEntity.ok(toResponseDto(member));
    }

    @GetMapping("/me/mypage")
    public ResponseEntity<?> getMeMyPage(@CurrentUser UserPrincipal principal, @PageableDefault Pageable pageable) {
        // todo 2021.08.27 : mypage 는 처음 로딩 시 기본 10개만 가져오고 나머지는 각자 클릭이벤트를 통해 10개씩 가져오거나 더보기 페이지를 통해 가져와서 page request 를 받을 필요가 있는 지 의문이 듦
        Page<Post> myPosts = postService.getMyPosts(principal.getId(), pageable);
        List<Post> myLikesPosts = likesService.getMyLikesPosts(principal.getId(), pageable);
        List<Post> myFavoritesPosts = favoriteService.getMyFavoritePosts(principal.getId(), pageable);

        return ResponseEntity.ok("null");
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