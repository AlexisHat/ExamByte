package de.propra.exam.web.DTO;

import de.propra.exam.domain.model.quiz.question.MultipleChoiceQuestion;
import de.propra.exam.domain.model.quiz.question.Question;
import de.propra.exam.domain.model.quiz.question.TextQuestion;

import java.util.List;

public class QuestionDTO {
    private String title;
    private String task;
    private String textMusterLoesung;
    private Double points;
    private String type;
    private List<String> options;
    private List<Integer> correctOptionIndexes;
    private int questionIndex;

    private QuestionDTO(String title, String task, String textMusterLoesung, Double points, String type) {
        this.title = title;
        this.task = task;
        this.textMusterLoesung = textMusterLoesung;
        this.points = points;
        this.type = type;
    }

    private QuestionDTO(int questionIndex, String title, String task, String textMusterLoesung, Double points, String type, List<String> options, List<Integer> correctOptionIndexes) {
        this.questionIndex = questionIndex;
        this.title = title;
        this.task = task;
        this.textMusterLoesung = textMusterLoesung;
        this.points = points;
        this.type = type;
        this.options = options;
        this.correctOptionIndexes = correctOptionIndexes;
    }

    public static QuestionDTO ofQuestion(Question question, int questionIndex) {
        String type = question instanceof TextQuestion ? "text" : "multiple";

        List<String> options = null;
        List<Integer> correctOptionIndexes = null;
        String textMusterLoesung = null;

        if (question instanceof MultipleChoiceQuestion mcQuestion) {
            options = mcQuestion.getOptions();
            correctOptionIndexes = mcQuestion.getCorrectOptionIndexes();
        } else if (question instanceof TextQuestion textQuestion) {
            textMusterLoesung = textQuestion.getMusterLoesung();
        }

        return new QuestionDTO(
                questionIndex,
                question.getTitle(),
                question.getTask(),
                textMusterLoesung,
                question.getPoints(),
                type,
                options,
                correctOptionIndexes
        );
    }

    public QuestionDTO() {
        this(null,null,null,null,null);
    }

    public static QuestionDTO withDummy() {
        return new QuestionDTO("titel","task","solution", 1.0, "text");
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getTextMusterLoesung() {
        return textMusterLoesung;
    }

    public void setTextMusterLoesung(String textMusterLoesung) {
        this.textMusterLoesung = textMusterLoesung;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public List<Integer> getCorrectOptionIndexes() {
        return correctOptionIndexes;
    }

    public void setCorrectOptionIndexes(List<Integer> correctOptionIndexes) {
        this.correctOptionIndexes = correctOptionIndexes;
    }

    public int getQuestionIndex() {
        return questionIndex;
    }
}
