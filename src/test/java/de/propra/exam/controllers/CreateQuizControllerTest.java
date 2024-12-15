package de.propra.exam.controllers;

import de.propra.exam.application.service.QuizService;
import de.propra.exam.config.security.MethodSecurityConfig;
import de.propra.exam.config.RolesConfig;
import de.propra.exam.config.security.SecurityConfig;
import de.propra.exam.facAndBuild.WithMockOAuth2User;
import de.propra.exam.domain.model.quizcore.MultipleChoiceQuestion;
import de.propra.exam.domain.model.quizcore.Question;
import de.propra.exam.domain.model.quizcore.Quiz;
import de.propra.exam.domain.model.quizcore.TextQuestion;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LOCAL_DATE_TIME;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.config.http.MatcherType.mvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@WebMvcTest(CreateQuizController.class)
@Import({SecurityConfig.class, MethodSecurityConfig.class})
public class CreateQuizControllerTest {

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


//
//    @Test
//    @DisplayName("Die add-question page wird korrekt angezeigt mit dem session attr Quiz")
//    @WithMockOAuth2User(roles = "STUDENT")
//    public void test_08() throws Exception {
//        Quiz quiz = new Quiz();
//
//        mockMvc.perform(get("/add-questions")
//                        .sessionAttr("quiz", quiz))
//                .andExpect(status().isOk())
//                .andExpect(view().name("quiz/add-questions"))
//                .andExpect(model().attributeExists("quiz"))
//                .andExpect(model().attribute("quiz", quiz));
//    }

//    @Test
//    @DisplayName("Eine Freitext Frage wird korrekt hinzugefügt")
//    @WithMockOAuth2User(roles = "STUDENT")
//    public void test_09() throws Exception {
//        Quiz quiz = new Quiz();
//
//        mockMvc.perform(post("/add-questions")
//                        .sessionAttr("quiz", quiz)
//                        .param("questionTitel", "foo")
//                        .param("questionText", "bar")
//                        .param("options","")
//                        .with(csrf()))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/add-questions"));
//
//        verify(quizService).createNewQuestionInQuiz(eq(quiz),any(),any(),any());
//    }
//    @Test
//    @DisplayName("Eine Multiple-Choice Frage wird korrekt hinzugefügt")
//    @WithMockOAuth2User(roles = "STUDENT")
//    public void test_10() throws Exception {
//
//        mockMvc.perform(post("/add-questions")
//                        .sessionAttr("quiz", quiz)
//                        .param("questionTitel", "foo")
//                        .param("questionText", "bar")
//                        .param("options","1","2","3")
//                        .with(csrf()))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/add-questions"));
//
//        verify(quizService).createNewQuestionInQuiz(eq(quiz),any(),any(),any());
//    }
//    @Test
//    @DisplayName("In einen test können gleichzeitig Mutiple und Freitext Aufgaben hinzugefügt werden")
//    @WithMockOAuth2User(roles = "STUDENT")
//    public void test_11() throws Exception {
//        Quiz quiz = new Quiz();
//
//        mockMvc.perform(post("/add-questions")
//                        .sessionAttr("quiz", quiz)
//                        .param("questionTitel", "foo")
//                        .param("questionText", "bar")
//                        .param("options","")
//                        .with(csrf()))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/add-questions"));
//
//        mockMvc.perform(post("/add-questions")
//                        .sessionAttr("quiz", quiz)
//                        .param("questionTitel", "foo")
//                        .param("questionText", "bar")
//                        .param("options","1","2","3")
//                        .with(csrf()))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/add-questions"));
//
//        verify(quizService,times(2)).createNewQuestionInQuiz(eq(quiz),any(),any(),any());
//    }
}

