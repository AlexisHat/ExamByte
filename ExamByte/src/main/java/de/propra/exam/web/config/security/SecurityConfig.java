package de.propra.exam.web.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final AppUserService appUserService;

    public SecurityConfig(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity chainBuilder) throws Exception {
        chainBuilder.authorizeHttpRequests(
                configurer -> configurer.requestMatchers("/", "/sec", "/css/*","/events/attempt/submitted","/events/quiz/created")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .oauth2Login(config -> config.userInfoEndpoint(
                        info -> info.userService(appUserService)
                ));
        return chainBuilder.build();
    }
}
