package de.propra.quizevaluation.service;

import de.propra.quizevaluation.domain.events.QuizErstellt;
import de.propra.quizevaluation.domain.quiz.Quiz;
import de.propra.quizevaluation.persistence.repo.QuizRepoImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;

import static java.lang.Math.max;

@Service
public class WatchQuizCreatedService {

    private final QuizRepoImpl quizRepoImpl;
    int lastSeen = -1;

    public WatchQuizCreatedService(QuizRepoImpl quizRepoImpl) {
        this.quizRepoImpl = quizRepoImpl;
    }

    @Scheduled(fixedDelay = 8000)
    public void updateQuizCreated() {

        List<QuizErstellt> events = fetch();

        System.out.printf("Got %d QuizEvents.%n", events.size());

        for (QuizErstellt event : events) {
            lastSeen = max(event.eventId(), lastSeen);
            Quiz quiz = event.quiz();
            quiz.setId(event.quizId());
            quizRepoImpl.saveQuizWithQuestions(quiz);
        }
    }

    private List<QuizErstellt> fetch() {
        WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080").build();

        List<QuizErstellt> events = webClient
                .get()
                .uri(
                        uriBuilder -> uriBuilder
                                .path("/events/quiz/created")
                                .queryParam("nr", lastSeen)
                                .build()
                )
                .retrieve()
                .bodyToFlux(QuizErstellt.class)
                .collectList()
                .block(Duration.ofSeconds(6));
        return events;
    }


}
