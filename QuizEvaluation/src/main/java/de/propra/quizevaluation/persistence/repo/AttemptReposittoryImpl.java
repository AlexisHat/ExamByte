package de.propra.quizevaluation.persistence.repo;

import de.propra.quizevaluation.domain.Attempt.Attempt;
import de.propra.quizevaluation.domain.service.AttemptRepo;
import de.propra.quizevaluation.persistence.dao.AttemptCrud;
import org.springframework.stereotype.Repository;

@Repository
public class AttemptReposittoryImpl implements AttemptRepo {

    AttemptCrud attemptCrud;

    public AttemptReposittoryImpl(AttemptCrud attemptCrud) {
        this.attemptCrud = attemptCrud;
    }

    @Override
    public void save(Attempt attempt) {
        attemptCrud.save(attempt);
    }

}
