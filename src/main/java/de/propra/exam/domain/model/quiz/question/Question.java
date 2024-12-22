package de.propra.exam.domain.model.quiz.question;

public abstract class Question {
    String title;
    String task;
    Double points;
    String solution;
    Long questionId;

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }
    public Long getQuestionId() {
        return questionId;
    }
}
