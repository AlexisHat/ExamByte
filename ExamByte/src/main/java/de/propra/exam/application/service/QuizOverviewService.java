package de.propra.exam.application.service;

import de.propra.exam.domain.DTO.QuizOverviewDTO;
import de.propra.exam.domain.model.quiz.Quiz;
import de.propra.exam.domain.model.quizattempt.QuizAttempt;
import de.propra.exam.application.service.repository.AttemptRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizOverviewService {


    private final QuizService quizService;
    private final AttemptRepository quizAttemptRepo;

    public QuizOverviewService(QuizService quizService, AttemptRepository quizAttemptRepo) {
        this.quizService = quizService;
        this.quizAttemptRepo = quizAttemptRepo;
    }

    public List<QuizOverviewDTO> getStudentQuizOverviews(Long studentId) {

        List<Quiz> quizzes = quizService.findAllQuiz();

        return quizzes.stream()
                .map(quiz -> {
                    QuizAttempt attempt = quizAttemptRepo.findQuizAttemptByQuizIdAndStudentId(quiz.getQuizID(), studentId).orElse(null);
                    return attempt != null ? QuizOverviewDTO.from(quiz, attempt)
                            : new QuizOverviewDTO(
                            quiz,
                            0.0,
                            quiz.getMaxScore()
                    );
                })
                .toList();
    }
}

