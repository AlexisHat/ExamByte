package de.propra.exam.persistence.entity.quiz;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table("quiz")
public class QuizEntity {
    @Id
    private Long quizId;
    private String quizName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<QuestionEntity> questions;


    @PersistenceCreator
    public QuizEntity(Long quizId, String quizName, LocalDateTime startTime, LocalDateTime endTime, List<QuestionEntity> questions) {
        this.quizId = quizId;
        this.quizName = quizName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.questions = questions;
    }


    public QuizEntity(String quizName, LocalDateTime startTime, LocalDateTime endTime) {
        this(null, quizName, startTime, endTime, new ArrayList<>());
    }

    public static QuizEntity createEmpty() {
        return new QuizEntity(null,null,null,null,null);
    }

    public Long getQuizId() {
        return quizId;
    }

    public String getQuizName() {
        return quizName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public List<QuestionEntity> getQuestions() {
        return questions;
    }

    public void addQuestions(QuestionEntity question) {
        questions.add(question);
    }
}
