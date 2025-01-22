package de.propra.exam.domain.model.events;

import de.propra.exam.domain.model.quiz.Quiz;

public record QuizErstellt(int eventId, Quiz quiz) {
}
