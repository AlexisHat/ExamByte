package de.propra.quizevaluation.web.DTO;

public record TextAnswerCorrectionDTO(String antwort, String frageTitel, String frageTask, String musterLoesung, Double maxPoints, Double points) {}
