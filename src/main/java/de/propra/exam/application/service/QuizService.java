package de.propra.exam.application.service;

import de.propra.exam.DTO.QuestionDTO;
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

    public void createNewQuestionInQuiz(Quiz quiz, QuestionDTO questionDTO) {

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
        newQuestion.setTitle(questionDTO.getTitle());
        newQuestion.setTask(questionDTO.getTask());
        newQuestion.setPoints(questionDTO.getPoints());
        newQuestion.setSolution(questionDTO.getSolution());
        quiz.addQuestion(newQuestion);

    }

    public void addQuiz(Quiz quiz) {
        quizRepository.save(quiz);
    }
}

