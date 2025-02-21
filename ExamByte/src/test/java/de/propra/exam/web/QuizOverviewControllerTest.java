package de.propra.exam.web;

import de.propra.exam.application.service.QuizService;
import de.propra.exam.web.config.security.AppUserService;
import de.propra.exam.web.config.security.MethodSecurityConfig;
import de.propra.exam.web.config.security.SecurityConfig;
import de.propra.exam.web.organisator.QuizOverviewController;
import de.propra.exam.domain.model.quiz.Quiz;
import de.propra.exam.facAndBuild.WithMockOAuth2User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuizOverviewController.class)
@Import({SecurityConfig.class, MethodSecurityConfig.class})
class QuizOverviewControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    QuizService quizService;

    @MockBean
    AppUserService appUserService;

    @Test
    @WithMockOAuth2User(roles = "ORGANISATOR", id = 123456789)
    @DisplayName("anfragen von organisatoren sind ok")
    void test_01() throws Exception {
        mvc.perform(get("/showall-quizzes"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockOAuth2User(roles = "STUDENT", id = 123456789)
    @DisplayName("Studenten können nicht auf die seite zugreifen")
    void test_02() throws Exception {
        mvc.perform(get("/showall-quizzes"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockOAuth2User(roles = "ORGANISATOR", id = 123456789)
    @DisplayName("Der quizze welche vom Service gefunden werden sind im model enthalten")
    void test_3() throws Exception {
        Quiz quiz = new Quiz();
        when(quizService.findAllQuiz()).thenReturn(List.of(quiz));

        mvc.perform(get("/showall-quizzes"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allQuiz"))
                .andExpect(model().attribute("allQuiz", List.of(quiz)))
                .andExpect(view().name("quiz/showall-quizes"));

        verify(quizService).findAllQuiz();
    }

    @Test
    @WithMockOAuth2User(roles = "ORGANISATOR", id = 123456789)
    @DisplayName("Das Quiz mit der angegebenen ID wird im Model enthalten und die richtige View wird zurückgegeben")
    void test_04() throws Exception {

        long quizId = 69L;
        Quiz quiz = new Quiz();
        quiz.setQuizID(quizId);
        when(quizService.findQuizById(quizId)).thenReturn(quiz);

        mvc.perform(get("/quiz/{quizid}", quizId))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("quiz"))
                .andExpect(model().attribute("quiz", quiz))
                .andExpect(view().name("quiz/show-quiz"));

        verify(quizService).findQuizById(quizId);
    }


}