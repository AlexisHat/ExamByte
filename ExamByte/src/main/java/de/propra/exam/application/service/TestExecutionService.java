package de.propra.exam.application.service;

import de.propra.exam.domain.exceptions.QuestionNotFoundException;
import de.propra.exam.domain.model.quiz.*;
import de.propra.exam.domain.model.quiz.question.MultipleChoiceQuestion;
import de.propra.exam.domain.model.quiz.question.Question;
import de.propra.exam.domain.model.quiz.question.TextQuestion;
import de.propra.exam.domain.model.quizattempt.answer.Answer;
import de.propra.exam.domain.model.quizattempt.QuizAttempt;
import de.propra.exam.domain.model.quizattempt.answer.MultipleChoiceAnswer;
import de.propra.exam.domain.model.quizattempt.answer.TextAnswer;
import de.propra.exam.application.service.repository.AttemptRepository;
import org.springframework.stereotype.Service;

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

    public void submitAnswer(Long quizId, Long studentId, Long questionId, String answerContent) {
        Quiz quiz = validateAndFetchQuiz(quizId);
        Question question = validateAndFetchQuestion(quiz, questionId);
        QuizAttempt quizAttempt = findOrCreateQuizAttempt(quizId, studentId);
        Answer newAnswer = createAnswer(question, answerContent);
        updateAndSaveQuizAttempt(quizAttempt, questionId, newAnswer, quiz);
    }

    Quiz validateAndFetchQuiz(Long quizId) {
        Quiz quiz = quizService.findQuizById(quizId);
        quizValidationService.validateQuizStartedAndNotEnded(quiz);
        return quiz;
    }

    Question validateAndFetchQuestion(Quiz quiz, Long questionId) {
         Question question = quiz.findQuestionById(questionId);
        if (question == null) {
            throw new QuestionNotFoundException("Ungültige Frage-ID.");
        }
        return question;
    }

    Answer createAnswer(Question question, String answerContent) {
        if (question instanceof TextQuestion) {
            return new TextAnswer(question.getQuestionId(), answerContent, quizValidationService.getCurrentTime());
        } else if (question instanceof MultipleChoiceQuestion) {
            List<String> options = parseMultipleChoiceAnswer(answerContent);
            return new MultipleChoiceAnswer(question.getQuestionId(), options, quizValidationService.getCurrentTime());
        } else {
            throw new IllegalStateException("Unbekannter Fragetyp.");
        }
    }

    void updateAndSaveQuizAttempt(QuizAttempt quizAttempt, Long questionId, Answer newAnswer, Quiz quiz) {
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

    public QuizAttempt findOrCreateQuizAttempt(Long quizId, Long studentId) {
        return attemptRepository.findQuizAttemptByQuizIdAndStudentId(quizId, studentId)
                .orElseGet(() -> createQuizAttempt(quizId, studentId));
    }

    private QuizAttempt createQuizAttempt(Long quizId, Long studentId) {
        QuizAttempt attempt = new QuizAttempt(null, quizId, studentId);
        return attemptRepository.saveQuizAttempt(attempt);
    }
}