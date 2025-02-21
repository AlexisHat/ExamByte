package de.propra.quizevaluation.dbtest;

import de.propra.quizevaluation.config.TestConfig;
import de.propra.quizevaluation.domain.quiz.Quiz;
import de.propra.quizevaluation.persistence.dao.QuizCrudRepo;
import de.propra.quizevaluation.persistence.repo.QuizRepoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestConfig.class)
public class QuizRepoTest {

    @Autowired
    QuizCrudRepo quizCrudRepo;


    QuizRepoImpl quizRepo;

    @BeforeEach
    void init() {
        quizRepo = new QuizRepoImpl(quizCrudRepo);
    }

    @Test
    @DisplayName("die add Quiz Methode fügt ein Quiz in eine bisher leere Db")
    void test_01(){
        assertThat(quizRepo.findAll().size()).isEqualTo(0);

        Quiz quiz = Quiz.createDummyWithOneQuestion();

        quizRepo.saveQuizWithQuestions(quiz);

        int size = quizRepo.findAll().size();

        assertThat(size).isOne();
    }

}
