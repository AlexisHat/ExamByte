package de.propra.exam.application.service;

import de.propra.exam.DTO.QuestionDTO;
import de.propra.exam.domain.exceptions.IllegalQuestionIndexException;
import de.propra.exam.domain.exceptions.QuizNotFoundException;
import de.propra.exam.domain.model.quiz.*;
import de.propra.exam.domain.model.quiz.question.Question;
import de.propra.exam.domain.model.quiz.question.QuestionBuilder;
import de.propra.exam.domain.service.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public Quiz createQuiz() {
        return new Quiz();
    }

    public Question getQuestion(Long id, Integer questionIndex) {
        Quiz quiz = findQuizById(id);

        if (questionIndex < 1 || questionIndex > quiz.getQuestions().size()) {
            throw new IllegalQuestionIndexException("Falscher Fragen Index" + questionIndex);
        }
        return quiz.getQuestions().get(questionIndex - 1);
    }

    public Quiz findQuizById(Long quizId) {
        return quizRepository.findById(quizId).orElseThrow(() ->
                new QuizNotFoundException("Quiz mit ID " + quizId + " nicht gefunden"));
    }

    public Long addQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public void createNewQuestionInQuiz(Quiz quiz, QuestionDTO questionDTO) {
        System.out.println("DTO= " + questionDTO.getType());
        QuestionBuilder builder = new QuestionBuilder()
                .withQuestionType(questionDTO.getType())
                .withTitle(questionDTO.getTitle())
                .withTask(questionDTO.getTask())
                .withPoints(questionDTO.getPoints());

        if ("multipleChoice".equals(questionDTO.getType())) {
            builder.withOptions(questionDTO.getOptions());
            builder.withCorrectOptionIndexes(questionDTO.getCorrectOptionIndexes());
        } else if ("text".equals(questionDTO.getType())) {
            builder.withMusterLoesung(questionDTO.getTextMusterLoesung());
        }

        Question newQuestion = builder.build();
        quiz.addQuestion(newQuestion);
    }

    public List<Quiz> findAllQuiz() {
        return quizRepository.findAll();
    }

    public int getQuestionListLength(Long id) {
        Quiz quizById = findQuizById(id);
        return quizById.getQuestions().size();
    }
}


