package de.propra.exam.persistence.mapper;

import de.propra.exam.domain.model.users.Student;
import de.propra.exam.persistence.entity.users.StudentEntity;

public class StudentMapper {

    public static StudentEntity toEntity(Student student) {
        return (student == null) ? null : new StudentEntity(null,student.getGithubId() ,student.getName(), student.getEmail());
    }
    public static Student toDomain(StudentEntity studentEntity) {
        if (studentEntity == null) {
            return null;
        }
        return new Student(
                studentEntity.githubId(),
                studentEntity.name(),
                studentEntity.email()
        );
    }
}
