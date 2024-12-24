package de.propra.exam.application.service;

import de.propra.exam.domain.exceptions.QuestionNotFoundException;
import de.propra.exam.domain.exceptions.QuizNotStartedException;
import de.propra.exam.domain.model.quiz.*;
import de.propra.exam.domain.model.quiz.question.MultipleChoiceQuestion;
import de.propra.exam.domain.model.quiz.question.Question;
import de.propra.exam.domain.model.quiz.question.TextQuestion;
import de.propra.exam.domain.model.quizattempt.answer.Answer;
import de.propra.exam.domain.model.quizattempt.QuizAttempt;
import de.propra.exam.domain.model.quizattempt.answer.MultipleChoiceAnswer;
import de.propra.exam.domain.model.quizattempt.answer.TextAnswer;
import de.propra.exam.domain.service.AttemptRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TestExecutionService {

    private final QuizService quizService;
    private final QuizValidationService quizValidationService;
    private final AttemptRepository attemptRepository;

    public TestExecutionService(QuizService quizService,
                                QuizValidationService quizValidationService,
                                AttemptRepository attemptRepository) {
        this.quizService = quizService;
        this.quizValidationService = quizValidationService;
        this.attemptRepository = attemptRepository;
    }

    public List<Question> getQuestionsForStudent(Long quizId, Long studentId) {
        Quiz quiz = quizService.findQuizById(quizId);
        quizValidationService.validateQuizStarted(quiz);

        //TODO: mby later checken ob student für quiz berechtigt ist

        return quiz.getQuestions();
    }

    public void submitAnswer(Long quizId, Long studentId, Long questionId, String answerContent) {
            Quiz quiz = quizService.findQuizById(quizId);
            quizValidationService.validateQuizStartedAndNotEnded(quiz);


            Question question = quiz.findQuestionById(questionId);

        if (question == null) {
            throw new QuestionNotFoundException("Ungültige Frage-ID.");
        }

        QuizAttempt quizAttempt = findOrCreateQuizAttempt(quizId, studentId);
        Answer newAnswer;
        if (question instanceof TextQuestion) {
            newAnswer = new TextAnswer(questionId, answerContent, quizValidationService.getCurrentTime());
        } else if (question instanceof MultipleChoiceQuestion) {
            List<String> options = parseMultipleChoiceAnswer(answerContent);
            newAnswer = new MultipleChoiceAnswer(questionId, options, quizValidationService.getCurrentTime());
        }else {
            throw new IllegalStateException("Unbekannter Fragetyp.");
        }

        quizAttempt.addOrUpdateAnswer(questionId, newAnswer, quiz, quizValidationService.getCurrentTime());

        attemptRepository.saveQuizAttempt(quizAttempt);
    }

    private List<String> parseMultipleChoiceAnswer(String answerContent) {
        return Arrays.stream(answerContent.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    public List<Answer> getSubmittedAnswers(Long quizId, Long studentId) {
        Quiz quiz = quizService.findQuizById(quizId);
        quizValidationService.validateQuizStarted(quiz);

        return attemptRepository.findAllByQuizIdAndStudentId(quizId, studentId);
    }

    private QuizAttempt findOrCreateQuizAttempt(Long quizId, Long studentId) {
        return attemptRepository.findQuizAttemptByQuizIdAndStudentId(quizId, studentId)
                .orElseGet(() -> attemptRepository.createQuizAttempt(quizId, studentId));
    }
}