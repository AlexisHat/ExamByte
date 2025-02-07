package de.propra.exam.application.service;

import de.propra.exam.domain.model.users.Student;
import de.propra.exam.application.service.repository.StudentRepository;
import org.springframework.stereotype.Service;

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
