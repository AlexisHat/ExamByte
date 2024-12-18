package de.propra.exam.domain.service;

import de.propra.exam.domain.model.quiz.Quiz;

import java.util.List;
import java.util.Optional;

public interface QuizRepository {
    List<Quiz> findAll();
    void save(Quiz quiz);
    Optional<Quiz> findById(Long id);
}
