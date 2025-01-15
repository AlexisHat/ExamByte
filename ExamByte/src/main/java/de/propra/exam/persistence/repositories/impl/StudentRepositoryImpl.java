package de.propra.exam.persistence.repositories.impl;

import de.propra.exam.domain.model.users.Student;
import de.propra.exam.domain.service.StudentRepository;
import de.propra.exam.persistence.entity.users.StudentEntity;
import de.propra.exam.persistence.mapper.StudentMapper;
import de.propra.exam.persistence.repositories.crud.StudentCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class StudentRepositoryImpl implements StudentRepository {

    private final StudentCrudRepository studentCrudRepository;

    public StudentRepositoryImpl(StudentCrudRepository studentCrudRepository) {
        this.studentCrudRepository = studentCrudRepository;
    }

    @Override
    public Optional<Student> findByGithubId(String id) {
        Optional<StudentEntity> byGithubId = studentCrudRepository.findByGithubId(id);
        if (byGithubId.isPresent()) {
            Student domain = StudentMapper.toDomain(byGithubId.get());
            return Optional.of(domain);
        }else {
            return Optional.empty();
        }
    }

    @Override
    public Student save(Student newStudent) {
        StudentEntity entity = StudentMapper.toEntity(newStudent);
        StudentEntity save = studentCrudRepository.save(entity);
        return StudentMapper.toDomain(save);
    }
}
