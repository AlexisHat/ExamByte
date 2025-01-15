package de.propra.quizevaluation.domain;

import java.util.List;

public record QuizAbgeschlossen(int eventId, Long quizId, Long studentId, List<AnswerEvent> antworten) {
}
