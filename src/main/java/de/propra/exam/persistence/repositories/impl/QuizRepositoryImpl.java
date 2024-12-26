package de.propra.exam.persistence.repositories.impl;

import de.propra.exam.domain.model.quiz.Quiz;
import de.propra.exam.domain.model.quiz.question.Question;
import de.propra.exam.domain.service.QuizRepository;
import de.propra.exam.persistence.entity.quiz.QuestionEntity;
import de.propra.exam.persistence.entity.quiz.QuizEntity;
import de.propra.exam.persistence.mapper.QuizMapper;
import de.propra.exam.persistence.repositories.crud.QuizCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
public class QuizRepositoryImpl implements QuizRepository {

    private final QuizCrudRepository quizCrudRepository;

    public QuizRepositoryImpl(QuizCrudRepository quizCrudRepository) {
        this.quizCrudRepository = quizCrudRepository;
    }

    @Override
    public List<Quiz> findAll() {
        Iterable<QuizEntity> all = quizCrudRepository.findAll();
        List<Quiz> quizzes = new ArrayList<>();
        all.forEach(quizEntity -> quizzes.add(QuizMapper.toDomain(quizEntity)));
        return quizzes;
    }


    @Override
    public Long save(Quiz quiz) {
        QuizEntity quizEntity = QuizMapper.toQuizEntity(quiz);
        QuizEntity save = quizCrudRepository.save(quizEntity);
        return save.getQuizId();
    }

    @Override
    public Optional<Quiz> findById(Long id) {
        Optional<QuizEntity> byId = quizCrudRepository.findById(id);
        return byId.map(QuizMapper::toDomain);
    }


    public List<Question> findQuestionsForQuizId(Long quizId) {
        Optional<QuizEntity> quiz = quizCrudRepository.findById(quizId);
        List<QuestionEntity> questionList = new ArrayList<>();
        quiz.ifPresent(q -> questionList.addAll(q.getQuestions()));
        return questionList.stream()
                .map(QuizMapper::toDomain)
                .collect(Collectors.toList());
    }

}
