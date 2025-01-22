package de.propra.exam.application.service.events;

import de.propra.exam.domain.model.events.QuizAbgeschlossen;
import de.propra.exam.domain.model.events.QuizErstellt;
import de.propra.exam.domain.model.quiz.Quiz;
import de.propra.exam.domain.model.quizattempt.answer.Answer;
import de.propra.exam.persistence.entity.quizattempt.AnswerEntity;
import de.propra.exam.persistence.mapper.QuizAttemptMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizCreateEventService {

    private final List<QuizErstellt> events = new ArrayList<>();

    public void addCreatedEvent(Quiz quiz) {
        synchronized (events) {
            int nr = events.size() + 1;
            events.add(new QuizErstellt(nr,quiz));
        }
    }

    public List<QuizErstellt> quizSubmittedSince(int nr) {
        return events.stream().filter(e -> e.eventId() > nr).toList();
    }

}

