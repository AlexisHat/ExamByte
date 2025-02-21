package de.propra.quizevaluation.service;

import de.propra.quizevaluation.domain.Attempt.Answer;
import de.propra.quizevaluation.domain.Korrektor;
import de.propra.quizevaluation.domain.service.KorrektorRepo;
import de.propra.quizevaluation.persistence.repo.AnswerRepoImpl;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class KorrektorService {

    private final List<Korrektor> korrektoren;
    private final KorrektorRepo korrektorRepo;
    private final AnswerRepoImpl answerRepoImpl;

    public KorrektorService(KorrektorRepo korrektorRepo, AnswerRepoImpl answerRepoImpl) {
        this.korrektoren = korrektorRepo.findAll();
        this.korrektorRepo = korrektorRepo;
        this.answerRepoImpl = answerRepoImpl;
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

    public Long findKorrektorIdByGitHubId(String id) {
        return korrektorRepo.findByGithubId(id).orElseThrow().getId();
    }

    public List<Answer> getAnswersForKorrektorWithId(Long korrektorId) {
        return answerRepoImpl.findAllForKorrektor(korrektorId);
    }
}