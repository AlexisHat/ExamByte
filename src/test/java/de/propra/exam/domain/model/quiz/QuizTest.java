package de.propra.exam.domain.model.quiz;

import de.propra.exam.domain.model.quiz.question.Question;
import de.propra.exam.domain.model.quiz.question.TextQuestion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class QuizTest {

    @Test
    @DisplayName("Ein Quiz ist nicht aktiv vor der Bearbeitungszeit")
    void test_1() {
        Quiz quiz = new Quiz();
        quiz.startTime = LocalDateTime.of(2000, 12, 1, 1, 0);
        quiz.endTime = LocalDateTime.of(2000, 12, 1, 3, 0);

        LocalDateTime testTime = LocalDateTime.of(2000, 11, 1, 2, 0);
        assertThat(quiz.isActive(testTime)).isEqualTo(false);
    }

    @Test
    @DisplayName("Ein Quiz ist nicht aktiv nach der Bearbeitungszeit")
    void test_2() {
        Quiz quiz = new Quiz();
        quiz.startTime = LocalDateTime.of(2000, 12, 1, 1, 0);
        quiz.endTime = LocalDateTime.of(2000, 12, 1, 3, 0);

        LocalDateTime testTime = LocalDateTime.of(2001, 12, 1, 2, 0);
        assertThat(quiz.isActive(testTime)).isEqualTo(false);
    }

    @Test
    @DisplayName("Ein Quiz ist nur aktiv, während der Bearbeitungszeit")
    void test_3() {
        Quiz quiz = new Quiz();
        quiz.startTime = LocalDateTime.of(2000, 12, 1, 1, 0);
        quiz.endTime = LocalDateTime.of(2000, 12, 1, 3, 0);

        LocalDateTime testTime = LocalDateTime.of(2000, 12, 1, 2, 0);
        assertThat(quiz.isActive(testTime)).isEqualTo(true);
    }
    @Test
    @DisplayName("findQuestionById findet eine Frage mit korrekter ID")
    void test_4() {
        Quiz quiz = new Quiz();
        Question question = new TextQuestion();
        question.setQuestionId(1L);
        quiz.addQuestion(question);

        Question found = quiz.findQuestionById(1L);
        assertThat(found).isNotNull();
        assertThat(found.getQuestionId()).isEqualTo(1L);
    }
}