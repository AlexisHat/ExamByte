package de.propra.exam.persistence.mapper;

import de.propra.exam.domain.model.quiz.Quiz;
import de.propra.exam.domain.model.quiz.question.MultipleChoiceQuestion;
import de.propra.exam.domain.model.quiz.question.Question;
import de.propra.exam.domain.model.quiz.question.QuestionBuilder;
import de.propra.exam.domain.model.quiz.question.TextQuestion;
import de.propra.exam.persistence.entity.quiz.QuestionEntity;
import de.propra.exam.persistence.entity.quiz.QuizEntity;

public class QuizMapper {

    public static Quiz toDomain(QuizEntity quizEntity) {
        Quiz quiz = new Quiz();
        quiz.setQuizID(quizEntity.getQuizId());
        quiz.setQuizName(quizEntity.getQuizName());
        quiz.setStartTime(quizEntity.getStartTime());
        quiz.setEndTime(quizEntity.getEndTime());

        quiz.setQuestions(quizEntity.getQuestions().stream()
                .map(QuizMapper::toDomain)
                .toList());

        return quiz;
    }

    public static QuizEntity toQuizEntity(Quiz quiz) {
        return new QuizEntity(
                quiz.getQuizID(),
                quiz.getQuizName(),
                quiz.getStartTime(),
                quiz.getEndTime(),
                quiz.getQuestions().stream()
                        .map(QuizMapper::toQuestionEntity)
                        .toList()
        );
    }

    public static Question toDomain(QuestionEntity questionEntity) {
        switch (questionEntity.getType()){
            case TEXT -> {
                return new QuestionBuilder()
                        .withQuestionType("text")
                        .withId(questionEntity.getQuestionId())
                        .withPoints(questionEntity.getPoints())
                        .withMusterLoesung(questionEntity.getMusterLoesungForTextQuestion())
                        .withTask(questionEntity.getTask())
                        .withTitle(questionEntity.getTitel())
                        .build();
            }
            case MULTIPLE_CHOICE -> {
                return new QuestionBuilder()
                        .withQuestionType("multipleChoice")
                        .withId(questionEntity.getQuestionId())
                        .withPoints(questionEntity.getPoints())
                        .withTask(questionEntity.getTask())
                        .withTitle(questionEntity.getTitel())
                        .withOptions(questionEntity.getOptions())
                        .withCorrectOptionIndexes(questionEntity.getCorrectOptionIndex())
                        .build();
            }
            }
        return null;
    }

    public static QuestionEntity toQuestionEntity(Question question) {
        if (question instanceof TextQuestion textQuestion) {
            return QuestionEntity.ofText(
                    textQuestion.getQuestionId(),
                    textQuestion.getPoints(),
                    textQuestion.getTitle(),
                    textQuestion.getTask(),
                    textQuestion.getMusterLoesung()
            );
        } else if (question instanceof MultipleChoiceQuestion multipleChoiceQuestion) {
            return QuestionEntity.ofMutiple(
                    multipleChoiceQuestion.getQuestionId(),
                    multipleChoiceQuestion.getPoints(),
                    multipleChoiceQuestion.getTitle(),
                    multipleChoiceQuestion.getTask(),
                    multipleChoiceQuestion.getOptionsAsString(),
                    multipleChoiceQuestion.getCorrectOptionIndexesAsString()
            );
        }
        return null;
    }
}