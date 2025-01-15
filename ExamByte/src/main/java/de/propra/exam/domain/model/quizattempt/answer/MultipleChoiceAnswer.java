package de.propra.exam.domain.model.quizattempt.answer;

import de.propra.exam.domain.model.quizattempt.answer.Answer;

import java.time.LocalDateTime;
import java.util.List;

public class MultipleChoiceAnswer extends Answer {

    private List<String> ausgewaehlteOptionen;

    public MultipleChoiceAnswer( Long frageId, List<String> ausgewaehlteOptionen, LocalDateTime abgegebenAm) {
        super(null, frageId, abgegebenAm);
        this.ausgewaehlteOptionen = ausgewaehlteOptionen;
    }

    public MultipleChoiceAnswer(Long id, Long frageId, LocalDateTime abgegebenAm, List<String> ausgewaehlteOptionen) {
        super(id, frageId, abgegebenAm);
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