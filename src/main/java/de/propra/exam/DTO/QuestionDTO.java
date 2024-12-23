package de.propra.exam.DTO;

public class QuestionDTO {
    private String title;
    private String task;
    private String solution;
    private Double points;
    private String type;

    private QuestionDTO(String title, String task, String solution, Double points, String type) {
        this.title = title;
        this.task = task;
        this.solution = solution;
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

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }
}
