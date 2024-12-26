package de.propra.exam.persistence.mapper;

import de.propra.exam.domain.model.quiz.Quiz;
import de.propra.exam.domain.model.quiz.question.MultipleChoiceQuestion;
import de.propra.exam.domain.model.quiz.question.Question;
import de.propra.exam.domain.model.quiz.question.TextQuestion;
import de.propra.exam.facAndBuild.QuizEntityBuilder;
import de.propra.exam.persistence.entity.quiz.QuestionEntity;
import de.propra.exam.persistence.entity.quiz.QuestionType;
import de.propra.exam.persistence.entity.quiz.QuizEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class QuizMapperTest {

    @Test
   @DisplayName("Wenn eine Question Entity mit dem Feld Type Freitext übergegebn bekommt wird ein Domain" +
           "Objekt der Klasse Freitext zurück gegeben")
    void test_01(){
        QuestionEntity questionEntity = QuestionEntity.ofText(1L, 1.0, "foo", "bar", "antwort");

        Question domain = QuizMapper.toDomain(questionEntity);

        assertThat(domain).isInstanceOf(TextQuestion.class);
    }

    @Test
    @DisplayName("Wenn eine Question Entity mit dem Feld Type Mutiple-Choice übergeben bekommt wird ein Domain" +
            "Objekt der Klasse Mutiple zurück gegeben")
    void test_02(){
        QuestionEntity questionEntity = QuestionEntity.ofMutiple(1L, 1.0, "foo", "bar","foo,bar","1");

        Question domain = QuizMapper.toDomain(questionEntity);

        assertThat(domain).isInstanceOf(MultipleChoiceQuestion.class);
    }

    @Test
    @DisplayName("Ein Quiz wird richtig von seine Entity aus gemapped")
    void test_03(){
        LocalDateTime now = LocalDateTime.now();
        QuizEntity quizEntity = new QuizEntityBuilder()
                .withId(1L)
                .withName("foo")
                .withStartTime(now)
                .withEndTime(now.plusDays(1))
                .withDefaultQuestions()
                .build();

        Quiz quiz = QuizMapper.toDomain(quizEntity);

        assertThat(quiz).isInstanceOf(Quiz.class);
    }

    @Test
    @DisplayName("Ein Quiz wird richtig von seine Entity aus gemapped und die Fragen sind vom richtigen type")
    void test_04(){
        LocalDateTime now = LocalDateTime.now();
        QuizEntity quizEntity = new QuizEntityBuilder()
                .withId(1L)
                .withName("foo")
                .withStartTime(now)
                .withEndTime(now.plusDays(1))
                .withDefaultQuestions()
                .build();

        Quiz quiz = QuizMapper.toDomain(quizEntity);

        Question fragea = quiz.getQuestions().getFirst();
        Question frageb = quiz.getQuestions().get(1);
        assertThat(fragea).isInstanceOf(MultipleChoiceQuestion.class);
        assertThat(frageb).isInstanceOf(TextQuestion.class);
    }

    @Test
    @DisplayName("Eine QuizEntity wird richtig von einem Domain Quiz Objekt aus gemapped")
    void test_05(){
        QuizEntity quizEntity = QuizMapper.toQuizEntity(new Quiz());

        assertThat(quizEntity).isInstanceOf(QuizEntity.class);
    }

    @Test
    @DisplayName("Eine QuizEntity wird richtig von einem Domain Quiz Objekt aus gemapped und hat die richtigen attribute")
    void test_06(){
        Quiz quiz = new Quiz();
        quiz.setQuizID(1L);
        quiz.setQuizName("foo");
        quiz.setStartTime(LocalDateTime.now());
        quiz.setEndTime(LocalDateTime.now().plusDays(1));
        quiz.addQuestion(new TextQuestion());

        QuizEntity quizEntity = QuizMapper.toQuizEntity(quiz);

        assertThat(quizEntity.getQuizId()).isEqualTo(quiz.getQuizID());
        assertThat(quizEntity.getQuizName()).isEqualTo(quiz.getQuizName());
        assertThat(quizEntity.getStartTime()).isEqualTo(quiz.getStartTime());
        assertThat(quizEntity.getEndTime()).isEqualTo(quiz.getEndTime());
        assertThat(quizEntity.getQuestions()).hasSize(1);
    }

    @Test
    @DisplayName("Beim mappen der Textfrage wird der type im entity objekt richtig gesetzt")
    void test_07(){
        Question question = new TextQuestion();
        QuestionEntity questionEntity = QuizMapper.toQuestionEntity(question);
        assertThat(questionEntity.getType()).isEqualTo(QuestionType.TEXT);
    }

    @Test
    @DisplayName("Beim mappen der Multiplefrage wird der type im entity objekt richtig gesetzt")
    void test_08(){
        Question question = new MultipleChoiceQuestion();
        QuestionEntity questionEntity = QuizMapper.toQuestionEntity(question);
        assertThat(questionEntity.getType()).isEqualTo(QuestionType.MULTIPLE_CHOICE);
    }
}