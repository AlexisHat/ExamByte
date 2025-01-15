package de.propra.exam.controllers;

import de.propra.exam.application.service.QuizService;
import de.propra.exam.config.security.AppUserService;
import de.propra.exam.config.security.MethodSecurityConfig;
import de.propra.exam.config.RolesConfig;
import de.propra.exam.config.security.SecurityConfig;
import de.propra.exam.controllers.organisator.CreateQuizController;
import de.propra.exam.facAndBuild.WithMockOAuth2User;
import de.propra.exam.domain.model.quiz.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;



@WebMvcTest(CreateQuizController.class)
@Import({SecurityConfig.class, MethodSecurityConfig.class})
public class CreateQuizControllerTest {

    @MockBean
    AppUserService appUserService;

    @Autowired
    MockMvc mvc;

    @MockBean
    RolesConfig rolesConfig;

    @MockBean
    QuizService quizService;

    private Quiz quiz;

    @BeforeEach
    void setup() {
        quiz = new Quiz();
        when(quizService.createQuiz()).thenReturn(quiz);
    }

    @Test
    @WithMockOAuth2User(roles = "STUDENT")
    @DisplayName("Wir können die URL create-test als Student nicht aufrufen")
    void test_1() throws Exception {
        mvc.perform(get("/create-test"))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    @WithMockOAuth2User(roles = "KORREKTOR")
    @DisplayName("Wir können die URL create-test als Korrektor nicht aufrufen")
    void test_2() throws Exception {
        mvc.perform(get("/create-test"))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    @WithMockOAuth2User(roles = "ORGANISATOR")
    @DisplayName("Wir können die URL create-test als Organisator aufrufen")
    void test_3() throws Exception {
        mvc.perform(get("/create-test"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("quiz/create-test"))
                .andReturn();
    }

    @Test
    @WithMockOAuth2User(roles = "STUDENT")
    @DisplayName("Wir können an die URL create-test als Student keine Post Request abgeben")
    void test_4() throws Exception {
        mvc.perform(post("/create-test"))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    @WithMockOAuth2User(roles = "KORREKTOR")
    @DisplayName("Wir können an die URL create-test als Korrektor keine Post Request abgeben")
    void test_5() throws Exception {
        mvc.perform(post("/create-test"))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    @WithMockOAuth2User(roles = "STUDENT")
    @DisplayName("Wir können die URL add-questions als Student nicht aufrufen")
    void test_6() throws Exception {
        mvc.perform(get("/add-questions"))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    @WithMockOAuth2User(roles = "KORREKTOR")
    @DisplayName("Wir können die URL add-questions als Korrektor nicht aufrufen")
    void test_7() throws Exception {
        mvc.perform(get("/add-questions"))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    @WithMockOAuth2User(roles = "ORGANISATOR")
    @DisplayName("Wir können die URL add-questions als Organisator aufrufen")
    void test_8() throws Exception {
        mvc.perform(get("/add-questions"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("quiz/add-questions"))
                .andReturn();
    }

    @Test
    @WithMockOAuth2User(roles = "STUDENT")
    @DisplayName("Wir können an die URL add-questions als Student keine Post Request abgeben")
    void test_9() throws Exception {
        mvc.perform(post("/add-questions"))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    @WithMockOAuth2User(roles = "KORREKTOR")
    @DisplayName("Wir können an die URL add-questions als Korrektor keine Post Request abgeben")
    void test_10() throws Exception {
        mvc.perform(post("/add-questions"))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    @DisplayName("Bei dem ausführen der Post methode von create-test wird man auf die add-question Seite weitergeleitet")
    @WithMockOAuth2User(roles = "ORGANISATOR")
    public void test_11() throws Exception {
        mvc.perform(post("/create-test")
                        .param("quizName", "foo")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/add-questions"));
    }

    @Test
    @WithMockOAuth2User(roles = "ORGANISATOR")
    @DisplayName("Nach dem Ersten Aufrufen von create Test wird ein Quiz erstellt")
    void test_12() throws Exception {
        mvc.perform(get("/create-test"))
                .andExpect(status().is2xxSuccessful());
        verify(quizService, times(1)).createQuiz();
    }

    @Test
    @DisplayName("Eine Post request an /add-questions löst aus, dass eine Frage zum Quiz hinzugefügt wird")
    @WithMockOAuth2User(roles = "ORGANISATOR")
    public void test_13() throws Exception {
        Quiz quiz = new Quiz();

        mvc.perform(post("/add-questions")
                        .sessionAttr("quiz", quiz)
                        .with(csrf()))
                .andExpect(redirectedUrl("/add-questions"));

        verify(quizService).createNewQuestionInQuiz(eq(quiz), any());
    }

    @Test
    @WithMockOAuth2User(roles = "ORGANISATOR")
    @DisplayName("Wenn wir mit post finalize-test aufrufen, dann wird das Quiz gespeichert")
    void test_14() throws Exception {
        mvc.perform(post("/finalize-quiz")
                        .sessionAttr("quiz", quiz)
                        .with(csrf()))
                .andExpect(redirectedUrl("/success"));

        verify(quizService).addQuiz(quiz);
    }

}

