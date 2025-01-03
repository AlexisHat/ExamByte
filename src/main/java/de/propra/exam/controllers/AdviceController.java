package de.propra.exam.controllers;

import de.propra.exam.domain.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AdviceController {

    @ExceptionHandler(QuizAlreadyEndedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleQuizAlreadyEndedException(QuizAlreadyEndedException ex) {
        return "error/ended";
    }

    @ExceptionHandler(IllegalQuestionIndexException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalQuestionIndexException(IllegalQuestionIndexException ex) {
        return "error/illegalQuestionIndex";
    }

    @ExceptionHandler(QuizNotStartedException.class)
    public String handleQuizNotStartedException(QuizNotStartedException ex) {
        return "error/ended";
    }

    @ExceptionHandler(QuizNotFoundException.class)
    public String handleQuizNotFoundException(QuizNotFoundException ex) {
        return "error/quizIdNotFound";
    }
}
