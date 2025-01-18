package de.propra.quizevaluation.repo;

import de.propra.quizevaluation.domain.Korrektor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KorrektorCrudRepo extends CrudRepository<Korrektor, Long> {
    List<Korrektor> findAll();

    Optional<Korrektor> findByGithubId(String id);
}
