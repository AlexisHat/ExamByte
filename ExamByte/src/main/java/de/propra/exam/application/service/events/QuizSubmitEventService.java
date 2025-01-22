package de.propra.exam.application.service.events;

import de.propra.exam.domain.model.events.QuizAbgeschlossen;
import de.propra.exam.domain.model.quizattempt.answer.Answer;
import de.propra.exam.persistence.entity.quizattempt.AnswerEntity;
import de.propra.exam.persistence.mapper.QuizAttemptMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizSubmitEventService {

    private final List<QuizAbgeschlossen> events = new ArrayList<>();


    public void addSubmittedEvent(Long quizId, Long studentId, List<Answer> answers) {
        List<AnswerEntity> list = answers.stream().map(QuizAttemptMapper::toAnswerEntity).toList();
        synchronized (events) {
            int nr = events.size() + 1;
            events.add(new QuizAbgeschlossen(nr, quizId, studentId, list));
        }
    }

    public List<QuizAbgeschlossen> quizSubmittedSince(int nr) {
        return events.stream().filter(e -> e.eventId() > nr).toList();
    }

}