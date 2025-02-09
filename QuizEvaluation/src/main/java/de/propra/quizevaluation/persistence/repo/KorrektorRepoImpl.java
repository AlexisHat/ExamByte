package de.propra.quizevaluation.persistence.repo;

import de.propra.quizevaluation.domain.Korrektor;
import de.propra.quizevaluation.domain.service.KorrektorRepo;
import de.propra.quizevaluation.persistence.dao.KorrektorCrudRepo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class KorrektorRepoImpl implements KorrektorRepo {

    KorrektorCrudRepo korrektorCrudRepo;

    public KorrektorRepoImpl(KorrektorCrudRepo korrektorCrudRepo) {
        this.korrektorCrudRepo = korrektorCrudRepo;
    }

    @Override
    public List<Korrektor> findAll() {
        return korrektorCrudRepo.findAll();
    }

    @Override
    public Optional<Korrektor> findByGithubId(String id) {
        return korrektorCrudRepo.findByGithubId(id);
    }

    @Override
    public Korrektor save(Korrektor korrektor) {
        return korrektorCrudRepo.save(korrektor);
    }
}
