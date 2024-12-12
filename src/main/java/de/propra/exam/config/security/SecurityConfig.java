package de.propra.exam.config.security;

import de.propra.exam.config.RolesConfig;
import de.propra.exam.config.security.AppUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private RolesConfig rolesConfig;

    public SecurityConfig(RolesConfig rolesConfig) {
        this.rolesConfig = rolesConfig;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity chainBuilder) throws Exception {
        chainBuilder.authorizeHttpRequests(
                configurer -> configurer.requestMatchers("/", "/sec", "/css/*")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .oauth2Login(config -> config.userInfoEndpoint(
                        info -> info.userService(new AppUserService(rolesConfig))
                ));
        return chainBuilder.build();
    }



}
