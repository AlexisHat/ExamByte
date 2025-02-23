package de.propra.quizevaluation.persistence.dao;

import de.propra.quizevaluation.domain.Attempt.Answer;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerCrud extends CrudRepository<Answer, Long> {

    @Query("SELECT FROM answer WHERE korrektor = :korrektorId AND answer.points is not null ")
    List<Answer> findAllByKorrektor(@Param("korrektorId") Long korrektorId);
}
