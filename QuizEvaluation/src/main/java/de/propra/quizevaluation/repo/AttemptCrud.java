package de.propra.quizevaluation.repo;

import de.propra.quizevaluation.domain.Attempt;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttemptCrud extends CrudRepository<Attempt, Long> {
}
