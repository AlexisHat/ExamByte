package de.propra.quizevaluation.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table("quiz_attempt")
public record Attempt(@Id Long id, Long quizId, Long studentId, List<AnswerEvent> answerEntities) {}
