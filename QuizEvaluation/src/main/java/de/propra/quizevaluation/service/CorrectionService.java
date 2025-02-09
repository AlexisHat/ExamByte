package de.propra.quizevaluation.service;

import de.propra.quizevaluation.domain.Attempt.Answer;
import de.propra.quizevaluation.domain.Attempt.QuestionType;
import de.propra.quizevaluation.domain.quiz.QuestionEntity;
import de.propra.quizevaluation.persistence.repo.AnswerRepoImpl;
import de.propra.quizevaluation.persistence.repo.QuizRepoImpl;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CorrectionService {

    private final QuizRepoImpl quizRepoImpl;
    private final AnswerRepoImpl answerRepoImpl;

    public CorrectionService(QuizRepoImpl quizRepoImpl, AnswerRepoImpl answerRepoImpl) {
        this.quizRepoImpl = quizRepoImpl;
        this.answerRepoImpl = answerRepoImpl;
    }

    public void autoCorrectMutiple(List<Answer> mutiple) {
        for (Answer answer : mutiple) {
            Long frageId = answer.getFrageId();

            QuestionEntity questionById = quizRepoImpl.findQuestionById(frageId);

            Double maxPoints = questionById.getPoints();
        if(!questionById.getType().equals(QuestionType.MULTIPLE_CHOICE)){
            throw new IllegalArgumentException();
        }
            List<String> correctOptionIndex =  Arrays.stream
                            (questionById.getCorrectOptionIndex().split(","))
                            .sorted()
                            .toList();
            List<String> selectedOptions = answer.getSelectedOptions().stream().sorted().toList();

            int error = (int) (selectedOptions.stream().filter(o -> !correctOptionIndex.contains(o)).count() +
                                            correctOptionIndex.stream().filter(o -> !selectedOptions.contains(o)).count());

            switch (error) {
                case 0:
                    answer.setPoints(maxPoints);
                    break;
                case 1:
                    answer.setPoints(maxPoints / 2);
                    break;
                default:
                    answer.setPoints(0.0);
                    break;
            }
            answerRepoImpl.save(answer);
        }
    }
}
