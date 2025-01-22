package de.propra.exam.persistence.entity.quiz;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Table("question")
public class QuestionEntity {
    @Id
    private Long questionId;

    private Double points;

    private final String titel;

    private final String task;

    private final QuestionType type;

    private final String options;

    private final String  correctOptionIndex;

    private final String musterLoesungForTextQuestion;


    private QuestionEntity(Long questionId,Double points ,String titel ,
                           String task, QuestionType type, String options,
                           String correctOptionIndex, String musterLoesungForTextQuestion) {
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
                                    String task, QuestionType type, String options,
                                    String correctOptionIndex, String musterLoesungForTextQuestion) {
        return new QuestionEntity(questionId,points, titel, task, type, options, correctOptionIndex, musterLoesungForTextQuestion);
    }

    public static QuestionEntity ofText(Long questionId, Double points, String titel,
                                        String task, String correctAnswerForText) {
        return new QuestionEntity(questionId,points, titel, task,
                QuestionType.TEXT, null,null, correctAnswerForText);
    }

    public static QuestionEntity ofMutiple(Long questionId, Double points, String titel,
                                           String task, String options, String correctOptionIndex) {
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

    public List<String> getOptionsAsList() {
        return Arrays.stream(options.split(","))
                .collect(Collectors.toList());
    }


    public List<Integer> getCorrectOptionIndexAsList() {
        return Arrays.stream(this.correctOptionIndex.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public String getOptions() {
        return options;
    }

    public String getCorrectOptionIndex() {
        return correctOptionIndex;
    }

    public String getMusterLoesungForTextQuestion() {
        return musterLoesungForTextQuestion;
    }
    public Double getPoints() {
        return points;
    }
}
