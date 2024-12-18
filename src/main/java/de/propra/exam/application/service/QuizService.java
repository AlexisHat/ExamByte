package de.propra.exam.application.service;

import de.propra.exam.DTO.QuestionDTO;
import de.propra.exam.domain.exceptions.QuizNotFoundException;
import de.propra.exam.domain.model.quiz.*;
import de.propra.exam.domain.model.quiz.question.Question;
import de.propra.exam.domain.model.quiz.question.QuestionBuilder;
import de.propra.exam.domain.service.QuizRepository;
import org.springframework.stereotype.Service;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public Quiz createQuiz() {
        return new Quiz();
    }

    public void createNewQuestionInQuiz(Quiz quiz, QuestionDTO questionDTO) {
        System.out.println("DTO= " + questionDTO.getType());
        Question newQuestion = new QuestionBuilder()
                .withQuestionType(questionDTO.getType())
                .withTitle(questionDTO.getTitle())
                .withTask(questionDTO.getTask())
                .withPoints(questionDTO.getPoints())
                .withSolution(questionDTO.getSolution())
                .build();

        quiz.addQuestion(newQuestion);

    }

    public void addQuiz(Quiz quiz) {
        quizRepository.save(quiz);
    }

    public Quiz findQuizById(Long quizId) {
        return quizRepository.findById(quizId).orElseThrow(() ->
                new QuizNotFoundException("Quiz mit ID " + quizId + " nicht gefunden"));
    }


}

