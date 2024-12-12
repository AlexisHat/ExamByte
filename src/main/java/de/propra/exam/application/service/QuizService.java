package de.propra.exam.application.service;

import de.propra.exam.domain.model.quizcore.Quiz;
import de.propra.exam.domain.service.QuizRepository;
import org.springframework.stereotype.Service;

@Service
public class QuizService {
    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }
    public void addQuiz(Quiz quiz){
        quizRepository.save(quiz);
    }
}
