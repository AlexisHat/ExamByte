package de.propra.exam.domain.model.quiz.question;

public class QuestionBuilder {
    private Question question;

    public QuestionBuilder withQuestionType(String type) {
        if (type.equals("multipleChoice")) {
            question = new MultipleChoiceQuestion();
        } else if (type.equals("text")) {
            question = new TextQuestion();
        }
        return this;
    }

    public QuestionBuilder withTitle(String title) {
        question.setTitle(title);
        return this;
    }

    public QuestionBuilder withTask(String task) {
        question.setTask(task);
        return this;
    }

    public QuestionBuilder withPoints(Integer points) {
        question.setPoints(points);
        return this;
    }

    public QuestionBuilder withSolution(String solution) {
        question.setSolution(solution);
        return this;
    }

    public Question build() {
        return question;
    }

}
