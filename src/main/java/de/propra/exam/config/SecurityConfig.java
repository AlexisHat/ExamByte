package de.propra.exam.config;

import de.propra.exam.service.AppUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private RolesConfig rolesConfig;

    @Bean
    public SecurityFilterChain configure(HttpSecurity chainBuilder) throws Exception {
        chainBuilder.authorizeHttpRequests(
                        configurer -> configurer.requestMatchers("/", "/css/*").permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2Login(config -> config.userInfoEndpoint(
                        info -> info.userService(new AppUserService(rolesConfig))
                ));
        return chainBuilder.build();
    }



}
