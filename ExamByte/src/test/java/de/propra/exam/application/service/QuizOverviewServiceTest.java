package de.propra.exam.application.service;

import de.propra.exam.web.DTO.QuizOverviewDTO;
import de.propra.exam.domain.model.quiz.Quiz;
import de.propra.exam.application.service.repository.AttemptRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QuizOverviewServiceTest {

    QuizService quizService = mock(QuizService.class);

    AttemptRepository attemptRepository = mock(AttemptRepository.class);

    @Test
    @DisplayName("die methode gibt die gleiche anzahl an overviews wie die Anzahl von quizes zurück")
    void test_01(){
        Quiz quiz = new Quiz();
        quiz.setQuizID(1L);
        quiz.setStartTime(LocalDateTime.now());
        quiz.setEndTime(LocalDateTime.now().plusDays(1));
        when(quizService.findAllQuiz()).thenReturn(List.of(quiz, quiz));
        QuizOverviewService quizOverviewService = new QuizOverviewService(quizService, attemptRepository);

        List<QuizOverviewDTO> studentQuizOverviews = quizOverviewService.getStudentQuizOverviews(12345L);

        assertThat(studentQuizOverviews.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("die overview hat die gleichen daten wie die quizes")
    void test_02(){
        Quiz quiz = new Quiz();
        quiz.setQuizID(1L);
        quiz.setStartTime(LocalDateTime.now());
        quiz.setEndTime(LocalDateTime.now().plusDays(1));
        quiz.setQuizName("foo");
        when(quizService.findAllQuiz()).thenReturn(List.of(quiz));
        QuizOverviewService quizOverviewService = new QuizOverviewService(quizService, attemptRepository);

        List<QuizOverviewDTO> studentQuizOverviews = quizOverviewService.getStudentQuizOverviews(12345L);
        QuizOverviewDTO first = studentQuizOverviews.getFirst();

        assertThat(first.getQuizId()).isEqualTo(quiz.getQuizID());
        assertThat(first.getQuizName()).isEqualTo(quiz.getQuizName());
    }
}
