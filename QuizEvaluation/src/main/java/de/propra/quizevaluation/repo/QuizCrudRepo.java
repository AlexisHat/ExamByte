package de.propra.quizevaluation.repo;

import de.propra.quizevaluation.domain.quiz.Quiz;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface QuizCrudRepo extends CrudRepository<Quiz, Long> {
    @Modifying
    @Query("INSERT INTO quiz (quiz_id, quiz_name, start_time, end_time, result_time) " +
            "VALUES (:quizId, :quizName, :startTime, :endTime, :resultTime)")
    void insert(
            @Param("quizId") Long quizId,
            @Param("quizName") String quizName,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("resultTime") LocalDateTime resultTime
    );
    @Modifying
    @Query("INSERT INTO question (question_id, quiz_key, points, titel, task, type, options, correct_option_index, muster_loesung_for_text_question) " +
            "VALUES (:questionId, :quizKey, :points, :titel, :task, :type, :options, :correctOptionIndex, :musterLoesungForTextQuestion)")
    void insertQuestion(
            @Param("questionId") Long questionId,
            @Param("quizKey") Long quizKey,
            @Param("points") Double points,
            @Param("titel") String titel,
            @Param("task") String task,
            @Param("type") String type,
            @Param("options") String options,
            @Param("correctOptionIndex") String correctOptionIndex,
            @Param("musterLoesungForTextQuestion") String musterLoesungForTextQuestion
    );
}
