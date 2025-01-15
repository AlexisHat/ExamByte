package de.propra.exam.controllers.event;

import de.propra.exam.application.service.QuizSubmitEventService;
import de.propra.exam.domain.model.events.QuizAbgeschlossen;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class EventController {


    private final QuizSubmitEventService submitEventService;

    public EventController(QuizSubmitEventService submitEventService) {
        this.submitEventService = submitEventService;
    }

    @GetMapping("/events")
    public List<QuizAbgeschlossen> submittedAttempts(int nr) {

        List<QuizAbgeschlossen> result = submitEventService.quizSubmittedSince(nr);


        System.out.println("Returning " + result.size() + " events for nr > " + nr);


        return result;
    }
}
