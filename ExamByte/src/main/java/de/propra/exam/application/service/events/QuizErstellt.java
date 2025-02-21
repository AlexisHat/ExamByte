package de.propra.exam.application.service.events;

import de.propra.exam.domain.model.quiz.Quiz;
import de.propra.exam.persistence.entity.quiz.QuizEntity;

public record QuizErstellt(int eventId, QuizEntity quiz, Long quizId) {
}
