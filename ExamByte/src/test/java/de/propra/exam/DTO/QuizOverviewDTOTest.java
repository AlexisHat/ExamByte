package de.propra.exam.DTO;

import de.propra.exam.domain.model.quiz.Quiz;
import de.propra.exam.domain.model.quizattempt.QuizAttempt;
import de.propra.exam.web.DTO.QuizOverviewDTO;
import de.propra.exam.web.DTO.QuizStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class QuizOverviewDTOTest {

    @Test
    @DisplayName("die factory methode gibt ein objekt zurück bei welchem der staus richtig berechnet ist also begonnen bei einem test der nch läuft")
    void test_01(){
        Quiz quiz = new Quiz();
        quiz.setStartTime(LocalDateTime.now().minusDays(1));
        quiz.setEndTime(LocalDateTime.now().plusDays(1));
        QuizOverviewDTO from = QuizOverviewDTO.from(quiz, new QuizAttempt(1L, 1L, 1L));

        assertThat(from.getStatus()).isEqualTo(QuizStatus.BEGONNEN);
    }
    @Test
    @DisplayName("die factory methode gibt ein objekt zurück bei welchem der staus richtig berechnet ist also beendet bei einem test der die endtime übergangen hat")
    void test_02(){
        Quiz quiz = new Quiz();
        quiz.setStartTime(LocalDateTime.now().minusDays(2));
        quiz.setEndTime(LocalDateTime.now().plusDays(-200));
        QuizOverviewDTO from = QuizOverviewDTO.from(quiz, new QuizAttempt(1L, 1L, 1L));

        assertThat(from.getStatus()).isEqualTo(QuizStatus.BEENDET);
    }

    @Test
    @DisplayName("status ist noch nicht begonnen wenn der start zeitpunk in der zukunft liegt")
    void test_03(){
        Quiz quiz = new Quiz();
        quiz.setQuizID(1L);
        quiz.setQuizName("Test");
        quiz.setStartTime(LocalDateTime.now().plusDays(2));
        quiz.setEndTime(LocalDateTime.now().plusDays(20));
        QuizOverviewDTO from = new QuizOverviewDTO(quiz, 1, 1);

        assertThat(from.getStatus()).isEqualTo(QuizStatus.NOCH_NICHT_BEGONNEN);
        assertThat(from.getQuizId()).isEqualTo(quiz.getQuizID());
        assertThat(from.getQuizName()).isEqualTo(quiz.getQuizName());
        assertThat(from.getMaxScore()).isEqualTo(1);
        assertThat(from.getScore()).isEqualTo(1);
    }
}