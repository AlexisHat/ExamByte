package de.propra.exam.controllers;

import de.propra.exam.DTO.QuizOverviewDTO;
import de.propra.exam.application.service.QuizOverviewService;
import de.propra.exam.config.RolesConfig;
import de.propra.exam.config.security.AppUserService;
import de.propra.exam.config.security.MethodSecurityConfig;
import de.propra.exam.config.security.SecurityConfig;
import de.propra.exam.domain.model.quiz.Quiz;
import de.propra.exam.facAndBuild.WithMockOAuth2User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentQuizController.class)
@Import({SecurityConfig.class, MethodSecurityConfig.class})
public class StudentQuizControllerTest {

    @MockBean
    AppUserService userService;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    QuizOverviewService overviewService;


    @Test
    @WithMockOAuth2User(roles = "STUDENT", id = 42069)
    @DisplayName("Ein Student kann die seite aufrufen")
    void test_01() throws Exception {
        mockMvc.perform(get("/student"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockOAuth2User(roles = "ORGANISATOR", id = 42069)
    @DisplayName("Ein Organisator kann nicht die seite private übersicht jedes studentens aufrufen")
    void test_02() throws Exception {
        mockMvc.perform(get("/student"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockOAuth2User(roles = "STUDENT", id = 42069L)
    @DisplayName("die Methode für alle testdaten für den studenten wird beim aufrufen der url aufgerufen")
    void test_03() throws Exception {


        mockMvc.perform(get("/student"))
                .andExpect(status().isOk());

        verify(overviewService).getStudentQuizOverviews(42069L);
    }

    @Test
    @WithMockOAuth2User(roles = "STUDENT", id = 42069L)
    @DisplayName("Die Methode lädt die Tests für den Studenten und speichert sie im Model")
    void test_getStudentTests() throws Exception {
        long studentId = 42069L;
        Quiz quiz = new Quiz();
        quiz.setQuizID(1L);
        quiz.setStartTime(LocalDateTime.now().minusHours(32));
        quiz.setEndTime(LocalDateTime.now().plusHours(31));
        List<QuizOverviewDTO> quizOverviews = List.of(
                new QuizOverviewDTO(quiz, 1.0,1.0),
                new QuizOverviewDTO(quiz, 1.0,1.0)
        );
        when(overviewService.getStudentQuizOverviews(studentId)).thenReturn(quizOverviews);

        mockMvc.perform(get("/student"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("quizs"))
                .andExpect(model().attribute("quizs", quizOverviews))
                .andExpect(view().name("student/landing"));
    }
}
