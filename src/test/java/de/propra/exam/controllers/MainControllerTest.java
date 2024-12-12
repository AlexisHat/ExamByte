package de.propra.exam.controllers;

import de.propra.exam.config.security.MethodSecurityConfig;
import de.propra.exam.config.RolesConfig;
import de.propra.exam.config.security.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MainController.class)
@Import({SecurityConfig.class, MethodSecurityConfig.class})
class MainControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    RolesConfig rolesConfig;
    @Test
    @DisplayName("Die Anwendung akzeptiert GET-req auf die Index seite")
    public void test_01() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk());
    }
}