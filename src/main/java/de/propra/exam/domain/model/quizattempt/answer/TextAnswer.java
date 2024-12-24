package de.propra.exam.domain.model.quizattempt.answer;


import de.propra.exam.domain.model.quizattempt.answer.Answer;

import java.time.LocalDateTime;

public class TextAnswer extends Answer {

    private String text;

    public TextAnswer(Long frageId, String text, LocalDateTime abgegebenAm) {
        super(frageId, abgegebenAm);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String neuerText, LocalDateTime zeit) {
        this.text = neuerText;
        aktualisiereAbgabeZeit(zeit);
    }
}