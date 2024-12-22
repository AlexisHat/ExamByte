package de.propra.exam.persistence.repositories.impl;

import de.propra.exam.domain.model.quiz.Quiz;
import de.propra.exam.domain.service.QuizRepository;
import de.propra.exam.persistence.repositories.crud.QuizCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class QuizRepositoryImpl implements QuizRepository {

    private QuizCrudRepository quizCrudRepository;

    public QuizRepositoryImpl(QuizCrudRepository quizCrudRepository) {
        this.quizCrudRepository = quizCrudRepository;
    }

    @Override
    public List<Quiz> findAll() {
        return null;
    }

    @Override
    public void save(Quiz quiz) {

    }

    @Override
    public Optional<Quiz> findById(Long id) {
        return Optional.empty();
    }
}
