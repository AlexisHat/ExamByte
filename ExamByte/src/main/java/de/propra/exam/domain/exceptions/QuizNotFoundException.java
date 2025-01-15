package de.propra.exam.domain.exceptions;

public class QuizNotFoundException extends RuntimeException{
    public QuizNotFoundException(String message) {
        super(message);
    }
}
