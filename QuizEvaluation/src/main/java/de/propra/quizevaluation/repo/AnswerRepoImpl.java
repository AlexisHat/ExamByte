package de.propra.quizevaluation.repo;

import de.propra.quizevaluation.domain.Answer;
import org.springframework.stereotype.Repository;

@Repository
public class AnswerRepoImpl {

    private AnswerCrud answerCrud;

    public AnswerRepoImpl(AnswerCrud answerCrud) {
        this.answerCrud = answerCrud;
    }

    public Answer save(Answer answer) {
        return answerCrud.save(answer);
    }
}
