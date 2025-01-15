package de.propra.quizevaluation.domain.service;

import de.propra.quizevaluation.domain.Attempt;

public interface AttemptRepo {
    void save(Attempt attempt);
}
