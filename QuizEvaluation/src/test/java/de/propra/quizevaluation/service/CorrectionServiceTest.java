package de.propra.quizevaluation.service;


import de.propra.quizevaluation.domain.Attempt.Answer;
import de.propra.quizevaluation.domain.quiz.QuestionEntity;
import de.propra.quizevaluation.domain.quiz.Quiz;
import de.propra.quizevaluation.persistence.repo.AnswerRepoImpl;
import de.propra.quizevaluation.persistence.repo.QuizRepoImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CorrectionServiceTest {
    QuizRepoImpl quizRepo = mock(QuizRepoImpl.class);
    AnswerRepoImpl answerRepo = mock(AnswerRepoImpl.class);
    CorrectionService correctionService = new CorrectionService(quizRepo, answerRepo);

    @Test
    @DisplayName("Die Methode ändert die punkte der fragen")
    void test_01(){
        List<Answer> answers = new ArrayList<>();
        Answer a = Answer.createDummyMutipleAnswer();

        a.setPoints(10069);

        answers.add(a);

        when(quizRepo.findQuestionById(a.getFrageId())).thenReturn(QuestionEntity.ofMutiple(1L,1.0,"null","null","1,2","1"));
        correctionService.autoCorrectMutiple(answers);
        assertThat(a.getPoints()).isEqualTo(0);
    }

    @Test
    @DisplayName("Wenn alle antworten richtig sind werden die maximalen punkte gesetzt")
    void test_02(){
        Double MAXPOINTS = 420.0;

        List<Answer> answers = new ArrayList<>();
        Answer a = Answer.createDummyMutipleAnswer();

        a.setPoints(10069);

        answers.add(a);

        when(quizRepo.findQuestionById(a.getFrageId())).thenReturn(QuestionEntity.ofMutiple(1L,MAXPOINTS,"null","null","1,2","1"));
        correctionService.autoCorrectMutiple(answers);
        assertThat(a.getPoints()).isEqualTo(MAXPOINTS);
    }
}