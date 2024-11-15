package de.propra.exam.service;

import de.propra.exam.config.RolesConfig;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AppUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {


    private final DefaultOAuth2UserService defaultOAuth2UserService = new DefaultOAuth2UserService();
    private final RolesConfig rolesConfig;

    public AppUserService(RolesConfig rolesConfig) {
        this.rolesConfig = rolesConfig;
    }


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User originalUser = defaultOAuth2UserService.loadUser(userRequest);
        Set<GrantedAuthority> authorities = new HashSet<>(originalUser.getAuthorities());
        String id = originalUser.getAttribute("id").toString();
        if (rolesConfig.getStudent().contains(id)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
        }
        if (rolesConfig.getKorrektor().contains(id)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_KORREKTOR"));
        }
        if (rolesConfig.getOrganisator().contains(id)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ORGANISATOR"));
        }
        System.out.println(authorities);
        return new DefaultOAuth2User(authorities, originalUser.getAttributes(), "id");
    }
}
