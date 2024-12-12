package de.propra.exam.domain.model.quizcore;

import java.util.List;


public class MultipleChoiceQuestion extends Question {
    private List<String> options;

    public MultipleChoiceQuestion(List<String> options) {
        this.options = options;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}