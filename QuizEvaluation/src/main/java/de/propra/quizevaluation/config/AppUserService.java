package de.propra.quizevaluation.config;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class AppUserService implements OAuth2UserService {

    @Autowired
    RolesConfig rolesConfig;

    private DefaultOAuth2UserService deuserService = new DefaultOAuth2UserService();



    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = deuserService.loadUser(userRequest);

        Set<GrantedAuthority> authorities = new HashSet<>(oAuth2User.getAuthorities());

        if(rolesConfig.getKorrektor().contains(oAuth2User.getAttribute("id").toString())){
            authorities.add(new SimpleGrantedAuthority("ROLE_KORREKTOR"));
        } else if (rolesConfig.getOrganisator().contains(oAuth2User.getAttribute("id").toString())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ORGANISATOR"));
        }
        return new DefaultOAuth2User(authorities,oAuth2User.getAttributes(),"id");
    }
}
