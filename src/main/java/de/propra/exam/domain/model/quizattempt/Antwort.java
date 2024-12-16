package de.propra.exam.domain.model.quizattempt;

import java.time.LocalDateTime;

public abstract class Antwort {
    private final Long frageId;
    private LocalDateTime abgegebenAm;

    public Antwort(Long frageId, LocalDateTime abgegebenAm) {
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
