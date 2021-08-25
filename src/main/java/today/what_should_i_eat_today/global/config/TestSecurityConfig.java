package today.what_should_i_eat_today.global.config;

import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Profile("test")
@EnableWebSecurity
public class TestSecurityConfig extends WebSecurityConfigurerAdapter {

    // 테스트 케이스에 대해서 스프링 시큐리티 적용 해제
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**");
    }
}
