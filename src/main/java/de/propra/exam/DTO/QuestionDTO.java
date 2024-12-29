package de.propra.exam.DTO;

import java.util.List;

public class QuestionDTO {
    private String title;
    private String task;
    private String textMusterLoesung;
    private Double points;
    private String type;
    private List<String> options;
    private List<Integer> correctOptionIndexes;

    private QuestionDTO(String title, String task, String textMusterLoesung, Double points, String type) {
        this.title = title;
        this.task = task;
        this.textMusterLoesung = textMusterLoesung;
        this.points = points;
        this.type = type;
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
}
