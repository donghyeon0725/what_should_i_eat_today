package today.what_should_i_eat_today.domain.member.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import today.what_should_i_eat_today.domain.member.dao.MemberRepository;
import today.what_should_i_eat_today.global.error.exception.ResourceDuplicatedException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class MemberProfileServiceImplTest {

    @InjectMocks
    private MemberProfileServiceImpl memberProfileService;

    @Mock
    private MemberRepository memberRepository;

    @Test
    @DisplayName("중복되는 닉네임이 있는 경우 중복오류여야 합니다")
    void duplicateNicknameMustBeADuplicateError() {

        String nickname = "martin";

        // given
        given(memberRepository.existsByNickNameAndIdIsNot(any(), anyLong())).willReturn(true);

        // when
        ResourceDuplicatedException exception = assertThrows(ResourceDuplicatedException.class, () -> {
            memberProfileService.updateNickNameProfileImage(1L, nickname, "http:localhost:8080");
        });

        // then
        assertThat(exception.getMessage()).contains("martin");
    }
}