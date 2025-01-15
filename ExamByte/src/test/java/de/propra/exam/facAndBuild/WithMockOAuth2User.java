package de.propra.exam.facAndBuild;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithOAuth2UserSecurityContextFactory.class)
public @interface WithMockOAuth2User {
    long id() default 666666L;


    String login() default "username";

    String[] roles() default {"USER"};

    String[] authorities() default {};

    String clientRegistrationId() default "github";
}
