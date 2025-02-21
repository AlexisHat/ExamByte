package de.propra.exam.application.service.events;

import de.propra.exam.domain.model.quizattempt.answer.Answer;
import de.propra.exam.persistence.entity.quiz.QuestionType;
import de.propra.exam.persistence.entity.quizattempt.AnswerEntity;

import java.util.List;
import java.util.Map;

public record QuizAbgeschlossen(int eventId, Long quizId, Long studentId, List<AnswerEntity> antworten) {
}
