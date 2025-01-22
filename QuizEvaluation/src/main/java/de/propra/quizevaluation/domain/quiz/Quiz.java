package de.propra.quizevaluation.domain.quiz;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;

@Table("quiz")
public class Quiz{
    @Id
    private Long quizId;
    private final String quizName;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final LocalDateTime resultTime;
    private final List<QuestionEntity> questions;


    @PersistenceCreator
    public Quiz(Long quizId, String quizName, LocalDateTime startTime, LocalDateTime endTime, LocalDateTime resultTime, List<QuestionEntity> questions) {
        this.quizId = quizId;
        this.quizName = quizName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.resultTime = resultTime;
        this.questions = questions;
    }
}