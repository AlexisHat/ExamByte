package de.propra.exam.domain.service;

import de.propra.exam.domain.model.quizcore.Quiz;

import java.util.List;
import java.util.Optional;

public interface QuizRepository {
    List<Quiz> findAll();
    void save(Quiz quiz);
    Optional<Quiz> findById(Integer id);
}
