package de.propra.exam.controllers;

import de.propra.exam.DTO.QuestionDTO;
import de.propra.exam.DTO.QuizOverviewDTO;
import de.propra.exam.application.service.QuizOverviewService;
import de.propra.exam.application.service.QuizService;
import de.propra.exam.application.service.TestExecutionService;
import de.propra.exam.config.RolesConfig;
import de.propra.exam.config.security.AppUserService;
import de.propra.exam.config.security.MethodSecurityConfig;
import de.propra.exam.config.security.SecurityConfig;
import de.propra.exam.domain.model.quiz.Quiz;
import de.propra.exam.domain.model.quiz.question.MultipleChoiceQuestion;
import de.propra.exam.facAndBuild.WithMockOAuth2User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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

    @MockBean
    QuizService quizService;

    @MockBean
    TestExecutionService testExecutionService;


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

    @Test
    @WithMockOAuth2User(roles = "STUDENT", id = 42069L)
    @DisplayName("wenn eine frage antwort seite aufgerufen wird, wird die methode zum finden der frage im quiz Service aufgerufen")
    void test_04() throws Exception {
    when(quizService.getQuestion(1L,1)).thenReturn(new MultipleChoiceQuestion());
    
        mockMvc.perform(get("/quiz/{id}/answer-question/{questionIndex}", 1L,1))
                .andExpect(status().isOk());

        verify(quizService).getQuestion(1L,1);
    }

    @Test
    @WithMockOAuth2User(roles = "STUDENT", id = 42069L)
    @DisplayName("das model quizID enhält die id aus dem path")
    void test_05() throws Exception {
        when(quizService.getQuestion(1L,1)).thenReturn(new MultipleChoiceQuestion());

        mockMvc.perform(get("/quiz/{id}/answer-question/{questionIndex}", 1L,1))
                .andExpect(status().isOk())
                .andExpect(model().attribute("quizID", 1L));
    }
    @Test
    @WithMockOAuth2User(roles = "STUDENT", id = 42069L)
    @DisplayName("das model question enhält das dto aus der question welche dem quiz mit dem richtigen index")
    void test_06() throws Exception {
        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion();
        long quizId = 1L;
        int questionIndex = 1;
        when(quizService.getQuestion(quizId, questionIndex)).thenReturn(multipleChoiceQuestion);

        QuestionDTO multipleChoiceQuestionDTO = QuestionDTO.ofQuestion(multipleChoiceQuestion, questionIndex);
        MvcResult mvcResult = mockMvc.perform(get("/quiz/{id}/answer-question/{questionIndex}", quizId, questionIndex))
                .andExpect(status().isOk()).andReturn();

        QuestionDTO question = (QuestionDTO) mvcResult.getModelAndView().getModel().get("question");

        assertThat(question.getType()).isEqualTo(multipleChoiceQuestionDTO.getType());
    }
}
