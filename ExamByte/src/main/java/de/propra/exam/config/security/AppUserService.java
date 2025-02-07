package de.propra.exam.config.security;

import de.propra.exam.config.RolesConfig;
import de.propra.exam.domain.model.users.Student;
import de.propra.exam.application.service.repository.StudentRepository;
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
    private final StudentRepository studentRepository;

    public AppUserService(RolesConfig rolesConfig, StudentRepository studentRepository) {
        this.rolesConfig = rolesConfig;
        this.studentRepository = studentRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User originalUser = defaultOAuth2UserService.loadUser(userRequest);

        Set<GrantedAuthority> authorities = new HashSet<>(originalUser.getAuthorities());
        String id = originalUser.getAttribute("id").toString();
        String name = originalUser.getAttribute("login");
        String email = originalUser.getAttribute("email");

        System.out.println(id+ name+ email);

        if (rolesConfig.getStudent().contains(id)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));

            studentRepository.findByGithubId(id).orElseGet(() -> {
                Student newStudent = new Student();
                newStudent.setGithubId(id);
                newStudent.setName(name);
                newStudent.setEmail(email);
                return studentRepository.save(newStudent);
            });
        }
        if (rolesConfig.getOrganisator().contains(id)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ORGANISATOR"));
        }
        System.out.println(authorities);
        return new DefaultOAuth2User(authorities, originalUser.getAttributes(), "id");
    }
}
