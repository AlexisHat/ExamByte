package de.propra.exam.persistence.repositories.crud;

import de.propra.exam.domain.model.users.Student;
import de.propra.exam.persistence.entity.users.StudentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentCrudRepository extends CrudRepository<StudentEntity, Long> {
    Optional<StudentEntity> findByGithubId(String id);
}

