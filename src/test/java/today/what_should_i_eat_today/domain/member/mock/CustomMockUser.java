package today.what_should_i_eat_today.domain.member.mock;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface CustomMockUser {

     long id() default 1L;

     String email() default "";

     String[] roles() default {"USER"};


}
