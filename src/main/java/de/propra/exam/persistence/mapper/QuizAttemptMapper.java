package de.propra.exam.persistence.mapper;

import de.propra.exam.domain.model.quizattempt.QuizAttempt;
import de.propra.exam.domain.model.quizattempt.answer.Answer;
import de.propra.exam.domain.model.quizattempt.answer.MultipleChoiceAnswer;
import de.propra.exam.domain.model.quizattempt.answer.TextAnswer;
import de.propra.exam.persistence.entity.quizattempt.AnswerEntity;
import de.propra.exam.persistence.entity.quizattempt.QuizAttemptEntity;


public class QuizAttemptMapper {

    public static QuizAttempt toDomain(QuizAttemptEntity attemptEntity){
        return new QuizAttempt
                (attemptEntity.quizAttemptId(), attemptEntity.quizId(), attemptEntity.studentId(), attemptEntity
                        .answers().stream()
                        .map(QuizAttemptMapper::toDomain)
                        .toList());
    }

    public static QuizAttemptEntity toAttemptEntity(QuizAttempt quizAttempt){
        return new QuizAttemptEntity(quizAttempt.getQuizAttemptId(),
                quizAttempt.getQuizId(),
                quizAttempt.getStudentId(),
                quizAttempt.getAntworten().stream()
                        .map(QuizAttemptMapper::toAnswerEntity)
                        .toList());
    }

    public static AnswerEntity toAnswerEntity(Answer answer) {
        if (answer instanceof TextAnswer textAnswer) {
            return AnswerEntity.ofText(
                    answer.getId(),
                    answer.getFrageId(),
                    textAnswer.getText(),
                    answer.getAbgegebenAm());
        } else if (answer instanceof MultipleChoiceAnswer multipleChoiceAnswer) {
            return AnswerEntity.ofMultipleChoice(
                    answer.getId(),
                    answer.getFrageId(),
                    multipleChoiceAnswer.getAusgewaehlteOptionen(),
                    answer.getAbgegebenAm());
        }
        return null;
    }

    public static Answer toDomain(AnswerEntity answerEntity){
        switch (answerEntity.getType()){
            case TEXT -> {
                return new TextAnswer(
                        answerEntity.getAnswerId(),
                        answerEntity.getFrageId(),
                        answerEntity.getSubmittedAt(),
                        answerEntity.getTextAnswer());
            }
            case MULTIPLE_CHOICE -> {
                return new MultipleChoiceAnswer(
                        answerEntity.getAnswerId(),
                        answerEntity.getFrageId(),
                        answerEntity.getSubmittedAt(),
                        answerEntity.getSelectedOptions());
            }
        }
        return null;
    }

}
