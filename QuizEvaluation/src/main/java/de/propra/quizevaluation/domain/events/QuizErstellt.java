package de.propra.quizevaluation.domain.events;

import de.propra.quizevaluation.domain.quiz.Quiz;

public record QuizErstellt(int eventId, Quiz quiz,Long quizId) {
}
