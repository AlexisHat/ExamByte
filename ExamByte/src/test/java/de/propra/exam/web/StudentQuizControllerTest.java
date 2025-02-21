package de.propra.exam.web;

import de.propra.exam.web.DTO.QuestionDTO;
import de.propra.exam.web.DTO.QuizOverviewDTO;
import de.propra.exam.application.service.*;
import de.propra.exam.web.config.security.AppUserService;
import de.propra.exam.web.config.security.MethodSecurityConfig;
import de.propra.exam.web.config.security.SecurityConfig;
import de.propra.exam.web.student.StudentQuizController;
import de.propra.exam.domain.model.quiz.Quiz;
import de.propra.exam.domain.model.quiz.question.MultipleChoiceQuestion;
import de.propra.exam.domain.model.quiz.question.TextQuestion;
import de.propra.exam.domain.model.quizattempt.QuizAttempt;
import de.propra.exam.domain.model.quizattempt.answer.MultipleChoiceAnswer;
import de.propra.exam.domain.model.quizattempt.answer.TextAnswer;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @MockBean
    StudentService studentService;

    @MockBean
    QuizValidationService quizValidationService;


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
        QuizAttempt quiattempt = new QuizAttempt(quizId,quizId,quizId);
        when(testExecutionService.findOrCreateQuizAttempt(any(),any())).thenReturn(quiattempt);

        QuestionDTO multipleChoiceQuestionDTO = QuestionDTO.ofQuestion(multipleChoiceQuestion, questionIndex);
        MvcResult mvcResult = mockMvc.perform(get("/quiz/{id}/answer-question/{questionIndex}", quizId, questionIndex))
                .andExpect(status().isOk()).andReturn();

        QuestionDTO question = (QuestionDTO) mvcResult.getModelAndView().getModel().get("question");

        assertThat(question.getType()).isEqualTo(multipleChoiceQuestionDTO.getType());
    }

    @Test
    @WithMockOAuth2User(roles = "STUDENT", id = 42069L)
    @DisplayName("das Modell erstellt eine Answer mit dem richtigen content also bei mutiple mit komma geteilt")
    void test_07() throws Exception {
        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion();
        long quizId = 1L;
        int questionIndex = 1;
        when(quizService.getQuestion(quizId, questionIndex)).thenReturn(multipleChoiceQuestion);
        QuizAttempt quiattempt = mock(QuizAttempt.class);
        when(testExecutionService.findOrCreateQuizAttempt(any(),any())).thenReturn(quiattempt);

        MultipleChoiceAnswer multipleChoiceAnswer = new MultipleChoiceAnswer(quizId,List.of("Option1", "Option2"),LocalDateTime.now());
        when(quiattempt.getAnswerByQuestionId(any())).thenReturn(Optional.of(multipleChoiceAnswer));


        MvcResult mvcResult = mockMvc.perform(get("/quiz/{id}/answer-question/{questionIndex}", quizId, questionIndex))
                .andExpect(status().isOk()).andReturn();

        String answer = (String) mvcResult.getModelAndView().getModel().get("existingAnswer");

        assertThat(answer).isEqualTo("Option1,Option2");
    }
    @Test
    @WithMockOAuth2User(roles = "STUDENT", id = 42069L)
    @DisplayName("das Modell erstellt eine Answer mit dem richtigen content also bei text den übergeben Text")
    void test_08() throws Exception {
        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion();
        long quizId = 1L;
        int questionIndex = 1;
        when(quizService.getQuestion(quizId, questionIndex)).thenReturn(multipleChoiceQuestion);
        QuizAttempt quiattempt = mock(QuizAttempt.class);
        when(testExecutionService.findOrCreateQuizAttempt(any(),any())).thenReturn(quiattempt);

        String text = "BlaBlaBlaWer Das Liest ist... toll";
        TextAnswer textAnswer = new TextAnswer(1L, text,LocalDateTime.now());
        when(quiattempt.getAnswerByQuestionId(any())).thenReturn(Optional.of(textAnswer));


        MvcResult mvcResult = mockMvc.perform(get("/quiz/{id}/answer-question/{questionIndex}", quizId, questionIndex))
                .andExpect(status().isOk()).andReturn();

        String answer = (String) mvcResult.getModelAndView().getModel().get("existingAnswer");

        assertThat(answer).isEqualTo(text);
    }

    @Test
    @WithMockOAuth2User(roles = "STUDENT", id = 42069L)
    @DisplayName("submitAnswerText leitet korrekt zur nächsten Frage weiter")
    void test_09() throws Exception {
        Long quizId = 1L;
        int currentQuestionIndex = 1;
        int totalQuestions = 3;
        String answer = "foo";

        TextQuestion question = new TextQuestion();
        question.setQuestionId(99L);
        when(quizService.getQuestion(quizId, currentQuestionIndex)).thenReturn(question);
        when(quizService.getQuestionListLength(quizId)).thenReturn(totalQuestions);

        mockMvc.perform(post("/quiz/submit/text/{id}/{questionIndex}", quizId, currentQuestionIndex)
                        .param("answer", answer)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/quiz/" + quizId + "/answer-question/" + (currentQuestionIndex + 1)));

        verify(quizService).getQuestionListLength(quizId);
    }
    @Test
    @WithMockOAuth2User(roles = "STUDENT", id = 42069L)
    @DisplayName("submitMutipleText leitet korrekt zur nächsten Frage weiter")
    void test_10() throws Exception {
        Long quizId = 1L;
        int currentQuestionIndex = 1;
        int totalQuestions = 3;

        MultipleChoiceQuestion question = new MultipleChoiceQuestion();
        question.setQuestionId(99L);
        when(quizService.getQuestion(quizId, currentQuestionIndex)).thenReturn(question);
        when(quizService.getQuestionListLength(quizId)).thenReturn(totalQuestions);

        mockMvc.perform(post("/quiz/submit/multiple/{id}/{questionIndex}", quizId, currentQuestionIndex)
                        .param("answer", "1","2","3")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/quiz/" + quizId + "/answer-question/" + (currentQuestionIndex + 1)));

        verify(quizService).getQuestion(quizId, currentQuestionIndex);
    }

    @Test
    @WithMockOAuth2User(roles = "STUDENT", id = 42069L)
    @DisplayName("submitMultiple leitet nicht weiter, wenn es die letzte Frage ist")
    void test_11() throws Exception {
        Long quizId = 1L;
        int currentQuestionIndex = 3;
        int totalQuestions = 3;

        MultipleChoiceQuestion question = new MultipleChoiceQuestion();
        question.setQuestionId(10L);

        when(quizService.getQuestion(quizId, currentQuestionIndex)).thenReturn(question);
        when(quizService.getQuestionListLength(quizId)).thenReturn(totalQuestions);

        mockMvc.perform(post("/quiz/submit/multiple/{id}/{questionIndex}", quizId, currentQuestionIndex)
                        .param("answer", "Option1", "Option2")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/quiz/" + quizId + "/answer-question/" + currentQuestionIndex));
    }
    @Test
    @WithMockOAuth2User(roles = "STUDENT", id = 42069L)
    @DisplayName("submitMultiple leitet nicht weiter, wenn es die letzte Frage ist")
    void test_12() throws Exception {
        Long quizId = 1L;
        int currentQuestionIndex = 3;
        int totalQuestions = 3;
        String answer = "foo";

        TextQuestion question = new TextQuestion();
        question.setQuestionId(10L);

        when(quizService.getQuestion(quizId, currentQuestionIndex)).thenReturn(question);
        when(quizService.getQuestionListLength(quizId)).thenReturn(totalQuestions);

        mockMvc.perform(post("/quiz/submit/multiple/{id}/{questionIndex}", quizId, currentQuestionIndex)
                        .param("answer", answer)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/quiz/" + quizId + "/answer-question/" + currentQuestionIndex));
    }

    @Test
    @WithMockOAuth2User(roles = "STUDENT", id = 42069L)
    @DisplayName("es werden die nötigen methoden im Service aufgerufen von submitText")
    void test_13() throws Exception {
        Long quizId = 1L;
        int currentQuestionIndex = 3;
        int totalQuestions = 3;
        String answer = "foo";

        TextQuestion question = new TextQuestion();
        question.setQuestionId(10L);

        when(quizService.getQuestion(quizId, currentQuestionIndex)).thenReturn(question);
        when(quizService.getQuestionListLength(quizId)).thenReturn(totalQuestions);

        mockMvc.perform(post("/quiz/submit/multiple/{id}/{questionIndex}", quizId, currentQuestionIndex)
                        .param("answer", answer)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/quiz/" + quizId + "/answer-question/" + currentQuestionIndex));

        verify(testExecutionService).submitAnswer(eq(quizId), any(Long.class), eq(10L), eq(answer));
        verify(quizService).getQuestion(quizId, currentQuestionIndex);
        verify(quizService).getQuestionListLength(quizId);
    }
}
