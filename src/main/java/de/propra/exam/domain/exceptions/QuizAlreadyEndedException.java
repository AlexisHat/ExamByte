package de.propra.exam.domain.exceptions;

public class QuizAlreadyEndedException extends RuntimeException{
    public QuizAlreadyEndedException(String message) {
        super(message);
    }
}
