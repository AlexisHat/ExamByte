package de.propra.quizevaluation.persistence.dao;

import de.propra.quizevaluation.domain.Attempt.Answer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerCrud extends CrudRepository<Answer, Long> {
}
