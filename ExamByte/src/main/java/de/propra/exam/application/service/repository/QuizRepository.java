package de.propra.exam.application.service.repository;

import de.propra.exam.domain.model.quiz.Quiz;

import java.util.List;
import java.util.Optional;

public interface QuizRepository {
    List<Quiz> findAll();
    Quiz save(Quiz quiz);
    Optional<Quiz> findById(Long id);
}
