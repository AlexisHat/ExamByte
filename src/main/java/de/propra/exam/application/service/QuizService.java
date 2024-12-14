package de.propra.exam.application.service;

import de.propra.exam.domain.model.quizcore.MultipleChoiceQuestion;
import de.propra.exam.domain.model.quizcore.Question;
import de.propra.exam.domain.model.quizcore.Quiz;
import de.propra.exam.domain.model.quizcore.TextQuestion;
import de.propra.exam.domain.service.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public Quiz createQuiz(){
        return new Quiz();
    }

    public void createNewQuestionInQuiz(Quiz quiz, List<String> options, String questionTitel, String questionText) {

        List<String> validOptions = (options != null)
                ? options.stream()
                .filter(option -> option != null && !option.trim().isEmpty())
                .toList()
                : List.of();

        Question newQuestion = validOptions.isEmpty()
                ? new TextQuestion()
                : new MultipleChoiceQuestion(validOptions);

        newQuestion.setText(questionText);
        newQuestion.setTitel(questionTitel);

        quiz.addFrage(newQuestion);

    }

    public void addQuiz(Quiz quiz) {
        quizRepository.save(quiz);
    }
}

