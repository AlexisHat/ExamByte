package de.propra.exam.persistence.repositories.crud;

import de.propra.exam.persistence.entity.quiz.QuizEntity;
import org.springframework.data.repository.CrudRepository;

public interface QuizCrudRepository extends CrudRepository<QuizEntity, Long> {
}
