package de.propra.exam.domain.model.users;

public class Student {
    private String githubId;
    private String name;
    private String email;

    public Student(String githubId, String name, String email) {
        this.githubId = githubId;
        this.name = name;
        this.email = email;
    }

    public Student() {
    }


    public String getGithubId() {
        return githubId;
    }

    public void setGithubId(String githubId) {
        this.githubId = githubId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
