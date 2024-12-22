package de.propra.exam.persistence.entity.quiz;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;

public class QuestionEntity {
    @Id
    private Long questionId;

    private final String titel;

    private final String task;

    private final QuestionType type;

    private final String options;

    private final Integer correctOptionIndex;

    private final String correctAnswer;


    private QuestionEntity(Long questionId, String titel,
                           String task, QuestionType type, String options,
                           Integer correctOptionIndex, String correctAnswer) {
        this.questionId = questionId;
        this.titel = titel;
        this.task = task;
        this.type = type;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
        this.correctAnswer = correctAnswer;
    }
    @PersistenceCreator
    public static QuestionEntity of(Long questionId, String titel,
                                    String task, QuestionType type, String options,
                                    Integer correctOptionIndex, String correctAnswer) {
        return new QuestionEntity(questionId, titel, task, type, options, correctOptionIndex, correctAnswer);
    }

    public static QuestionEntity forMultipleChoice(String titel, String task,
                                                   String options, Integer correctOptionIndex) {
        return new QuestionEntity(null, titel, task, QuestionType.MULTIPLE_CHOICE,
                options, correctOptionIndex, null);
    }

    public static QuestionEntity forText(String titel, String task,
                                                   String options, String correctAnswer) {
        return new QuestionEntity(null, titel, task, QuestionType.TEXT,
                options,null, correctAnswer);
    }
}
