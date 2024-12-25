package de.propra.exam.domain.model.quizattempt.answer;

import java.time.LocalDateTime;

public abstract class Answer {
    private final Long id;
    private final Long frageId;
    private LocalDateTime abgegebenAm;

    public Answer(Long id, Long frageId, LocalDateTime abgegebenAm) {
        this.id = id;
        this.frageId = frageId;
        this.abgegebenAm = abgegebenAm;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getAbgegebenAm() {
        return abgegebenAm;
    }

    public Long getFrageId() {
        return frageId;
    }

    public void aktualisiereAbgabeZeit(LocalDateTime zeitpunkt) {
        this.abgegebenAm = zeitpunkt;
    }
}
