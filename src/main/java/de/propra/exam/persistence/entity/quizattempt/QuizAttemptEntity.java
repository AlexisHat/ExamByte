package de.propra.exam.persistence.entity.quizattempt;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table("quiz_attempt")
public record QuizAttemptEntity(@Id Long id, Long quizId, Long studentId, @MappedCollection(idColumn = "quiz_attempt", keyColumn = "quiz_attempt_key") List<AnswerEntity> answers) {}
