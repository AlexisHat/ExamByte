package de.propra.exam.application.service;

import de.propra.exam.DTO.QuestionDTO;
import de.propra.exam.domain.model.quiz.Quiz;
import de.propra.exam.domain.service.QuizRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class QuizServiceTest {

    @Test
    @DisplayName("create Quiz gibt ein neues Quiz Objekt zurück")
    void test_1() {
        QuizRepository quizRepository = mock(QuizRepository.class);
        QuizService quizService = new QuizService(quizRepository);
        Quiz quiz = quizService.createQuiz();
        assertThat(quiz).isNotNull();
    }


    @Test
    @DisplayName("createNewQuestionInQuiz fügt eine Question zu der Liste von Questions hinzu")
    void test_2() {
        QuizRepository quizRepository = mock(QuizRepository.class);
        QuestionDTO questionDTO = new QuestionDTO();
        QuizService quizService = new QuizService(quizRepository);
        Quiz quiz = quizService.createQuiz();

        quizService.createNewQuestionInQuiz(quiz, questionDTO);
        assertThat(quiz.getQuestions()).hasSize(1);
    }
}