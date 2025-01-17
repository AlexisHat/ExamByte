package de.propra.quizevaluation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity chainBuilder) throws Exception {
        return chainBuilder.authorizeHttpRequests(
                        configurer -> configurer.requestMatchers("/css/*").permitAll()
                                .anyRequest().authenticated()
                ).oauth2Login(config -> config.userInfoEndpoint(
                        info ->info.userService(new AppUserService())
                ))
                .build();
    }
}
