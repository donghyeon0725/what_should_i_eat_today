package today.what_should_i_eat_today.global.security.oauth2.user;


import today.what_should_i_eat_today.domain.model.AuthProvider;
import today.what_should_i_eat_today.global.error.exception.OAuth2AuthenticationProcessingException;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        switch (AuthProvider.valueOf(registrationId.toLowerCase())) {
            case naver:
                return new NaverOAuth2UserInfo(attributes);
            case kakao:
                return new KakaoOAuth2UserInfo(attributes);
            case google:
                return new GoogleOAuth2UserInfo(attributes);
            case facebook:
                return new FacebookOAuth2UserInfo(attributes);
            case github:
                return new GithubOAuth2UserInfo(attributes);
            default:
                throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
