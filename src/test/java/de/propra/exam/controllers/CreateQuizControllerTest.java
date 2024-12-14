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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
//@WebMvcTest(CreateQuizController.class)
//@Import({SecurityConfig.class, MethodSecurityConfig.class})
//public class CreateQuizControllerTest {

//    @Autowired
//    MockMvc mockMvc;
//
//    @MockBean
//    RolesConfig rolesConfig;
//
//   @MockBean
//   QuizService quizService;
//
//   private Quiz quiz;
//    @BeforeEach
//    void setup() {
//        quiz = new Quiz();
//        when(quizService.createQuiz()).thenReturn(quiz);
//    }
//
//
//    @Test
//    @DisplayName("Eine freitext Frage kann mit Titel und beschreibung erstellt werden")
//    public void test_01(){
//        Question question = new TextQuestion();
//        question.setTitel("foo");
//        question.setAufgabenstellung("bar");
//
//        assertThat(question.getTitel()).isEqualTo("foo");
//        assertThat(question.getAufgabenstellung()).isEqualTo("bar");
//    }
////    @Test
////    @DisplayName("Eine Multiple Choice Frage kann mit Titel und beschreibung erstellt werden und hat die richtige Anzahl an Options")
////    public void test_02(){
////        MultipleChoiceQuestion question = new MultipleChoiceQuestion(List.of("foo","bar"));
////        question.setTitel("foo");
////        question.setAufgabenstellung("bar");
////
////        assertThat(question.getTitel()).isEqualTo("foo");
////        assertThat(question.getAufgabenstellung()).isEqualTo("bar");
////        assertThat(question.getOptions().size()).isEqualTo(2);
////    }
//
//    @Test
//    @DisplayName("Ein leerer Quiz kann erstellt werden")
//    public void test_03(){
//        Quiz quiz = new Quiz();
//        assertThat(quiz.getFragen()).isEmpty();
//    }
//
//    @Test
//    @DisplayName("Ein Quiz kann sowohl mit Mutiple Choice, als auch mit Freitext erstellt werden")
//    public void test_04(){
//        Quiz quiz = new Quiz();
//        quiz.addFrage(new MultipleChoiceQuestion(List.of()));
//        quiz.addFrage(new TextQuestion());
//        quiz.addFrage(new TextQuestion());
//        assertThat(quiz.getFragen().size()).isEqualTo(3);
//    }
//    @Test
//    @DisplayName("Ein Quiz kann mit Name erstellt werden")
//    public void test_05(){
//        Quiz quiz = new Quiz();
//        quiz.setQuizName("foo");
//        assertThat(quiz.getQuizName()).isEqualTo("foo");
//    }
//
//    @Test
//    @DisplayName("die create Quiz Page wird Korrekt angezeigt")
//    @WithMockOAuth2User(roles = "STUDENT")
//    public void test_06() throws Exception {
//        mockMvc.perform(get("/create-test"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("quiz/test-erstellen"))
//                .andExpect(model().attributeExists("quiz"));
//    }
//
//    @Test
//    @DisplayName("Bei dem ausführen der Post methode von create-test wird man auf die add-question Seite weitergeleitet")
//    @WithMockOAuth2User(roles = "STUDENT")
//    public void test_07() throws Exception {
//        mockMvc.perform(post("/create-test")
//                        .param("quizName", "foo")
//                        .with(csrf()))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/add-questions"));
//    }
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
//}

