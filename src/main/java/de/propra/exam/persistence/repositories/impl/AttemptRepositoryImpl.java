package de.propra.exam.persistence.repositories.impl;

import de.propra.exam.domain.model.quizattempt.QuizAttempt;
import de.propra.exam.domain.model.quizattempt.answer.Answer;
import de.propra.exam.domain.service.AttemptRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AttemptRepositoryImpl implements AttemptRepository {
    @Override
    public Optional<QuizAttempt> findQuizAttemptByQuizIdAndStudentId(Long quizId, Long studentId) {
        return Optional.of(null);
    }

    @Override
    public void saveQuizAttempt(QuizAttempt quizAttempt) {

    }

    @Override
    public List<Answer> findAllByQuizIdAndStudentId(Long quizId, Long studentId) {
        return null;
    }

    @Override
    public QuizAttempt createQuizAttempt(Long quizId, Long studentId) {
        return null;
    }
}
