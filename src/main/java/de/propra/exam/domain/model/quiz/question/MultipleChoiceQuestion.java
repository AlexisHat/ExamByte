package de.propra.exam.domain.model.quiz.question;


import java.awt.*;
import java.util.List;

public class MultipleChoiceQuestion extends Question {

    List<String> options;

    List<Integer> correctOptionIndexes;

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