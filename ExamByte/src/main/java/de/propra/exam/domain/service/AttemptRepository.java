package de.propra.exam.domain.service;

import de.propra.exam.domain.model.quizattempt.QuizAttempt;
import de.propra.exam.domain.model.quizattempt.answer.Answer;

import java.util.List;
import java.util.Optional;

public interface AttemptRepository {

    Optional<QuizAttempt> findQuizAttemptByQuizIdAndStudentId(Long quizId, Long studentId);

    QuizAttempt saveQuizAttempt(QuizAttempt quizAttempt);

    List<Answer> findAllByQuizIdAndStudentId(Long quizId, Long studentId);

    QuizAttempt findById(Long quizAttemptId);
}

