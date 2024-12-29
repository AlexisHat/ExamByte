package de.propra.exam.persistence.entity.users;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Table("student")
public record StudentEntity(@Id Long id, String githubId, String name ,String email) {}
