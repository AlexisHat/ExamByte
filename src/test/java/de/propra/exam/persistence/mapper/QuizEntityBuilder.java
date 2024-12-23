package de.propra.exam.persistence.mapper;

import de.propra.exam.domain.model.quiz.Quiz;
import de.propra.exam.persistence.entity.quiz.QuestionEntity;
import de.propra.exam.persistence.entity.quiz.QuizEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public  class QuizEntityBuilder {
    private Long id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<QuestionEntity> questions = new ArrayList<>();

public QuizEntityBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public QuizEntityBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public QuizEntityBuilder withStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public QuizEntityBuilder withEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public QuizEntityBuilder withQuestions(List<QuestionEntity> questions) {
        this.questions = questions;
        return this;
    }

    public QuizEntityBuilder withDefaultQuestions() {
        QuestionEntity mutiple = QuestionEntity.ofMutiple
                (1L, 1.0, "a", "b", "hallo,bla", "1");
        QuestionEntity text = QuestionEntity.ofText
                (2L,1.5,"s","e", "x");

        List<QuestionEntity> questionEntities = List.of(mutiple, text);

        this.questions = List.of(mutiple, text);
        return this;
    }

    public QuizEntity build() {
        return new QuizEntity(id, name, startTime, endTime, questions);
    }
}
