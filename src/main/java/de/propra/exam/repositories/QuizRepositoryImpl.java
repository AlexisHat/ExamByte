package de.propra.exam.repositories;

import de.propra.exam.domain.model.quiz.Quiz;
import de.propra.exam.domain.service.QuizRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class QuizRepositoryImpl implements QuizRepository {
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
