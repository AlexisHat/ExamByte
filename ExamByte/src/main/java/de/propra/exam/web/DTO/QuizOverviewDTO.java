package de.propra.exam.web.DTO;

import de.propra.exam.domain.model.quiz.Quiz;
import de.propra.exam.domain.model.quizattempt.QuizAttempt;

import java.time.LocalDateTime;

public class QuizOverviewDTO {
    private String quizName;
    private QuizStatus status;
    private double score;
    private double maxScore;
    private Long quizId;


    public QuizOverviewDTO(Long quizId, String quizName, QuizStatus status, double score, double maxScore) {
        this.quizId = quizId;
        this.quizName = quizName;
        this.status = status;
        this.score = score;
        this.maxScore = maxScore;
    }

    public QuizOverviewDTO(Quiz quiz, double score, double maxScore) {
        this.quizId = quiz.getQuizID();
        this.quizName = quiz.getQuizName();
        this.status = determineQuizStatus(quiz.getStartTime(), quiz.getEndTime());
        this.score = score;
        this.maxScore = maxScore;
    }

    public static QuizOverviewDTO from(Quiz quiz, QuizAttempt quizAttempt) {
        QuizStatus quizStatus = determineQuizStatus(quiz.getStartTime(), quiz.getEndTime());
        return new QuizOverviewDTO(quiz.getQuizID(), quiz.getQuizName(), quizStatus, quizAttempt.getScore() , quiz.getMaxScore());
    }

    private static QuizStatus determineQuizStatus(LocalDateTime startTime, LocalDateTime endTime) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(startTime) && now.isBefore(endTime)) {
            return QuizStatus.BEGONNEN;
        } else if (now.isAfter(endTime)) {
            return QuizStatus.BEENDET;
        } else if (now.isBefore(startTime)) {
            return QuizStatus.NOCH_NICHT_BEGONNEN;
        }
        return null;
    }

    public String getQuizName() {
        return quizName;
    }

    public QuizStatus getStatus() {
        return status;
    }

    public double getScore() {
        return score;
    }

    public double getMaxScore() {
        return maxScore;
    }

    public long getQuizId() {
        return quizId;
    }


}
