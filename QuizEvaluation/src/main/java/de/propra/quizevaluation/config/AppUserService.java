package de.propra.quizevaluation.config;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import de.propra.quizevaluation.domain.Korrektor;
import de.propra.quizevaluation.domain.service.KorrektorRepo;
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
public class AppUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>  {

    @Autowired
    RolesConfig rolesConfig;

    private final KorrektorRepo korrektorRepo;

    public AppUserService(KorrektorRepo korrektorRepo) {
        this.korrektorRepo = korrektorRepo;
    }

    private DefaultOAuth2UserService deuserService = new DefaultOAuth2UserService();



    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = deuserService.loadUser(userRequest);

        Set<GrantedAuthority> authorities = new HashSet<>(oAuth2User.getAuthorities());
        String id = oAuth2User.getAttribute("id").toString();
        String name = oAuth2User.getAttribute("login");


        if(rolesConfig.getKorrektor().contains(oAuth2User.getAttribute("id").toString())){
            authorities.add(new SimpleGrantedAuthority("ROLE_KORREKTOR"));

            korrektorRepo.findByGithubId(id).orElseGet(() -> {
                Korrektor korrektor = new Korrektor();
                korrektor.setGithubId(id);
                return korrektorRepo.save(korrektor);
            });

        } else if (rolesConfig.getOrganisator().contains(oAuth2User.getAttribute("id").toString())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ORGANISATOR"));
        }
        return new DefaultOAuth2User(authorities,oAuth2User.getAttributes(),"id");
    }
}
