package today.what_should_i_eat_today.global.security.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import today.what_should_i_eat_today.domain.member.dao.MemberRepository;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.model.AuthProvider;
import today.what_should_i_eat_today.global.error.exception.invalid.OAuth2AuthenticationProcessingException;
import today.what_should_i_eat_today.global.security.UserPrincipal;
import today.what_should_i_eat_today.global.security.oauth2.user.OAuth2UserInfo;
import today.what_should_i_eat_today.global.security.oauth2.user.OAuth2UserInfoFactory;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {

        final String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();

        final String accessToken = oAuth2UserRequest.getAccessToken().getTokenValue();
        System.err.println("accessToken-> " + accessToken);

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, oAuth2User.getAttributes());

        if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        Optional<Member> userOptional = memberRepository.findByEmail(oAuth2UserInfo.getEmail());
        Member member;

        if (userOptional.isPresent()) {
            member = userOptional.get();

            if (!member.getProvider().equals(AuthProvider.valueOf(registrationId))) {

                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        member.getProvider() + " account. Please use your " + member.getProvider() +
                        " account to login.");
            }
            member = updateExistingUser(member, oAuth2UserInfo);
        } else {
            member = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(member, oAuth2User.getAttributes());
    }

    private Member registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {

        Member user = Member.builder()
                .provider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))
                .providerId(oAuth2UserInfo.getId())
                .name(oAuth2UserInfo.getName())
                .email(oAuth2UserInfo.getEmail())
                .profileImg(oAuth2UserInfo.getImageUrl())
                .build();

        return memberRepository.save(user);
    }

    private Member updateExistingUser(Member existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.updateNameAndImage(oAuth2UserInfo.getName(), oAuth2UserInfo.getImageUrl());
        return memberRepository.save(existingUser);
    }

}
