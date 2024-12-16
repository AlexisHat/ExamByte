package de.propra.exam.domain.model.quizattempt;


import java.time.LocalDateTime;

public class FreitextAntwort extends Antwort {
    private String text;

    public FreitextAntwort(Long frageId, String text, LocalDateTime abgegebenAm) {
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