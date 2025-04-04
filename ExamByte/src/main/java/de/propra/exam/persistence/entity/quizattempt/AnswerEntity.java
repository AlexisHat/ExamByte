package de.propra.exam.persistence.entity.quizattempt;

import de.propra.exam.persistence.entity.quiz.QuestionType;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Table("answer")
public class AnswerEntity {

    @Id
    private Long answerId;

    private final Long frageId;

    private final QuestionType type;

    private final String selectedOptions;

    private final String textAnswer;

    private final LocalDateTime submittedAt;

    private final Double points;


    private AnswerEntity(Long answerId, Long frageId, QuestionType type, String selectedOptions,
                         String textAnswer, LocalDateTime submittedAt, Double points) {
        this.answerId = answerId;
        this.frageId = frageId;
        this.type = type;
        this.selectedOptions = selectedOptions;
        this.textAnswer = textAnswer;
        this.submittedAt = submittedAt;
        this.points = points;
    }

    @PersistenceCreator
    public static AnswerEntity of(Long answerId, Long frageId, QuestionType type,
                                  String selectedOptions, String textAnswer, LocalDateTime submittedAt, Double points) {
        return new AnswerEntity(answerId, frageId, type, selectedOptions, textAnswer, submittedAt, points);
    }

    public static AnswerEntity ofMultipleChoice(Long answerId, Long frageId, List<String> options,
                                                LocalDateTime submittedAt) {
        String optionsAsString = String.join(",", options);
        return new AnswerEntity(answerId, frageId, QuestionType.MULTIPLE_CHOICE, optionsAsString, null, submittedAt,null);
    }

    public static AnswerEntity ofText(Long answerId, Long frageId, String textAnswer,
                                      LocalDateTime submittedAt) {
        return new AnswerEntity(answerId, frageId, QuestionType.TEXT, null, textAnswer, submittedAt,null);
    }

    public Long getAnswerId() {
        return answerId;
    }

    public Long getFrageId() {
        return frageId;
    }

    public QuestionType getType() {
        return type;
    }

    public List<String> getSelectedOptions() {
        return selectedOptions == null || selectedOptions.isEmpty()
                ? List.of()
                : List.of(selectedOptions.split(","));
    }

    public String getTextAnswer() {
        return textAnswer;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }
}