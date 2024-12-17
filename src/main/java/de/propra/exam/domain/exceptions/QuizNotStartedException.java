package de.propra.exam.domain.exceptions;

public class QuizNotStartedException extends RuntimeException{
    public QuizNotStartedException(String message) {
        super(message);
    }
}
