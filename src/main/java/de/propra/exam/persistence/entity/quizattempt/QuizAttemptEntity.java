package de.propra.exam.persistence.entity.quizattempt;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
@Table("quiz_attempt")
public record QuizAttemptEntity(@Id Long quizAttemptId, Long quizId, Long studentId, List<AnswerEntity> answers) {}
