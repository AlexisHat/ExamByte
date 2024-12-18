package de.propra.exam.application.service;

import de.propra.exam.domain.exceptions.QuizAlreadyEndedException;
import de.propra.exam.domain.exceptions.QuizNotStartedException;
import de.propra.exam.domain.model.quiz.*;
import de.propra.exam.domain.model.quiz.question.Question;
import de.propra.exam.domain.model.quizattempt.answer.Answer;
import de.propra.exam.domain.model.quizattempt.QuizAttempt;
import de.propra.exam.domain.model.quizattempt.answer.TextAnswer;
import de.propra.exam.domain.service.AttemptRepository;
import de.propra.exam.domain.service.Clock;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TestExecutionService {
    private final QuizService quizService;
    private final AttemptRepository attemptRepository;
    private final Clock clock;

    public TestExecutionService(QuizService quizService, AttemptRepository attemptRepository, Clock clock) {
        this.quizService = quizService;
        this.attemptRepository = attemptRepository;
        this.clock = clock;
    }

    public List<Question> getQuestionsForStudent(Long quizId, Long studentId) {
        Quiz quiz = quizService.findQuizById(quizId);
        LocalDateTime now = clock.now();

        if (!(quiz.isGestartet(now)) ){
            throw new QuizNotStartedException("Quiz noch nicht begonnen.");
        }

        //TODO: mby later checken ob student für quiz berechtigt ist

        return quiz.getQuestions();
    }

    public void submitAnswer(Long quizId, Long studentId, Long questionId, String answerContent) {
        Quiz quiz = quizService.findQuizById(quizId);
        LocalDateTime now = clock.now();

        if (!(quiz.isGestartet(now)) ){
            throw new QuizNotStartedException("Quiz hat noch nicht begonnen.");
        }

        if (quiz.isBeendet(now)) {
            throw new QuizAlreadyEndedException("Quiz beendet.");
        }

        Question question = quiz.findQuestionById(questionId);
        if (question == null) {
            throw new IllegalArgumentException("Ungültige Frage-ID.");
        }

        QuizAttempt quizAttempt = attemptRepository.findQuizAttemptByQuizIdAndStudentId(quizId, studentId)
                .orElseGet(() -> new QuizAttempt(System.currentTimeMillis(), quizId, studentId));

        Answer newAnswer = new TextAnswer(questionId, answerContent, now);
        quizAttempt.addOrUpdateAnswer(questionId, newAnswer, quiz, now);

        attemptRepository.saveQuizAttempt(quizAttempt);
    }


    public List<Answer> getSubmittedAnswers(Long quizId, Long studentId) {
        Quiz quiz = quizService.findQuizById(quizId);
        LocalDateTime now = clock.now();

        if (!(quiz.isGestartet(now))) {
            throw new QuizNotStartedException("Der Test hat noch nicht begonnen.");
        }

        return attemptRepository.findAllByQuizIdAndStudentId(quizId, studentId);
    }
}
