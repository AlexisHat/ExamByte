package de.propra.quizevaluation.persistence.repo;

import de.propra.quizevaluation.domain.Attempt.Answer;
import de.propra.quizevaluation.persistence.dao.AnswerCrud;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AnswerRepoImpl {

    private AnswerCrud answerCrud;

    public AnswerRepoImpl(AnswerCrud answerCrud) {
        this.answerCrud = answerCrud;
    }

    public Answer save(Answer answer) {
        return answerCrud.save(answer);
    }

    public List<Answer> findAllForKorrektor(Long korrektorId) {
        return answerCrud.findAllByKorrektor(korrektorId);
    }

    public Answer findById(Long id) {
        return answerCrud.findById(id).orElse(null);
    }
}
