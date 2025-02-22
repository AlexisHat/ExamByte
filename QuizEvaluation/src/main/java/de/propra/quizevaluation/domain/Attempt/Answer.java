package de.propra.quizevaluation.domain.Attempt;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;

@Table("answer")
public class Answer {

    @Id
    private Long answerId;

    private final Long frageId;

    private final QuestionType type;

    private final List<String> selectedOptions;

    private final String textAnswer;

    private final LocalDateTime submittedAt;

    private double points;

    @PersistenceCreator
    public Answer(Long frageId, QuestionType type, List<String> selectedOptions, String textAnswer, LocalDateTime submittedAt, Long answerId, double points) {
        this.frageId = frageId;
        this.type = type;
        this.selectedOptions = selectedOptions;
        this.textAnswer = textAnswer;
        this.submittedAt = submittedAt;
        this.answerId = answerId;
        this.points = points;
    }

    public static Answer createDummyAnswer() {
        return new Answer((long) (Math.random() * 100) + 1, QuestionType.TEXT, null, null, null, (long) (Math.random() * 100) + 1, 1.0);
    }

    public QuestionType getType() {
        return type;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public Long getFrageId() {
        return frageId;
    }

    public List<String> getSelectedOptions() {
        return selectedOptions;
    }

    public double getPoints() {
        return points;
    }

    public String getTextAnswer() {
        return textAnswer;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setPoints(double points) {
        this.points = points;
    }
}
