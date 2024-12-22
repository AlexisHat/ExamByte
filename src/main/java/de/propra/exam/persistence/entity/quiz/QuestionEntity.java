package de.propra.exam.persistence.entity.quiz;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table("question")
public class QuestionEntity {
    @Id
    private Long questionId;

    private Double points;

    private final String titel;

    private final String task;

    private final QuestionType type;

    private final List<String> options;

    private final List<Integer> correctOptionIndex;

    private final String musterLoesungForTextQuestion;


    private QuestionEntity(Long questionId,Double points ,String titel ,
                           String task, QuestionType type, List<String> options,
                           List<Integer> correctOptionIndex, String musterLoesungForTextQuestion) {
        this.questionId = questionId;
        this.points = points;
        this.titel = titel;
        this.task = task;
        this.type = type;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
        this.musterLoesungForTextQuestion = musterLoesungForTextQuestion;
    }
    @PersistenceCreator
    public static QuestionEntity of(Long questionId,Double points, String titel,
                                    String task, QuestionType type, List<String> options,
                                    List<Integer> correctOptionIndex, String correctAnswer) {
        return new QuestionEntity(questionId,points, titel, task, type, options, correctOptionIndex, correctAnswer);
    }

    public static QuestionEntity ofText(Long questionId, Double points, String titel,
                                        String task, String correctAnswerForText) {
        return new QuestionEntity(questionId,points, titel, task,
                QuestionType.TEXT, null,null, correctAnswerForText);
    }

    public static QuestionEntity ofMutiple(Long questionId, Double points, String titel,
                                           String task, List<String> options, List<Integer> correctOptionIndex) {
        return new QuestionEntity(questionId,points, titel, task,
                QuestionType.MULTIPLE_CHOICE, options ,correctOptionIndex, null);
    }

    public QuestionType getType() {
        return type;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public String getTitel() {
        return titel;
    }

    public String getTask() {
        return task;
    }

    public List<String> getOptions() {
        return options;
    }

    public List<Integer> getCorrectOptionIndex() {
        return correctOptionIndex;
    }

    public String getMusterLoesungForTextQuestion() {
        return musterLoesungForTextQuestion;
    }
    public Double getPoints() {
        return points;
    }
}
