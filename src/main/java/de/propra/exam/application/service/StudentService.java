package de.propra.exam.application.service;

import de.propra.exam.domain.model.users.Student;
import de.propra.exam.domain.service.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.format.SignStyle;
import java.util.Optional;

@Service
public class StudentService {
    StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Long findStudentIdByGitHubId(String github) {
        return studentRepository.findByGithubId(github).map(Student::getId).orElse(null);
    }
}
