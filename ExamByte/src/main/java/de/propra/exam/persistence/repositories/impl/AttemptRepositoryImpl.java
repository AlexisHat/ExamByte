package de.propra.exam.persistence.repositories.impl;

import de.propra.exam.domain.model.quizattempt.QuizAttempt;
import de.propra.exam.domain.model.quizattempt.answer.Answer;
import de.propra.exam.application.service.repository.AttemptRepository;
import de.propra.exam.persistence.entity.quizattempt.QuizAttemptEntity;
import de.propra.exam.persistence.mapper.QuizAttemptMapper;
import de.propra.exam.persistence.repositories.crud.AttemptCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AttemptRepositoryImpl implements AttemptRepository {
    AttemptCrudRepository attemptCrudRepository;

    public AttemptRepositoryImpl(AttemptCrudRepository attemptCrudRepository) {
        this.attemptCrudRepository = attemptCrudRepository;
    }

    @Override
    public Optional<QuizAttempt> findQuizAttemptByQuizIdAndStudentId(Long quizId, Long studentId) {
        return attemptCrudRepository.findAllByStudentId(studentId).stream()
                .filter(attempt -> attempt.quizId().equals(quizId))
                .findFirst()
                .map(QuizAttemptMapper::toDomain);
    }

    @Override
    public QuizAttempt saveQuizAttempt(QuizAttempt quizAttempt) {
        QuizAttemptEntity entity = QuizAttemptMapper.toAttemptEntity(quizAttempt);
        attemptCrudRepository.save(entity);
        return quizAttempt;
    }

    @Override
    public List<Answer> findAllByQuizIdAndStudentId(Long quizId, Long studentId) {
        return attemptCrudRepository.findAllByStudentId(studentId).stream()
                .filter(attempt -> attempt.quizId().equals(quizId))
                .flatMap(attempt -> attempt.answers().stream())
                .map(QuizAttemptMapper::toDomain)
                .toList();
    }

    public QuizAttempt findById(Long id) {
        Optional<QuizAttempt> o = attemptCrudRepository.findById(id).map(QuizAttemptMapper::toDomain);
        return o.get();
    }
}