package de.propra.quizevaluation.repo;

import de.propra.quizevaluation.domain.Answer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerCrud extends CrudRepository<Answer, Long> {
}
