package de.propra.quizevaluation.domain;

import de.propra.quizevaluation.domain.db.QuestionType;
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

    @PersistenceCreator
    public Answer(Long frageId, QuestionType type, List<String> selectedOptions, String textAnswer, LocalDateTime submittedAt, Long answerId) {
        this.frageId = frageId;
        this.type = type;
        this.selectedOptions = selectedOptions;
        this.textAnswer = textAnswer;
        this.submittedAt = submittedAt;
        this.answerId = answerId;
    }

    public static Answer createDummyAnswer() {
        return new Answer((long) (Math.random() * 100) + 1, QuestionType.TEXT, null, null, null, (long) (Math.random() * 100) + 1);
    }

    public QuestionType getType() {
        return type;
    }
}
