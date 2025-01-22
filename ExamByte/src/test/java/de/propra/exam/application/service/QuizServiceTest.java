package de.propra.exam.application.service;

import de.propra.exam.DTO.QuestionDTO;
import de.propra.exam.config.TestcontainersConfiguration;
import de.propra.exam.domain.exceptions.QuizNotFoundException;
import de.propra.exam.domain.model.quiz.Quiz;
import de.propra.exam.domain.service.QuizRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
class QuizServiceTest {

    @Autowired
    private QuizRepository quizRepository;

    @Test
    @DisplayName("create Quiz gibt ein neues Quiz Objekt zurück")
    void test_1() {
        QuizService quizService = new QuizService(quizRepository,null);
        Quiz quiz = quizService.createQuiz();
        assertThat(quiz).isNotNull();
        assertThat(quiz).isInstanceOf(Quiz.class);
    }

    @Test
    @DisplayName("createNewQuestionInQuiz fügt eine Question zu der Liste von Questions hinzu")
    void test_2() {
        QuestionDTO questionDTO = QuestionDTO.withDummy();
        QuizService quizService = new QuizService(quizRepository,null);
        Quiz quiz = quizService.createQuiz();

        quizService.createNewQuestionInQuiz(quiz, questionDTO);
        assertThat(quiz.getQuestions()).hasSize(1);
    }

    @Test
    @DisplayName("add quiz saved ein quiz richtg")
    void test_3() {
      QuizService quizService = new QuizService(quizRepository,null);
      Quiz quiz = new Quiz();

        Long l = quizService.addQuiz(quiz).getQuizID();
        boolean isIdPresent = quizRepository.findAll()
                .stream()
                .anyMatch(q -> q.getQuizID().equals(l)); // Überprüfen, ob die ID existiert

        assertThat(isIdPresent).isTrue();
    }

    @Test
    @DisplayName("find quiz by id wirft exception, falls id nicht vorhanden"
    )
    void test_4() {
        QuizService quizService = new QuizService(quizRepository,null);
        assertThatThrownBy(()-> quizService.findQuizById(90L)).isExactlyInstanceOf(QuizNotFoundException.class);
    }

    @Test
    @DisplayName("findquiz by id findet ein quiz richtiger weise")
    void test_5() {
        QuizService quizService = new QuizService(quizRepository,null);
        Quiz quiz = new Quiz();

        Long l = quizService.addQuiz(quiz).getQuizID();
        Quiz quizById = quizService.findQuizById(l);
        assertThat(quizById).isNotNull();
    }
}
