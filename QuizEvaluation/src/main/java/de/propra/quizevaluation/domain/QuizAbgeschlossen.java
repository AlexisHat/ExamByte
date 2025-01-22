package de.propra.quizevaluation.domain;

import de.propra.quizevaluation.domain.Attempt.Answer;

import java.util.List;

public record QuizAbgeschlossen(int eventId, Long quizId, Long studentId, List<Answer> antworten) {
}
