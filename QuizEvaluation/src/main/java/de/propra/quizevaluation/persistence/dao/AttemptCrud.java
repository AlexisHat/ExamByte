package de.propra.quizevaluation.persistence.dao;

import de.propra.quizevaluation.domain.Attempt.Attempt;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttemptCrud extends CrudRepository<Attempt, Long> {
}
