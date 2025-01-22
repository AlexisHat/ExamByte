package de.propra.exam.domain.model.quiz;

import de.propra.exam.domain.model.quiz.question.Question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Quiz {


    Long quizID;
    String quizName;
    LocalDateTime startTime;
    LocalDateTime endTime;
    LocalDateTime resultTime;
    List<Question> questions = new ArrayList<>();
    private double maxScore;

    public LocalDateTime getResultTime() {
        return resultTime;
    }

    public void setResultTime(LocalDateTime resultTime) {
        this.resultTime = resultTime;
    }

    public boolean isActive(LocalDateTime clientLDT) {
        return clientLDT.isAfter(startTime) && clientLDT.isBefore(endTime);
    }
    public Long getQuizID() {
        return quizID;
    }

    public void setQuizID(Long quizID) {
        this.quizID = quizID;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
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

    public boolean isGestartet() {
        LocalDateTime now = LocalDateTime.now();
        return !now.isBefore(startTime);
    }

    public boolean isGestartet(LocalDateTime now) {
        return !now.isBefore(startTime);
    }

    public boolean isBeendet(LocalDateTime now) {
        return now.isAfter(endTime);

    }



    public Question findQuestionById(Long questionID) {
        return questions.stream()
                .filter(question -> question.getQuestionId() != null && question.getQuestionId().equals(questionID))
                .findFirst()
                .orElse(null);
    }

    public double getMaxScore() {
        return questions
                .stream()
                .mapToDouble(Question::getPoints)
                .sum();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Quiz quiz = (Quiz) o;
        return Objects.equals(quizID, quiz.quizID);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(quizID);
    }
}
