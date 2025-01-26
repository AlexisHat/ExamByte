package de.propra.quizevaluation.repo;

import de.propra.quizevaluation.domain.quiz.QuestionEntity;
import de.propra.quizevaluation.domain.quiz.Quiz;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class QuizRepoImpl {
    private final QuizCrudRepo quizCrudRepository;

    public QuizRepoImpl(QuizCrudRepo quizCrudRepository) {
        this.quizCrudRepository = quizCrudRepository;
    }

    public void save(Quiz quiz) {
        quizCrudRepository.save(quiz);
    }

    public void insert(Quiz quiz) {
        quizCrudRepository.insert(
                quiz.getId(),
                quiz.getQuizName(),
                quiz.getStartTime(),
                quiz.getEndTime(),
                quiz.getResultTime()
        );
    }

    @Transactional
    public void saveQuizWithQuestions(Quiz quizEntity) {
        quizCrudRepository.insert(
                quizEntity.getQuizId(),
                quizEntity.getQuizName(),
                quizEntity.getStartTime(),
                quizEntity.getEndTime(),
                quizEntity.getResultTime()
        );

        for (QuestionEntity question : quizEntity.getQuestions()) {
            quizCrudRepository.insertQuestion(
                    question.getQuestionId(),
                    quizEntity.getQuizId(),
                    question.getPoints(),
                    question.getTitel(),
                    question.getTask(),
                    question.getType().name(),
                    question.getOptions(),
                    question.getCorrectOptionIndex(),
                    question.getMusterLoesungForTextQuestion()
            );
        }
    }

    public QuestionEntity findQuestionById(Long questionId) {
        return quizCrudRepository.findQuestionById(questionId).orElse(null);
    }
}

