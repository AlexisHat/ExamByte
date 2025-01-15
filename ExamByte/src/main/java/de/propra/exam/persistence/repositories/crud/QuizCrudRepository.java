package de.propra.exam.persistence.repositories.crud;

import de.propra.exam.persistence.entity.quiz.QuizEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizCrudRepository extends CrudRepository<QuizEntity, Long> {
}
