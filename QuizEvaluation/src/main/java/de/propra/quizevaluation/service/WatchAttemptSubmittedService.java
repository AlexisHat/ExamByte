package de.propra.quizevaluation.service;

import de.propra.quizevaluation.domain.Attempt.Answer;
import de.propra.quizevaluation.domain.QuizAbgeschlossen;
import de.propra.quizevaluation.domain.Attempt.Attempt;
import de.propra.quizevaluation.domain.Attempt.QuestionType;
import de.propra.quizevaluation.repo.AttemptReposittoryImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;

import static java.lang.Math.max;

@Service
public class WatchAttemptSubmittedService {

    private final AttemptReposittoryImpl attemptRepository;
    private final KorrektorService korrektorService;

    public WatchAttemptSubmittedService(AttemptReposittoryImpl attemptRepository, KorrektorService korrektorService) {
        this.attemptRepository = attemptRepository;
        this.korrektorService = korrektorService;
    }

    int lastSeen = -1;

    @Scheduled(fixedDelay = 10000)
    public void updateData() {
        System.out.println("Updating");

        List<QuizAbgeschlossen> events = fetch();

        System.out.printf("Got %d events.%n", events.size());

        for (QuizAbgeschlossen event : events) {
            lastSeen = max(event.eventId(), lastSeen);
            Attempt attempt = new Attempt(null, event.quizId(), event.studentId(), event.antworten());

            List<Answer> textAntworten = event.antworten().stream()
                    .filter(a -> a.getType().equals(QuestionType.TEXT))
                    .toList();

            List<Answer> mutiple = event.antworten().stream()
                    .filter(a -> a.getType().equals(QuestionType.MULTIPLE_CHOICE))
                    .toList();

            korrektorService.distributeTextAnswers(textAntworten);
            attemptRepository.save(attempt);
        }
    }

public List<QuizAbgeschlossen> fetch() {
    WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080").build();

    List<QuizAbgeschlossen> events = webClient
            .get()
            .uri(
                    uriBuilder -> uriBuilder
                            .path("/events/attempt/submitted")
                            .queryParam("nr", lastSeen)
                            .build()
            )
            .retrieve()
            .bodyToFlux(QuizAbgeschlossen.class)
            .collectList()
            .block(Duration.ofSeconds(5));

    return events;
}
}
