package de.propra.quizevaluation.domain;

import org.springframework.data.annotation.Id;

import java.util.Set;

public class Korrektor {
    @Id
    private Long id;
    private Integer githubId;
    private Set<Answer> textAufgaben;

    public void addTextAufgaben(Answer answer) {
        textAufgaben.add(answer);
    }

    public int textAufgabenSize() {
        return textAufgaben.size();
    }

    public void setGithubId(String id) {
        this.githubId = Integer.parseInt(id);
    }
}
