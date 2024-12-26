package de.propra.exam.persistence.entity.users;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Table("student")
public class StudentEntity {
    @Id
    private Long id;
    private String githubId;
    private String email;
    private String name;

    @PersistenceCreator
    private StudentEntity(Long id, String githubId, String email, String name) {
        this.id = id;
        this.githubId = githubId;
        this.email = email;
        this.name = name;
    }

    public StudentEntity(String name, String email, String githubId) {
        this.name = name;
        this.email = email;
        this.githubId = githubId;
    }

    public String getGithubId() {
        return githubId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
