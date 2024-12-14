package de.propra.exam.domain.model.quizcore;

import java.util.Map;


public class MultipleChoiceQuestion extends Question {

    private Map<String, Boolean> options;

    public MultipleChoiceQuestion(Map<String, Boolean> options) {
        this.options = options;
    }

    public Map<String, Boolean> getOptions() {
        return options;
    }

    public void setOptions(Map<String, Boolean> options) {
        this.options = options;
    }
}