package de.propra.exam.persistence.repositories.crud;

import de.propra.exam.persistence.entity.quizattempt.QuizAttemptEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttemptCrudRepository extends CrudRepository<QuizAttemptEntity, Long> {
    List<QuizAttemptEntity> findAllByStudentId(Long studentId);
}
