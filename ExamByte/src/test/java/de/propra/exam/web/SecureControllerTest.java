package de.propra.exam.web;

import de.propra.exam.web.config.security.AppUserService;
import de.propra.exam.web.config.security.MethodSecurityConfig;
import de.propra.exam.web.config.security.SecurityConfig;
import de.propra.exam.web.index.SecureController;
import de.propra.exam.facAndBuild.WithMockOAuth2User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(SecureController.class)
@Import({SecurityConfig.class, MethodSecurityConfig.class})
public class SecureControllerTest {

    @MockBean
    AppUserService appUserService;

    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("Die öffentliche /sec Seite ist für alle User erreichbar")
    void test_1() throws Exception {
        mvc.perform(get("/sec"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Die /studenten Seite ist nicht für Unangemeldete User erreichbar " +
            "und wird auf die Login von OAuth GitHub Page Redirected")
    void test_2() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/student"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        assertThat(mvcResult.getResponse().getRedirectedUrl())
                .contains("oauth2/authorization/github");
    }
    @Test
    @WithMockOAuth2User(roles = "STUDENT")
    @DisplayName("Die /student Seite ist für User mit der Rolle Student erreichbar")
    void test_3() throws Exception {
        mvc.perform(get("/studi"))
                .andExpect(status().isOk())
                .andReturn();
    }
    @Test
    @WithMockOAuth2User(roles = "KORREKTOR")
    @DisplayName("Die /student Seite ist für User mit der Rolle Korrektor erreichbar")
    void test_4() throws Exception {
        mvc.perform(get("/studi"))
                .andExpect(status().isOk())
                .andReturn();
    }
    @Test
    @WithMockOAuth2User(roles = "ORGANISATOR")
    @DisplayName("Die /student Seite ist für User mit der Rolle Organisator erreichbar")
    void test_5() throws Exception {
        mvc.perform(get("/studi"))
                .andExpect(status().isOk())
                .andReturn();
    }
    @Test
    @DisplayName("Die private /korrektor Seite ist nicht für Unangemeldete User erreichbar " +
            "und wird auf die Login von OAuth GitHub Page Redirected")
    void test_6() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/korrektor"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        assertThat(mvcResult.getResponse().getRedirectedUrl())
                .contains("oauth2/authorization/github");
    }
    @Test
    @DisplayName("Die private /korrektor Seite ist nicht für Studenten erreichbar")
    @WithMockOAuth2User(roles = "STUDENT")
    void test_7() throws Exception {
        mvc.perform(get("/korrektor"))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }
    @Test
    @WithMockOAuth2User(roles = "KORREKTOR")
    @DisplayName("Die /korrektor Seite ist für User mit der Rolle Korrektor erreichbar")
    void test_8() throws Exception {
        mvc.perform(get("/studi"))
                .andExpect(status().isOk())
                .andReturn();
    }
    @Test
    @WithMockOAuth2User(roles = "ORGANISATOR")
    @DisplayName("Die /korrektor Seite ist für User mit der Rolle Organisator erreichbar")
    void test_9() throws Exception {
        mvc.perform(get("/studi"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithMockOAuth2User(roles = "ORGANISATOR")
    @DisplayName("Die /organisator Seite ist für User mit der Rolle Organisator erreichbar")
    void test_10() throws Exception {
        mvc.perform(get("/organisator"))
                .andExpect(status().isOk())
                .andReturn();
    }
    @Test
    @DisplayName("Die private /korrektor Seite ist nicht für Unangemeldete User erreichbar " +
            "und wird auf die Login von OAuth GitHub Page Redirected")
    void test_11() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/organisator"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        assertThat(mvcResult.getResponse().getRedirectedUrl())
                .contains("oauth2/authorization/github");
    }
    @Test
    @DisplayName("Die private /organisator Seite ist nicht für Studenten erreichbar")
    @WithMockOAuth2User(roles = "STUDENT")
    void test_12() throws Exception {
        mvc.perform(get("/organisator"))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }
    @Test
    @DisplayName("Die private /organisator Seite ist nicht für Korrektoren erreichbar")
    @WithMockOAuth2User(roles = "KORREKTOR")
    void test_13() throws Exception {
        mvc.perform(get("/organisator"))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }
}