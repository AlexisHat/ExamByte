package de.propra.exam.domain.model.quizattempt.answer;

import java.time.LocalDateTime;

public abstract class Answer {
    private final Long frageId;
    private LocalDateTime abgegebenAm;

    public Answer(Long frageId, LocalDateTime abgegebenAm) {
        this.frageId = frageId;
        this.abgegebenAm = abgegebenAm;
    }

    public Long getFrageId() {
        return frageId;
    }

    public void aktualisiereAbgabeZeit(LocalDateTime zeitpunkt) {
        this.abgegebenAm = zeitpunkt;
    }
}
