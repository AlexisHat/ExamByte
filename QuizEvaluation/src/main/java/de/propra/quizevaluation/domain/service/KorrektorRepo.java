package de.propra.quizevaluation.domain.service;

import de.propra.quizevaluation.domain.Korrektor;

import java.util.List;
import java.util.Optional;

public interface KorrektorRepo {
    List<Korrektor> findAll();

    Optional<Korrektor> findByGithubId(String id);

    Korrektor save(Korrektor korrektor);
}

