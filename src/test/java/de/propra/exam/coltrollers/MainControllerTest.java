package de.propra.exam.coltrollers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MainController.class)
class MainControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("Die Anwendung akzeptiert GET-req auf die Index seite")
    public void test_01() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("Die Anwendung akzeptiert GET-req auf die Login Page")
    public void test_02() throws Exception {
        mvc.perform(get("/login"))
                .andExpect(status().isOk());
    }
}