package de.propra.exam.application.service.repository;

import de.propra.exam.domain.model.users.Student;

import java.util.Optional;

public interface StudentRepository {
    Optional<Student> findByGithubId(String id);

    Student save(Student newStudent);
}
