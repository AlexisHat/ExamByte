package de.propra.exam.domain.model.quizattempt;

import java.time.LocalDateTime;
import java.util.List;

public class MultipleChoiceAntwort extends Antwort {
    private List<String> ausgewaehlteOptionen;

    public MultipleChoiceAntwort(Long frageId, List<String> ausgewaehlteOptionen, LocalDateTime abgegebenAm) {
        super(frageId, abgegebenAm);
        this.ausgewaehlteOptionen = ausgewaehlteOptionen;
    }


    public List<String> getAusgewaehlteOptionen() {
        return ausgewaehlteOptionen;
    }

    public void setAusgewaehlteOptionen(List<String> neueOptionen, LocalDateTime zeit) {
        this.ausgewaehlteOptionen = neueOptionen;
        aktualisiereAbgabeZeit(zeit);
    }
}