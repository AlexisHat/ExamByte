package de.propra.exam.web.event;

import de.propra.exam.application.service.events.QuizCreateEventService;
import de.propra.exam.application.service.events.QuizSubmitEventService;
import de.propra.exam.application.service.events.QuizAbgeschlossen;
import de.propra.exam.application.service.events.QuizErstellt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class EventController {


    private final QuizSubmitEventService submitEventService;
    private final QuizCreateEventService createEventService;

    public EventController(QuizSubmitEventService submitEventService, QuizCreateEventService createEventService) {
        this.submitEventService = submitEventService;
        this.createEventService = createEventService;
    }

    @GetMapping("/events/attempt/submitted")
    public List<QuizAbgeschlossen> submittedAttempts(int nr) {
        return submitEventService.quizSubmittedSince(nr);
    }

    @GetMapping("/events/quiz/created")
    public List<QuizErstellt> createdQuizs(int nr) {
       return createEventService.quizSubmittedSince(nr);
    }
}
