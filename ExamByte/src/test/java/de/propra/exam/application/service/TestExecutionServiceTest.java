package de.propra.exam.application.service;

import de.propra.exam.config.TestcontainersConfiguration;
import de.propra.exam.domain.exceptions.QuestionNotFoundException;
import de.propra.exam.domain.model.quiz.Quiz;
import de.propra.exam.domain.model.quiz.question.MultipleChoiceQuestion;
import de.propra.exam.domain.model.quiz.question.Question;
import de.propra.exam.domain.model.quiz.question.TextQuestion;
import de.propra.exam.domain.model.quizattempt.answer.Answer;
import de.propra.exam.domain.model.quizattempt.answer.MultipleChoiceAnswer;
import de.propra.exam.domain.model.quizattempt.answer.TextAnswer;
import de.propra.exam.application.service.repository.AttemptRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest
@Sql(scripts = {"setup-test-data.sql"})
@Import(TestcontainersConfiguration.class)
public class TestExecutionServiceTest {

    @Autowired
    private TestExecutionService testExecutionService;

    @Autowired
    private QuizService quizService;

    @Autowired
    private AttemptRepository attemptRepository;

    @MockBean
    QuizValidationService quizValidationService;

    @BeforeEach
    void setUp() {
        quizValidationService = mock(QuizValidationService.class);
        doNothing().when(quizValidationService).validateQuizStartedAndNotEnded(any());
        when(quizValidationService.getCurrentTime()).thenReturn(LocalDateTime.now());
    }

    @Test
    @DisplayName("bei einer korrekten quizd id wird ein quiz zurück gegeben")
    void test_01() {
        Quiz quiz = testExecutionService.validateAndFetchQuiz(1L);

        assertThat(quiz).isNotNull();
        assertThat(quiz.getQuizName()).isEqualTo("Test Quiz");
    }

    @Test
    @DisplayName("Bei falscher QuestionId wird ein QuestionNotFoundException geworfen")
    void test_02() {
        assertThatThrownBy(() -> testExecutionService.validateAndFetchQuestion(new Quiz(),90L))
                .isInstanceOf(QuestionNotFoundException.class);
    }

    @Test
    @DisplayName("validateandfetch gibt eine quiz mit korrektem inhalt wieder")
    void test_03() {
        Quiz quiz = quizService.findQuizById(1L);

        Question question = testExecutionService.validateAndFetchQuestion(quiz, 1L);

        assertThat(question).isNotNull();
        assertThat(question.getTitle()).isEqualTo("Wer das ließt ist toll");
    }

    @Test
    @DisplayName("eine textantwort wird korrekt gespeichert und hat den richtigen type")
    void test_04() {
        Question question = new TextQuestion();
        String answerContent = "The answer is 4 because it is the sum of 2 and 2.";

        Answer answer = testExecutionService.createAnswer(question, answerContent);

        assertThat(answer).isInstanceOf(TextAnswer.class);
        assertThat(((TextAnswer) answer).getText()).isEqualTo(answerContent);
    }

    @Test
    @DisplayName("eine mutiple antwort wird korrekt erstellt und hat den richtigen type")
    void test_05() {
        Question question = new MultipleChoiceQuestion();
        String answerContent = "4";

        Answer answer = testExecutionService.createAnswer(question, answerContent);

        assertThat(answer).isInstanceOf(MultipleChoiceAnswer.class);
        assertThat(((MultipleChoiceAnswer) answer).getAusgewaehlteOptionen()).containsExactly("4");
    }

//    @Test
//    void test_08() {
//        Quiz quiz = quizService.findQuizById(1L);
//        QuizAttempt attempt = new QuizAttempt(1L, 1L, 1L);
//        Question question = quiz.findQuestionById(1L);
//        Answer answer = new MultipleChoiceAnswer(1L, List.of("4"), LocalDateTime.now());
//
//        testExecutionService.updateAndSaveQuizAttempt(attempt, 1L, answer, quiz);
//
//        QuizAttempt savedAttempt = attemptRepository.findById(attempt.getQuizAttemptId());
//
//        assertThat(savedAttempt.getAntworten()).hasSize(1);
//        assertThat(savedAttempt.getAntworten().get(1)).isEqualTo(answer);
//    }
}
