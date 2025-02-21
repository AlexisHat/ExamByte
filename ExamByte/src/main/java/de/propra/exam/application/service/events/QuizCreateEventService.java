package de.propra.exam.application.service.events;

import de.propra.exam.domain.model.quiz.Quiz;
import de.propra.exam.persistence.mapper.QuizMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizCreateEventService {

    private final List<QuizErstellt> events = new ArrayList<>();

    public void addCreatedEvent(Quiz quiz) {
        synchronized (events) {
            int nr = events.size() + 1;

            events.add(new QuizErstellt(nr, QuizMapper.toQuizEntity(quiz), quiz.getQuizID()));
        }
    }

    public List<QuizErstellt> quizSubmittedSince(int nr) {
        return events.stream().filter(e -> e.eventId() > nr).toList();
    }

}

