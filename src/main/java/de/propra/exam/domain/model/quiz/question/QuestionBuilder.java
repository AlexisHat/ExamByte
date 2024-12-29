package de.propra.exam.domain.model.quiz.question;

import java.util.List;

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

    public QuestionBuilder withId(Long id) {
        question.setQuestionId(id);
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

    public QuestionBuilder withPoints(Double points) {
        question.setPoints(points);
        return this;
    }
    public QuestionBuilder withOptions(List<String> options) {
        if (!(question instanceof MultipleChoiceQuestion)) {
            throw new IllegalArgumentException("Question must be a MultipleChoiceQuestion to add options");
        } else {
            ((MultipleChoiceQuestion) question).setOptions(options);
        }
        return this;
    }

    public QuestionBuilder withCorrectOptionIndexes(List<Integer> correctOptionIndex) {
        if (!(question instanceof MultipleChoiceQuestion)) {
            throw new IllegalArgumentException("Question must be a MultipleChoiceQuestion to add correct option indexes");
        } else {
            ((MultipleChoiceQuestion) question).setCorrectOptionIndexes(correctOptionIndex);
        }
        return this;
    }

    public QuestionBuilder withMusterLoesung(String musterLoesungForTextQuestion) {
        if(!(question instanceof TextQuestion)){
            throw new IllegalArgumentException("Question must be a TextQuestion to set Musterlösungs text");
        }else{
            ((TextQuestion) question).setMusterLoesung(musterLoesungForTextQuestion);
        }
        return this;
    }

    public Question build() {
        return question;
    }
}
