package de.propra.exam.domain.model.quizattempt;


import de.propra.exam.domain.exceptions.QuizAlreadyEndedException;
import de.propra.exam.domain.exceptions.QuizNotStartedException;
import de.propra.exam.domain.model.quiz.Quiz;
import de.propra.exam.domain.model.quiz.question.TextQuestion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class QuizAttemptTest {

    @Test
    @DisplayName("wenn noch keine antwort hinzugefügt wurde ist die antworten Liste leer")
    void test_01(){
        QuizAttempt quizAttempt = new QuizAttempt(1L, 1L, 123L);


        List<Antwort> antworten = quizAttempt.getAntworten();

        assertThat(antworten.size()).isZero();
    }
    @Test
    @DisplayName("wenn noch keine antwort hinzugefügt wurde kann eine Antwort hinzugefügt werden")
    void test_02(){
        LocalDateTime zeit = LocalDateTime.now();
        Quiz quiz = mock(Quiz.class);
        when(quiz.isGestartet(zeit)).thenReturn(true);
        when(quiz.isBeendet(zeit)).thenReturn(false);
        when(quiz.findeFrage(1L)).thenReturn(new TextQuestion());
        QuizAttempt quizAttempt = new QuizAttempt(1L, 1L, 123L);
        quizAttempt.addOrUpdateAnswer(1L,new FreitextAntwort(1L,"Bla", zeit),quiz,zeit);
        List<Antwort> antworten = quizAttempt.getAntworten();

        assertThat(antworten.size()).isOne();
    }
    @Test
    @DisplayName("wenn der test noch nicht gestartet ist failed die Methode mit exception")
    void test_03(){
        LocalDateTime zeit = LocalDateTime.now();
        Quiz quiz = mock(Quiz.class);
        when(quiz.isGestartet(zeit)).thenReturn(false);
        QuizAttempt quizAttempt = new QuizAttempt(1L, 1L, 123L);
        FreitextAntwort bla = new FreitextAntwort(1L, "Bla", zeit);

        assertThatThrownBy(() -> quizAttempt.addOrUpdateAnswer(1L, bla, quiz, zeit)).isInstanceOf(QuizNotStartedException.class)
                .hasMessageContaining("Das Quiz hat noch nicht begonnen.");
    }
    @Test
    @DisplayName("wenn der test schon beendet ist, wird exception geworfen")
    void test_04() {
        LocalDateTime zeit = LocalDateTime.now();
        Quiz quiz = mock(Quiz.class);
        when(quiz.isBeendet(any())).thenReturn(true);
        when(quiz.isGestartet(zeit)).thenReturn(true);
        QuizAttempt quizAttempt = new QuizAttempt(1L, 1L, 123L);
        FreitextAntwort bla = new FreitextAntwort(1L, "Bla", zeit);


        assertThatThrownBy(() -> quizAttempt.addOrUpdateAnswer(1L, bla, quiz, zeit)).isInstanceOf(QuizAlreadyEndedException.class)
                .hasMessageContaining("Das Quiz ist beendet");
    }

    @Test
    @DisplayName("ein schon abgeschlossener Test kann auch nicht mehr geändert werden")
    void test_05() {
        QuizAttempt quizAttempt = new QuizAttempt(1L, 1L, 123L);
        quizAttempt.abgeschlossen = true;
        FreitextAntwort bla = new FreitextAntwort(1L, "Bla", LocalDateTime.now());

        assertThatThrownBy(() -> quizAttempt.addOrUpdateAnswer(1L, bla, new Quiz(), LocalDateTime.now())).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Versuch ist bereits abgeschlossen.");
    }

    @Test
    @DisplayName("wenn eine frageID nicht im Quiz vorhanden ist wirft die Methode eine exception")
    void test_06(){
        LocalDateTime zeit = LocalDateTime.now();
        Quiz quiz = mock(Quiz.class);
        when(quiz.findeFrage(1L)).thenReturn(null);
        when(quiz.isGestartet(zeit)).thenReturn(true);
        when(quiz.isBeendet(zeit)).thenReturn(false);
        QuizAttempt quizAttempt = new QuizAttempt(1L, 1L, 123L);
        FreitextAntwort antwort = new FreitextAntwort(1L, "Bla", LocalDateTime.now());


        assertThatThrownBy(() -> quizAttempt.addOrUpdateAnswer(1L, antwort, quiz, zeit)).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Frage gehört nicht zu diesem Quiz.");
    }

//    @Test
//    @DisplayName("wenn eine frage schon im attempt enthalten ist wird die aktualisiere methode aufgerufen")
//    void test_07(){
//        QuizAttempt quizAttempt = mock(QuizAttempt.class);
//        FreitextAntwort antwort = new FreitextAntwort(1L, "Bla", LocalDateTime.now());
//        LocalDateTime zeit = LocalDateTime.now();
//        Quiz quiz = mock(Quiz.class);
//        when(quiz.findeFrage(1L)).thenReturn(new TextQuestion());
//        when(quiz.isGestartet(zeit)).thenReturn(true);
//        when(quiz.isBeendet(zeit)).thenReturn(false);
//
//        quizAttempt.addOrUpdateAnswer(1L, antwort, quiz, zeit);
//
//        verify(quizAttempt).aktualisiereAntwort(eq(antwort),eq(antwort),zeit);
//    }

}