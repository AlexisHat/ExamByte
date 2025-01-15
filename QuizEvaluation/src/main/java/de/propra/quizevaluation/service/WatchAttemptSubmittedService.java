package de.propra.quizevaluation.service;

import de.propra.quizevaluation.domain.QuizAbgeschlossen;
import de.propra.quizevaluation.domain.Attempt;
import de.propra.quizevaluation.repo.AttemptReposittoryImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;
import java.util.List;

import static java.lang.Math.max;

@Service
public class WatchAttemptSubmittedService {

    AttemptReposittoryImpl attemptRepository;

    public WatchAttemptSubmittedService(AttemptReposittoryImpl attemptRepository) {
        this.attemptRepository = attemptRepository;
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

            System.out.printf("Attempt %d: %s%n", lastSeen, attempt);
            attemptRepository.save(attempt);
        }
    }

public List<QuizAbgeschlossen> fetch() {
    WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080").build();

    List<QuizAbgeschlossen> events = webClient
            .get()
            .uri(
                    uriBuilder -> uriBuilder
                            .path("/events")
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
