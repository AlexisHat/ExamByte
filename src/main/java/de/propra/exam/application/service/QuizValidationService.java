package de.propra.exam.application.service;

import de.propra.exam.domain.exceptions.QuizAlreadyEndedException;
import de.propra.exam.domain.exceptions.QuizNotStartedException;
import de.propra.exam.domain.model.quiz.Quiz;
import de.propra.exam.domain.service.Clock;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class QuizValidationService {
    private final Clock clock;

    public QuizValidationService(Clock clock) {
        this.clock = clock;
    }

    public void validateQuizStarted(Quiz quiz) {
        if (!quiz.isGestartet(clock.now())) {
            throw new QuizNotStartedException("Quiz hat noch nicht begonnen.");
        }
    }

    public void validateQuizStartedAndNotEnded(Quiz quiz) {
        LocalDateTime now = clock.now();
        validateQuizStarted(quiz);
        if (quiz.isBeendet(now)) {
            throw new QuizAlreadyEndedException("Quiz ist beendet.");
        }
    }

    public LocalDateTime getCurrentTime() {
        return clock.now();
    }
}
