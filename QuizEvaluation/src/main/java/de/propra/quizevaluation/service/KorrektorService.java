package de.propra.quizevaluation.service;

import de.propra.quizevaluation.domain.Attempt.Answer;
import de.propra.quizevaluation.domain.Korrektor;
import de.propra.quizevaluation.domain.service.KorrektorRepo;
import de.propra.quizevaluation.repo.AnswerRepoImpl;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class KorrektorService {

    private final List<Korrektor> korrektoren;
    private final KorrektorRepo korrektorRepo;
    private final AnswerRepoImpl answerRepo;

    public KorrektorService(KorrektorRepo korrektorRepo, AnswerRepoImpl answerRepo) {
        this.korrektoren = korrektorRepo.findAll();
        this.korrektorRepo = korrektorRepo;
        this.answerRepo = answerRepo;
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