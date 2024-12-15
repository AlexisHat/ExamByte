package de.propra.exam.application.service;

import de.propra.exam.domain.model.quizcore.*;
import de.propra.exam.domain.service.QuizRepository;
import org.springframework.stereotype.Service;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public Quiz createQuiz(){
        return new Quiz();
    }

    public void createNewQuestionInQuiz(Quiz quiz,
                                        String questionTitel,
                                        String questionText,
                                        Integer questionPoints,
                                        String questionSolution) {

//        List<String> validOptions = (options != null)
//                ? options.stream()
//                .filter(option -> option != null && !option.trim().isEmpty())
//                .toList()
//                : List.of();
//
//        Question newQuestion = validOptions.isEmpty()
//                ? new TextQuestion()
//                : new MultipleChoiceQuestion(validOptions);
//
//        newQuestion.setAufgabenstellung(questionText);
//        newQuestion.setTitel(questionTitel);

        TextQuestion newQuestion = new TextQuestion();
        newQuestion.setTitle(questionTitel);
        newQuestion.setTask(questionText);
        newQuestion.setPoints(questionPoints);
        newQuestion.setSolution(questionSolution);
        quiz.addQuestion(newQuestion);

    }

    public void addQuiz(Quiz quiz) {
        quizRepository.save(quiz);
    }
}

