package de.propra.quizevaluation.domain.service;

import de.propra.quizevaluation.domain.Attempt.Attempt;

public interface AttemptRepo {
    void save(Attempt attempt);
}
