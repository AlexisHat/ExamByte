package de.propra.exam.persistence.mapper;

import de.propra.exam.domain.model.quiz.Quiz;
import de.propra.exam.domain.model.quiz.question.TextQuestion;
import de.propra.exam.domain.model.quizattempt.QuizAttempt;
import de.propra.exam.domain.model.quizattempt.answer.Answer;
import de.propra.exam.domain.model.quizattempt.answer.MultipleChoiceAnswer;
import de.propra.exam.domain.model.quizattempt.answer.TextAnswer;
import de.propra.exam.persistence.entity.quiz.QuestionType;
import de.propra.exam.persistence.entity.quizattempt.AnswerEntity;
import de.propra.exam.persistence.entity.quizattempt.QuizAttemptEntity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AttemptMapperTest {

    private Quiz quiz;
    LocalDateTime now;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();
        quiz = mock(Quiz.class);
        when(quiz.isBeendet(now)).thenReturn(false);
        when(quiz.isGestartet(now)).thenReturn(true);
        when(quiz.findQuestionById(any())).thenReturn(new TextQuestion());
    }

    @Test
    @DisplayName("Wenn eine Answer Entity mit dem Feld Type TEXT übergeben wird, wird ein Domain-Objekt der Klasse TextAnswer zurückgegeben")
    void test_01() {
        AnswerEntity answerEntity = AnswerEntity.ofText(1L, 1L, "Antwort", LocalDateTime.now());

        Answer domain = QuizAttemptMapper.toDomain(answerEntity);

        assertThat(domain).isInstanceOf(TextAnswer.class);
    }

    @Test
    @DisplayName("Wenn eine Answer Entity mit dem Feld Type MULTIPLE_CHOICE übergeben wird, wird ein Domain-Objekt der Klasse MultipleChoiceAnswer zurückgegeben")
    void test_02() {
        AnswerEntity answerEntity = AnswerEntity.ofMultipleChoice(1L, 1L, List.of("Option1", "Option2"), LocalDateTime.now());

        Answer domain = QuizAttemptMapper.toDomain(answerEntity);

        assertThat(domain).isInstanceOf(MultipleChoiceAnswer.class);
    }

    @Test
    @DisplayName("Ein QuizAttempt wird korrekt von seiner Entity gemappt")
    void test_03() {
        AnswerEntity answerEntity = AnswerEntity.ofText(1L, 1L, "Antwort", now);
        QuizAttemptEntity attemptEntity = new QuizAttemptEntity(1L, 1L, 1L, List.of(answerEntity));

        QuizAttempt quizAttempt = QuizAttemptMapper.toDomain(attemptEntity);

        assertThat(quizAttempt).isInstanceOf(QuizAttempt.class);
    }

    @Test
    @DisplayName("Ein QuizAttempt wird korrekt von seiner Entity gemappt und die Antworten sind vom richtigen Typ")
    void test_04() {
        AnswerEntity textAnswerEntity = AnswerEntity.ofText(1L, 1L, "Antwort", now);
        AnswerEntity multipleChoiceAnswerEntity = AnswerEntity.ofMultipleChoice(2L, 1L, List.of("Option1", "Option2"), now);
        QuizAttemptEntity attemptEntity = new QuizAttemptEntity(1L, 1L, 1L, List.of(textAnswerEntity, multipleChoiceAnswerEntity));

        QuizAttempt quizAttempt = QuizAttemptMapper.toDomain(attemptEntity);

        Answer answer1 = quizAttempt.getAntworten().get(0);
        Answer answer2 = quizAttempt.getAntworten().get(1);

        assertThat(answer1).isInstanceOf(TextAnswer.class);
        assertThat(answer2).isInstanceOf(MultipleChoiceAnswer.class);
    }

    @Test
    @DisplayName("Eine QuizAttemptEntity wird korrekt von einem Domain-QuizAttempt-Objekt gemappt")
    void test_05() {
        QuizAttempt quizAttempt = new QuizAttempt(1L, 1L, 1L);
        quizAttempt.addOrUpdateAnswer(1L, new TextAnswer(1L, 1L, now , "Antwort"), quiz, now);

        QuizAttemptEntity attemptEntity = QuizAttemptMapper.toAttemptEntity(quizAttempt);

        assertThat(attemptEntity).isInstanceOf(QuizAttemptEntity.class);
    }

    @Test
    @DisplayName("Eine QuizAttemptEntity wird korrekt von einem Domain-QuizAttempt-Objekt gemappt und hat die richtigen Attribute")
    void test_06() {
        QuizAttempt quizAttempt = new QuizAttempt(1L, 1L, 1L);
        quizAttempt.addOrUpdateAnswer(1L, new TextAnswer(1L, 1L, now, "Antwort"), quiz, now);

        QuizAttemptEntity attemptEntity = QuizAttemptMapper.toAttemptEntity(quizAttempt);

        assertThat(attemptEntity.quizAttemptId()).isEqualTo(quizAttempt.getQuizAttemptId());
        assertThat(attemptEntity.quizId()).isEqualTo(quizAttempt.getQuizId());
        assertThat(attemptEntity.studentId()).isEqualTo(quizAttempt.getStudentId());
        assertThat(attemptEntity.answers()).hasSize(1);
    }

    @Test
    @DisplayName("Beim Mapping der Textantwort wird der Typ im Entity-Objekt richtig gesetzt")
    void test_07() {
        Answer answer = new TextAnswer(1L, 1L, now , "Antwort");
        AnswerEntity answerEntity = QuizAttemptMapper.toAnswerEntity(answer);

        assertThat(answerEntity.getType()).isEqualTo(QuestionType.TEXT);
    }

    @Test
    @DisplayName("Beim Mapping der Multiple-Choice-Antwort wird der Typ im Entity-Objekt richtig gesetzt")
    void test_08() {
        Answer answer = new MultipleChoiceAnswer(1L, 1L, LocalDateTime.now(), List.of("Option1", "Option2"));
        AnswerEntity answerEntity = QuizAttemptMapper.toAnswerEntity(answer);

        assertThat(answerEntity.getType()).isEqualTo(QuestionType.MULTIPLE_CHOICE);
    }
}
