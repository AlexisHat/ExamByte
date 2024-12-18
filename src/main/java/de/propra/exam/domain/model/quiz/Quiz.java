package de.propra.exam.domain.model.quiz;

import de.propra.exam.domain.model.quiz.question.Question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Quiz {
    String quizName;
    LocalDateTime startTime;
    LocalDateTime endTime;
    List<Question> questions = new ArrayList<>();

    public boolean isActive(LocalDateTime clientLDT) {
        return clientLDT.isAfter(startTime) && clientLDT.isBefore(endTime);
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question){
        questions.add(question);
    }

    public boolean isGestartet(LocalDateTime now) {
        return !now.isBefore(startTime);
    }

    public boolean isBeendet(LocalDateTime now) {
        return now.isAfter(endTime);
    }
    public Question findQuestionById(Long questionID) {
        return questions.stream().filter(question -> question.getQuestionId().equals(questionID)).findFirst().orElse(null);
    }
}
