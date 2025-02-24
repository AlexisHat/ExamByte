package de.propra.quizevaluation.service;

import de.propra.quizevaluation.domain.Attempt.Answer;
import de.propra.quizevaluation.domain.Korrektor;
import de.propra.quizevaluation.domain.quiz.QuestionEntity;
import de.propra.quizevaluation.domain.service.KorrektorRepo;
import de.propra.quizevaluation.persistence.repo.AnswerRepoImpl;
import de.propra.quizevaluation.persistence.repo.QuizRepoImpl;
import de.propra.quizevaluation.web.DTO.TextAnswerCorrectionDTO;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class KorrektorService {

    private final List<Korrektor> korrektoren;
    private final KorrektorRepo korrektorRepo;
    private final AnswerRepoImpl answerRepoImpl;
    private final QuizRepoImpl quizRepoImpl;

    public KorrektorService(KorrektorRepo korrektorRepo, AnswerRepoImpl answerRepoImpl, QuizRepoImpl quizRepoImpl) {
        this.korrektoren = korrektorRepo.findAll();
        this.korrektorRepo = korrektorRepo;
        this.answerRepoImpl = answerRepoImpl;
        this.quizRepoImpl = quizRepoImpl;
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

    public TextAnswerCorrectionDTO createDtoFromAnswer(Answer answers) {
            QuestionEntity questionById = quizRepoImpl.findQuestionById(answers.getFrageId());
            String musterLoesungForTextQuestion = questionById.getMusterLoesungForTextQuestion();
            String task = questionById.getTask();
            String titel = questionById.getTitel();
            Double maxPoints = questionById.getPoints();

            return new TextAnswerCorrectionDTO(answers.getTextAnswer(), titel, task,musterLoesungForTextQuestion ,maxPoints, answers.getPoints());
    }

    public void updatePointsForAnswer(TextAnswerCorrectionDTO textAnswerCorrectionDTO, Long id) {
        Answer byId = answerRepoImpl.findById(id);
        byId.setPoints(textAnswerCorrectionDTO.points());
        answerRepoImpl.save(byId);
    }
}
