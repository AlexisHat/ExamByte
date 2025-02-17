package de.propra.quizevaluation.service;

import de.propra.quizevaluation.domain.Answer;
import de.propra.quizevaluation.domain.Korrektor;
import de.propra.quizevaluation.repo.KorrektorRepoImpl;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class KorrektorService {

    private final List<Korrektor> korrektoren;
    private final KorrektorRepoImpl korrektorRepo;

    public KorrektorService(List<Korrektor> korrektoren, KorrektorRepoImpl korrektorRepo) {
        this.korrektoren = korrektorRepo.findAll();
        this.korrektorRepo = korrektorRepo;
    }

    public void distributeTextAnswers(List<Answer> textAntworten) {
        if (korrektoren == null || korrektoren.isEmpty()) {
            throw new IllegalStateException("Keine Korrektoren verfügbar.");
        }

        korrektoren.sort(Comparator.comparingInt(Korrektor::textAufgabenSize));

        int index = 0;
        for (Answer answer : textAntworten) {
            Korrektor korrektor = korrektoren.get(index);
            korrektor.addTextAufgaben(answer);

            korrektoren.sort(Comparator.comparingInt(Korrektor::textAufgabenSize));

        }
    }

}